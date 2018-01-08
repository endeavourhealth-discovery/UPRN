package org.endeavourhealth.datavalidation.dal;

import com.fasterxml.jackson.core.type.TypeReference;
import org.endeavourhealth.common.cache.ObjectMapperPool;
import org.endeavourhealth.core.database.dal.DalProvider;
import org.endeavourhealth.core.database.dal.admin.ServiceDalI;
import org.endeavourhealth.core.database.dal.admin.models.Service;
import org.endeavourhealth.core.database.dal.ehr.ResourceDalI;
import org.endeavourhealth.core.database.dal.ehr.models.ResourceWrapper;
import org.endeavourhealth.core.fhirStorage.JsonServiceInterfaceEndpoint;
import org.endeavourhealth.datavalidation.models.ResourceType;
import org.hl7.fhir.instance.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        resourceTypes.add(new ResourceType("Immunization", "Immunization"));
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
    public List<ResourceWrapper> getPatientResources(String serviceId, String systemId, String patientId, List<String> resourceTypes) {
        List<ResourceWrapper> resources = new ArrayList<>();

        try {
            for (String resourceType : resourceTypes) {
                List<ResourceWrapper> resourcesByType = getPatientResourcesByType(serviceId, systemId, patientId, resourceType);

                if (resourcesByType != null && resourcesByType.size() > 0)
                    resources.addAll(resourcesByType);
            }

            return resources;
        } catch (Exception ex) {
            LOG.error("Error fetching resource", ex);
            return null;
        }
    }

    @Override
    public Resource getResource(org.hl7.fhir.instance.model.ResourceType resourceType, String resourceId, String serviceId) {
        ResourceDalI resourceRepository = DalProvider.factoryResourceDal();
        try {

            return resourceRepository.getCurrentVersionAsResource(UUID.fromString(serviceId), resourceType, resourceId);
        } catch (Exception e) {
            LOG.error("Error fetching resource", e);
            return null;
        }
    }

    private List<ResourceWrapper> getPatientResourcesByType(String serviceId, String systemId, String patientId, String resourceType) throws Exception {
        ResourceDalI resourceRepository = DalProvider.factoryResourceDal();

        return resourceRepository.getResourcesByPatient(
                UUID.fromString(serviceId),
                UUID.fromString(systemId),
                UUID.fromString(patientId),
                resourceType);

//        return resourceRepository.getResourcesByPatientAllSystems(
//            UUID.fromString(serviceId),
//            UUID.fromString(patientId),
//            resourceType);
    }

    @Override
    public List<UUID> getServiceSystems(String serviceId) {
        ServiceDalI serviceRepository = DalProvider.factoryServiceDal();
        try {
            Service service = serviceRepository.getById(UUID.fromString(serviceId));
            List<UUID> systemIds = findSystemIds(service);
            return systemIds;
        } catch (Exception e) {
            LOG.error("Error fetching service systems", e);
            return null;
        }
    }

    private static List<UUID> findSystemIds(Service service) throws Exception {
        List<UUID> ret = new ArrayList<>();

        List<JsonServiceInterfaceEndpoint> endpoints = null;
        try {
            endpoints = ObjectMapperPool.getInstance().readValue(service.getEndpoints(), new TypeReference<List<JsonServiceInterfaceEndpoint>>() {});
            for (JsonServiceInterfaceEndpoint endpoint: endpoints) {
                UUID endpointSystemId = endpoint.getSystemUuid();
                ret.add(endpointSystemId);
            }
        } catch (Exception e) {
            throw new Exception("Failed to get system endpoints from service " + service.getId());
        }

        return ret;
    }
}
