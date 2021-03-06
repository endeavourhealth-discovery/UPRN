package org.endeavourhealth.dataassurance.logic;

import com.google.common.base.Strings;
import org.endeavourhealth.common.cache.ObjectMapperPool;
import org.endeavourhealth.core.database.dal.DalProvider;
import org.endeavourhealth.core.database.dal.ehr.models.ResourceWrapper;
import org.endeavourhealth.core.database.dal.publisherTransform.SourceFileMappingDalI;
import org.endeavourhealth.core.database.dal.publisherTransform.models.ResourceFieldMapping;
import org.endeavourhealth.dataassurance.dal.ResourceDAL;
import org.endeavourhealth.dataassurance.dal.ResourceDAL_Cassandra;
import org.endeavourhealth.dataassurance.helpers.CUIFormatter;
import org.endeavourhealth.dataassurance.models.PatientResource;
import org.endeavourhealth.dataassurance.models.ResourceId;
import org.endeavourhealth.dataassurance.models.ResourceType;
import org.hl7.fhir.instance.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

public class ResourceLogic {
    static ResourceDAL dal = null;
    static SourceFileMappingDalI fileMappingDal = null;

    private static final Logger LOG = LoggerFactory.getLogger(ResourceLogic.class);

    public ResourceLogic() {
        if (dal == null)
            dal = new ResourceDAL_Cassandra();

        if (fileMappingDal == null)
            fileMappingDal = DalProvider.factorySourceFileMappingDal();
    }

    public List<ResourceType> getTypes() {
        return dal.getResourceTypes();
    }

    public List<PatientResource> getPatientResources(Set<String> serviceIds, List<ResourceId> patients, List<String> resourceTypes) throws Exception {
        List<PatientResource> resourceObjects = new ArrayList<>();

        if (serviceIds == null || serviceIds.size() == 0)
            return resourceObjects;


        for (ResourceId patient : patients) {

            String serviceId = patient.getServiceId();
            if (serviceIds.contains(serviceId)) {
                List<ResourceWrapper> resourceWrappers = dal.getPatientResources(
                    patient.getServiceId(),
                    null,
                    patient.getPatientId(),
                    resourceTypes);

                ObjectMapperPool parserPool = ObjectMapperPool.getInstance();

                for (ResourceWrapper resourceWrapper : resourceWrappers)
                    resourceObjects.add(
                        new PatientResource(
                            resourceWrapper.getServiceId().toString(),
                            null,
                            resourceWrapper.getPatientId().toString(),
                            parserPool.readTree(resourceWrapper.getResourceData())
                        ));
            }
        }
        return resourceObjects;
    }

    public String getReferenceDescription(String serviceId, String reference) {
        if (reference == null || reference.isEmpty())
            return null;

        int slashPos = reference.indexOf('/');
        if (slashPos == -1)
            return "Invalid reference";

        String resourceTypeStr = reference.substring(0, slashPos);
        org.hl7.fhir.instance.model.ResourceType resourceType = org.hl7.fhir.instance.model.ResourceType.valueOf(resourceTypeStr);
        String resourceId = reference.substring(slashPos + 1);

        try {
            UUID.fromString(resourceId);
        } catch (IllegalArgumentException e) {
            return "Invalid reference id";
        }

        Resource resource = dal.getResource(resourceType, resourceId, serviceId);
        if (resource == null)
            return "Not found";

        switch (resourceType) {
            case Organization: return ((Organization)resource).getName();
            case Practitioner: return getHumanName(((Practitioner)resource).getName());
            case Location: return ((Location)resource).getName();
            case Observation: return getObservationDisplay((Observation)resource);
            case Condition: return getConditionDisplay((Condition)resource);
            case Procedure: return getProcedureDisplay((Procedure)resource);
            case Immunization: return getImmsDisplay((Immunization)resource);
            case FamilyMemberHistory: return getFamilyHistoryDisplay((FamilyMemberHistory)resource);
            case MedicationStatement: return getMedicationStatementDisplay((MedicationStatement)resource);
            case MedicationOrder: return getMedicationOrderDisplay((MedicationOrder)resource);
            case AllergyIntolerance: return getAllergyDisplay((AllergyIntolerance)resource);
            case ReferralRequest: return getReferralRequest((ReferralRequest)resource);
            case ProcedureRequest: return getProcedureRequest((ProcedureRequest)resource);
        }

        return "Unknown type";
    }

