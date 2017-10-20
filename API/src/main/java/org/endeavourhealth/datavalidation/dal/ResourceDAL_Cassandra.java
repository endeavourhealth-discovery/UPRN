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

        resourceTypes.add(new ResourceType("AllergyIntolerance", "Allergy/Intolerance"));
        resourceTypes.add(new ResourceType("Condition", "Condition"));
        resourceTypes.add(new ResourceType("DiagnosticOrder", "Diagnostic Order"));
        resourceTypes.add(new ResourceType("DiagnosticReport", "Diagnostic Report"));
        resourceTypes.add(new ResourceType("ProcedureRequest", "Procedure Request"));
        resourceTypes.add(new ResourceType("Encounter", "Encounter"));
        resourceTypes.add(new ResourceType("EpisodeOfCare", "Episode Of Care"));
        resourceTypes.add(new ResourceType("FamilyMemberHistory", "Family Member History"));
        resourceTypes.add(new ResourceType("Immunisation", "Immunisation"));
        resourceTypes.add(new ResourceType("MedicationOrder", "Medication Order"));
        resourceTypes.add(new ResourceType("MedicationStatement", "Medication Statement"));
        resourceTypes.add(new ResourceType("Medication", "Medication"));
        resourceTypes.add(new ResourceType("Observation", "Observation"));
        resourceTypes.add(new ResourceType("Procedure", "Procedure"));
        resourceTypes.add(new ResourceType("ReferralRequest", "Referral Request"));
        resourceTypes.add(new ResourceType("Specimen", "Specimen"));

        return resourceTypes;
    }

    @Override
    public List<String> getPatientResources(String serviceId, String systemId, String patientId, List<String> resourceTypes) {
        List<String> resources = new ArrayList<>();

        for (String resourceType : resourceTypes) {
            List<String> resourcesByType = getPatientResourcesByType(serviceId, systemId, patientId, resourceType);

            if (resourcesByType != null && resourcesByType.size() > 0)
                resources.addAll(resourcesByType);
        }

        return resources;
    }

    private List<String> getPatientResourcesByType(String serviceId, String systemId, String patientId, String resourceType) {
        ResourceRepository resourceRepository = new ResourceRepository();
        List<ResourceByPatient> resourceByPatientList = resourceRepository.getResourcesByPatient(
            UUID.fromString(serviceId),
            UUID.fromString(systemId),
            UUID.fromString(patientId),
            resourceType);

        List<String> resources = resourceByPatientList
            .stream()
            .map(resource -> resource.getResourceData())
            .collect(Collectors.toList());

        return resources;
    }
}
