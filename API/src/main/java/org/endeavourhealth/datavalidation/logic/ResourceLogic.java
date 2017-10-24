package org.endeavourhealth.datavalidation.logic;

import org.endeavourhealth.common.cache.ObjectMapperPool;
import org.endeavourhealth.datavalidation.dal.ResourceDAL;
import org.endeavourhealth.datavalidation.dal.ResourceDAL_Cassandra;
import org.endeavourhealth.datavalidation.helpers.CUIFormatter;
import org.endeavourhealth.datavalidation.models.Patient;
import org.endeavourhealth.datavalidation.models.ResourceId;
import org.endeavourhealth.datavalidation.models.ResourceType;
import org.endeavourhealth.datavalidation.models.PatientResource;
import org.hl7.fhir.instance.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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

        for(ResourceId patient : patients) {

            if (serviceIds.contains(patient.getServiceId())) {
                List<String> resourceStrings = dal.getPatientResources(
                    patient.getServiceId(),
                    patient.getSystemId(),
                    patient.getPatientId(),
                    resourceTypes);

                ObjectMapperPool parserPool = ObjectMapperPool.getInstance();

                for (String resourceString : resourceStrings)
                    resourceObjects.add(
                        new PatientResource(
                            patient.getServiceId(),
                            patient.getSystemId(),
                            patient.getPatientId(),
                            parserPool.readTree(resourceString))
                    );
            }
        }

        return resourceObjects;
    }

    public String getReferenceDescription(String reference) {
        int slashPos = reference.indexOf('/');
        if (slashPos == -1)
            return "Invalid reference";

        String resourceTypeStr = reference.substring(0, slashPos);
        org.hl7.fhir.instance.model.ResourceType resourceType = org.hl7.fhir.instance.model.ResourceType.valueOf(resourceTypeStr);
        String resourceId = reference.substring(slashPos + 1);

        Resource resource = dal.getResource(resourceType, resourceId);
        if (resource == null)
            return "Not Found";

        switch (resourceType) {
            case Organization: return ((Organization)resource).getName();
            case Practitioner: return getHumanName(((Practitioner)resource).getName());
        }

        return "Unknown Type";
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