    private String getProcedureRequest(ProcedureRequest resource) {
        if (resource.getCode().hasText() && resource.hasStatus()) {
            String status = resource.getStatus().getDisplay();
            String orderDate = new SimpleDateFormat("dd/MM/yyyy").format(resource.getOrderedOn());
            return resource.getCode().getText().concat(" (" + status + ":" + orderDate + ")");
        }

        return "";
    }

    private String getReferralRequest(ReferralRequest resource) {
        List<CodeableConcept> services = resource.getServiceRequested();
        if (services != null && services.size() > 0) {
            CodeableConcept service = services.get(0);

            if (service.hasText())
                return service.getText();

            if (service.hasCoding() && service.getCoding().size() > 0)
                return  service.getCoding().get(0).getDisplay();
        }

        return "";
    }

    private String getObservationDisplay(Observation resource) {
        String obsDisplay = "";
        try {
            // the basic display term
            List<Coding> codes = resource.getCode().getCoding();
            if (codes.size() > 0) {
                obsDisplay = codes.get(0).getDisplay();
            }

            // value type? => value + units
            if (resource.getValue()!=null) {
                Quantity qty = resource.getValueQuantity();
                if (qty != null) {
                    obsDisplay = obsDisplay.concat(" " + qty.getValue().toPlainString());
                    String units = qty.getUnit();
                    if (!Strings.isNullOrEmpty(units)) {
                        obsDisplay = obsDisplay.concat(" " + units);
                    }
                }
            }
            // has additional BP value components
            List<Observation.ObservationComponentComponent> comps = resource.getComponent();
            if (comps.size() == 2) {
                obsDisplay = obsDisplay.concat(", BP "+comps.get(0).getValueQuantity().getValue().toPlainString());
                obsDisplay = obsDisplay.concat(" / "+comps.get(1).getValueQuantity().getValue().toPlainString());
            }
            // additional comments/notes
            String comments = resource.getComments();
            if (!Strings.isNullOrEmpty(comments)) {
                obsDisplay = obsDisplay.concat(" ("+comments+")");
            }
        }
        catch (Exception e) {
            obsDisplay = "Error resolving Observation reference";
        }
        return obsDisplay;
    }

    private String getConditionDisplay(Condition resource) {
        String conditionDisplay = "";
        // the basic display term
        List<Coding> codes = resource.getCode().getCoding();
        if (codes.size() > 0) {
            conditionDisplay = codes.get(0).getDisplay();
        }
        // additional comments/notes
        String comments = resource.getNotes();
        if (!Strings.isNullOrEmpty(comments)) {
            conditionDisplay = conditionDisplay.concat(" ("+comments+")");
        }
        return conditionDisplay;
    }

    private String getProcedureDisplay(Procedure resource) {
        String procedureDisplay = "";

        // the basic display term
        List<Coding> codes = resource.getCode().getCoding();
        if (codes.size() > 0) {
            procedureDisplay = codes.get(0).getDisplay();
        }
        // additional comments/notes
        if (resource.getNotes().size() > 0) {
            String comments = resource.getNotes().get(0).getText();
            if (!Strings.isNullOrEmpty(comments)) {
                procedureDisplay = procedureDisplay.concat(" (" + comments + ")");
            }
        }

        return procedureDisplay;
    }

    private String getImmsDisplay(Immunization resource) {
        String immsDisplay = "";
        // the basic display term
        List<Coding> codes = resource.getVaccineCode().getCoding();
        if (codes.size() > 0) {
            immsDisplay = codes.get(0).getDisplay();
        }
        // additional comments/notes
        if (resource.getNote().size() > 0) {
            String comments = resource.getNote().get(0).getText();
            if (!Strings.isNullOrEmpty(comments)) {
                immsDisplay = immsDisplay.concat(" (" + comments + ")");
            }
        }

        return immsDisplay;
    }

    private String getFamilyHistoryDisplay(FamilyMemberHistory resource) {
        String familyHistoryDisplay = "";
        // the basic display term
        List<Coding> codes = resource.getCondition().get(0).getCode().getCoding();
        if (codes.size() > 0) {
            familyHistoryDisplay = codes.get(0).getDisplay();
        }
        // additional comments/notes
        String comments = resource.getNote().getText();
        if (!Strings.isNullOrEmpty(comments)) {
            familyHistoryDisplay = familyHistoryDisplay.concat(" (" + comments + ")");
        }
        return familyHistoryDisplay;
    }

