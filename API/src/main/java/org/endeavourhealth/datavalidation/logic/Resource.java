package org.endeavourhealth.datavalidation.logic;

import com.fasterxml.jackson.databind.JsonNode;
import org.endeavourhealth.common.cache.ObjectMapperPool;
import org.endeavourhealth.datavalidation.dal.ResourceDAL;
import org.endeavourhealth.datavalidation.dal.ResourceDAL_Cassandra;
import org.endeavourhealth.datavalidation.models.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Resource {
    static ResourceDAL dal = new ResourceDAL_Cassandra();
    private static final Logger LOG = LoggerFactory.getLogger(Resource.class);

    public static List<ResourceType> getTypes() {
        return dal.getResourceTypes();
    }

    public static List<JsonNode> getPatientResources(Set<String> serviceIds, String patientId, String serviceId, List<String> resourceTypes) throws Exception {
        if (!serviceIds.contains(serviceId))
            return new ArrayList<>();

        List<String> resourcesJson = dal.getPatientResources(patientId, serviceId, resourceTypes);

        ObjectMapperPool parserPool = ObjectMapperPool.getInstance();

        List<JsonNode> resourceObjects = new ArrayList<>();
        for(String resourceJson : resourcesJson)
            resourceObjects.add(parserPool.readTree(resourceJson));

        return resourceObjects;
    }
}
