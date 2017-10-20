package org.endeavourhealth.datavalidation.logic;

import org.endeavourhealth.common.cache.ObjectMapperPool;
import org.endeavourhealth.datavalidation.dal.ResourceDAL;
import org.endeavourhealth.datavalidation.dal.ResourceDAL_Cassandra;
import org.endeavourhealth.datavalidation.models.Patient;
import org.endeavourhealth.datavalidation.models.ResourceId;
import org.endeavourhealth.datavalidation.models.ResourceType;
import org.endeavourhealth.datavalidation.models.PatientResource;
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
}