    private String getMedicationStatementDisplay(MedicationStatement resource) {
        String medicationStatementDisplay = "";
        try {
            // the basic display term
            List<Coding> codes = resource.getMedicationCodeableConcept().getCoding();
            if (codes.size() > 0) {
                medicationStatementDisplay = codes.get(0).getDisplay();
            }
            // get dosage
            if (resource.getDosage().size() >0) {
                medicationStatementDisplay = medicationStatementDisplay.concat(" " + resource.getDosage().get(0).getText());
            }
            // additional comments/notes
            String comments = resource.getNote();
            if (!Strings.isNullOrEmpty(comments)) {
                medicationStatementDisplay = medicationStatementDisplay.concat(" ("+comments+")");
            }
        }
        catch (Exception e) {
            medicationStatementDisplay = "Error resolving MedicationStatement reference";
        }
        return medicationStatementDisplay;
    }

    private String getMedicationOrderDisplay(MedicationOrder resource) {
        String medicationOrderDisplay = "";
        try {
            // the basic display term
            List<Coding> codes = resource.getMedicationCodeableConcept().getCoding();
            if (codes.size() > 0) {
                medicationOrderDisplay = codes.get(0).getDisplay();
            }
            // get dosage
            if (resource.getDosageInstruction().size() >0) {
                medicationOrderDisplay = medicationOrderDisplay.concat(" " + resource.getDosageInstruction().get(0).getText());
            }
            // additional comments/notes
            String comments = resource.getNote();
            if (!Strings.isNullOrEmpty(comments)) {
                medicationOrderDisplay = medicationOrderDisplay.concat(" ("+comments+")");
            }
        }
        catch (Exception e) {
            medicationOrderDisplay = "Error resolving MedicationOrder reference";
        }
        return medicationOrderDisplay;
    }

    private String getAllergyDisplay(AllergyIntolerance resource) {
        String allergyDisplay = "";

        // the basic display term
        List<Coding> codes = resource.getSubstance().getCoding();
        if (codes.size() > 0) {
            allergyDisplay = codes.get(0).getDisplay();
        }
        // additional comments/notes
        String comments = resource.getNote().getText();
        if (!Strings.isNullOrEmpty(comments)) {
            allergyDisplay = allergyDisplay.concat(" (" + comments + ")");
        }

        return allergyDisplay;
    }

    private String getHumanName(HumanName humanName) {
        String humanNameStr = humanName.getText();

        if (humanNameStr == null || humanNameStr.isEmpty()) {
            List<StringType> titles = humanName.getPrefix();
            String title = null;
            if (titles != null && titles.size() > 0)
                title = titles.get(0).getValue();

            List<StringType> givenNames = humanName.getGiven();
            String givenName = null;
            if (givenNames != null && givenNames.size() > 0)
                givenName = givenNames.get(0).getValue();

            List<StringType> surnames = humanName.getFamily();
            String surname = null;
            if (surnames != null && surnames.size() > 0)
                surname = surnames.get(0).getValue();

            humanNameStr = new CUIFormatter().getFormattedName(title, givenName, surname);
        }
        return humanNameStr;
    }

    public List<ResourceFieldMapping> getResourceMappings(String serviceId, String resourceType, String resourceId) {
        try {
            return fileMappingDal.findFieldMappings(UUID.fromString(serviceId), org.hl7.fhir.instance.model.ResourceType.valueOf(resourceType), UUID.fromString(resourceId));
        } catch (Exception e) {
            LOG.error("Error getting resource mappings", e);
            return new ArrayList<>();
        }
    }

    public List<ResourceFieldMapping> getResourceMappingsForField(String serviceId, String resourceType, String resourceId, String field) {
        try {
            if (field == null || field.isEmpty())
                return new ArrayList<>();

            List<ResourceFieldMapping> fieldMapping = fileMappingDal.findFieldMappingsForField(UUID.fromString(serviceId), org.hl7.fhir.instance.model.ResourceType.valueOf(resourceType), UUID.fromString(resourceId), field);
            if (fieldMapping != null && fieldMapping.size() > 0)
                return fieldMapping;

            // Try the parent
            Integer idx = field.lastIndexOf('.');
            if (idx > 0)
                field = field.substring(0, idx);

            fieldMapping = fileMappingDal.findFieldMappingsForField(UUID.fromString(serviceId), org.hl7.fhir.instance.model.ResourceType.valueOf(resourceType), UUID.fromString(resourceId), field);

            if (fieldMapping != null)
                return fieldMapping;
            else
                return new ArrayList<>();
        } catch (Exception e) {
            LOG.error("Error getting resource mappings", e);
            return new ArrayList<>();
        }
    }
}
