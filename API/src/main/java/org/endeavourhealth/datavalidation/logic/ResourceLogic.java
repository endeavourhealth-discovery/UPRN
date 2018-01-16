package org.endeavourhealth.datavalidation.logic;

import com.google.common.base.Strings;
import org.endeavourhealth.common.cache.ObjectMapperPool;
import org.endeavourhealth.core.database.dal.ehr.models.ResourceWrapper;
import org.endeavourhealth.datavalidation.dal.ResourceDAL;
import org.endeavourhealth.datavalidation.dal.ResourceDAL_Cassandra;
import org.endeavourhealth.datavalidation.helpers.CUIFormatter;
import org.endeavourhealth.datavalidation.models.PatientResource;
import org.endeavourhealth.datavalidation.models.ResourceId;
import org.endeavourhealth.datavalidation.models.ResourceType;
import org.hl7.fhir.instance.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ResourceLogic {
    static ResourceDAL dal;
    private static final Logger LOG = LoggerFactory.getLogger(ResourceLogic.class);

    public ResourceLogic() {
        if (dal == null)
            dal = new ResourceDAL_Cassandra();
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
            case FamilyMemberHistory: return getFamiltyHistoryDisplay((FamilyMemberHistory)resource);
            case MedicationStatement: return getMedicationStatementDisplay((MedicationStatement)resource);
            case MedicationOrder: return getMedicationOrderDisplay((MedicationOrder)resource);
            case AllergyIntolerance: return getAllergyDisplay((AllergyIntolerance)resource);
        }

        return "Unknown type";
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
                obsDisplay = obsDisplay.concat(" BP "+comps.get(0).getValueQuantity().getValue());
                obsDisplay = obsDisplay.concat(" / "+comps.get(1).getValueQuantity().getValue());
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

    //TODO
    private String getConditionDisplay(Condition resource) {
        String conditionDisplay = "TO DO";
        try {

        }
        catch (Exception e) {
            conditionDisplay = "Error resolving Condition reference";
        }
        return conditionDisplay;
    }

    //TODO
    private String getProcedureDisplay(Procedure resource) {
        String procedureDisplay = "TO DO";
        try {

        }
        catch (Exception e) {
            procedureDisplay = "Error resolving Procedure reference";
        }
        return procedureDisplay;
    }

    //TODO
    private String getImmsDisplay(Immunization resource) {
        String immsDisplay = "TO DO";
        try {

        }
        catch (Exception e) {
            immsDisplay = "Error resolving Immunization reference";
        }
        return immsDisplay;
    }

    //TODO
    private String getFamiltyHistoryDisplay(FamilyMemberHistory resource) {
        String familyHistoryDisplay = "TO DO";
        try {

        }
        catch (Exception e) {
            familyHistoryDisplay = "Error resolving FamilyMemberHistory reference";
        }
        return familyHistoryDisplay;
    }

    //TODO
    private String getMedicationStatementDisplay(MedicationStatement resource) {
        String medicationStatementDisplay = "TO DO";
        try {

        }
        catch (Exception e) {
            medicationStatementDisplay = "Error resolving MedicationStatement reference";
        }
        return medicationStatementDisplay;
    }

    //TODO
    private String getMedicationOrderDisplay(MedicationOrder resource) {
        String medicationOrderDisplay = "TO DO";
        try {

        }
        catch (Exception e) {
            medicationOrderDisplay = "Error resolving MedicationOrder reference";
        }
        return medicationOrderDisplay;
    }

    //TODO
    private String getAllergyDisplay(AllergyIntolerance resource) {
        String allergyDisplay = "TO DO";
        try {

            // additional comments/notes
            String comments = resource.getNote().getText();
            if (!Strings.isNullOrEmpty(comments)) {
                allergyDisplay = allergyDisplay.concat(" ("+comments+")");
            }
        }
        catch (Exception e) {
            allergyDisplay = "Error resolving AllergyIntolerance reference";
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
}
