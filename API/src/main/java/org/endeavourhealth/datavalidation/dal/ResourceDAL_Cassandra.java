package org.endeavourhealth.datavalidation.dal;

import org.endeavourhealth.core.data.ehr.ResourceRepository;
import org.endeavourhealth.core.data.ehr.models.ResourceByPatient;
import org.endeavourhealth.datavalidation.models.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class ResourceDAL_Cassandra implements ResourceDAL {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceDAL_Cassandra.class);

    @Override
    public List<ResourceType> getResourceTypes() {
        List<ResourceType> resourceTypes = new ArrayList<>();

        resourceTypes.add(new ResourceType().setId("EpisodeOfCare").setName("Episode Of Care"));
        resourceTypes.add(new ResourceType().setId("Encounter").setName("Encounter"));
        resourceTypes.add(new ResourceType().setId("Observation").setName("Observation"));

        return resourceTypes;
    }

    @Override
    public List<String> getPatientResources(String patientId, String serviceId, List<String> resourceTypes) {
        List<String> resources = new ArrayList<>();

        for (String resourceType : resourceTypes) {
            List<String> resourcesByType = getPatientResourcesByType(patientId, serviceId, resourceType);

            if (resourcesByType != null && resourcesByType.size() > 0)
                resources.addAll(resourcesByType);
        }

        return resources;
    }

    private List<String> getPatientResourcesByType(String patientId, String serviceId, String resourceType) {
        ResourceRepository resourceRepository = new ResourceRepository();
        List<ResourceByPatient> resourceByPatientList = resourceRepository.getResourcesByPatientAllSystems(UUID.fromString(serviceId), UUID.fromString(patientId), resourceType);

        List<String> resources = resourceByPatientList
            .stream()
            .map(resource -> resource.getResourceData())
            .collect(Collectors.toList());

        return resources;
    }
}
