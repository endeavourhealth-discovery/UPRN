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
        // Hard-coded list as you cant do a simple distinct in cassandra!

        resourceTypes.add(new ResourceType().setId("AllergyIntolerance").setName("Allergy/Intolerance"));
        resourceTypes.add(new ResourceType().setId("Condition").setName("Condition"));
        resourceTypes.add(new ResourceType().setId("DiagnosticOrder").setName("Diagnostic Order"));
        resourceTypes.add(new ResourceType().setId("DiagnosticReport").setName("Diagnostic Report"));
        resourceTypes.add(new ResourceType().setId("ProcedureRequest").setName("Procedure Request"));
        resourceTypes.add(new ResourceType().setId("Encounter").setName("Encounter"));
        resourceTypes.add(new ResourceType().setId("EpisodeOfCare").setName("Episode Of Care"));
        resourceTypes.add(new ResourceType().setId("FamilyMemberHistory").setName("Family Member History"));
        resourceTypes.add(new ResourceType().setId("Immunisation").setName("Immunisation"));
        resourceTypes.add(new ResourceType().setId("MedicationOrder").setName("Medication Order"));
        resourceTypes.add(new ResourceType().setId("MedicationStatement").setName("Medication Statement"));
        resourceTypes.add(new ResourceType().setId("Medication").setName("Medication"));
        resourceTypes.add(new ResourceType().setId("Observation").setName("Observation"));
        resourceTypes.add(new ResourceType().setId("Procedure").setName("Procedure"));
        resourceTypes.add(new ResourceType().setId("ReferralRequest").setName("Referral Request"));
        resourceTypes.add(new ResourceType().setId("Specimen").setName("Specimen"));

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
