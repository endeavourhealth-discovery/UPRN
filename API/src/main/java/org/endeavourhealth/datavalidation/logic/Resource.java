package org.endeavourhealth.datavalidation.logic;

import org.endeavourhealth.common.cache.ObjectMapperPool;
import org.endeavourhealth.datavalidation.dal.ResourceDAL;
import org.endeavourhealth.datavalidation.dal.ResourceDAL_Cassandra;
import org.endeavourhealth.datavalidation.models.ResourceType;
import org.endeavourhealth.datavalidation.models.ServicePatientResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Resource {
    static ResourceDAL dal = new ResourceDAL_Cassandra();
    private static final Logger LOG = LoggerFactory.getLogger(Resource.class);

    public static List<ResourceType> getTypes() {
        return dal.getResourceTypes();
    }

    public static List<ServicePatientResource> getServicePatientResources(Set<String> serviceIds, String servicePatientId, List<String> resourceTypes) throws Exception {
        String serviceId = servicePatientId.substring(0, 36);
        String patientId = servicePatientId.substring(36, 72);

        if (!serviceIds.contains(serviceId))
            return new ArrayList<>();

        List<String> resourceStrings = dal.getPatientResources(patientId, serviceId, resourceTypes);

        ObjectMapperPool parserPool = ObjectMapperPool.getInstance();

        List<ServicePatientResource> resourceObjects = new ArrayList<>();
        for(String resourceString : resourceStrings)
            resourceObjects.add(
                new ServicePatientResource()
                .setPatientId(patientId)
                .setServiceId(serviceId)
                .setResourceJson(parserPool.readTree(resourceString))
            );


        return resourceObjects;
    }
}
