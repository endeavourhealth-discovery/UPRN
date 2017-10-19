package org.endeavourhealth.datavalidation.logic;

import org.endeavourhealth.common.cache.ObjectMapperPool;
import org.endeavourhealth.datavalidation.dal.ResourceDAL;
import org.endeavourhealth.datavalidation.dal.ResourceDAL_Cassandra;
import org.endeavourhealth.datavalidation.models.ResourceType;
import org.endeavourhealth.datavalidation.models.ServicePatientResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ResourceLogic {
    ResourceDAL dal;
    private static final Logger LOG = LoggerFactory.getLogger(ResourceLogic.class);

    public ResourceLogic() {
        dal = new ResourceDAL_Cassandra();
    }

    ResourceLogic(ResourceDAL dal) {
        this.dal = dal;
    }

    public List<ResourceType> getTypes() {
        return dal.getResourceTypes();
    }

    public List<ServicePatientResource> getServicePatientResources(Set<String> serviceIds, List<String> servicePatientIds, List<String> resourceTypes) throws Exception {
        List<ServicePatientResource> resourceObjects = new ArrayList<>();

        for(String servicePatientId : servicePatientIds) {
            String serviceId = servicePatientId.substring(0, 36);
            String patientId = servicePatientId.substring(36, 72);

            if (serviceIds.contains(serviceId)) {
                List<String> resourceStrings = dal.getPatientResources(patientId, serviceId, resourceTypes);

                ObjectMapperPool parserPool = ObjectMapperPool.getInstance();

                for (String resourceString : resourceStrings)
                    resourceObjects.add(
                        new ServicePatientResource()
                            .setPatientId(patientId)
                            .setServiceId(serviceId)
                            .setResourceJson(parserPool.readTree(resourceString))
                    );
            }
        }

        return resourceObjects;
    }
}
