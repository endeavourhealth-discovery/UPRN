package org.endeavourhealth.datavalidation.logic.mocks;

import org.endeavourhealth.core.database.dal.ehr.models.ResourceWrapper;
import org.endeavourhealth.datavalidation.dal.ResourceDAL;
import org.endeavourhealth.datavalidation.models.ResourceType;
import org.hl7.fhir.instance.model.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Mock_ResourceDAL implements ResourceDAL {
    public String MISSING_REFERENCE = "Patient/" + UUID.randomUUID().toString();
    public String VALID_REFERENCE = "Organization/" + UUID.randomUUID().toString();
    public String UNKNOWN_REFERENCE = "TestScript/" + UUID.randomUUID().toString();

    @Override
    public List<ResourceType> getResourceTypes() {
        return null;
    }

    @Override
    public List<ResourceWrapper> getPatientResources(String serviceId, String systemId, String patientId, List<String> resourceTypes) {
        List<ResourceWrapper> resources = new ArrayList<>();

        ResourceWrapper resourceWrapper = new ResourceWrapper();
        resourceWrapper.setServiceId(UUID.fromString(serviceId));
        resourceWrapper.setSystemId(UUID.fromString(systemId));
        resourceWrapper.setPatientId(UUID.fromString(patientId));
        resourceWrapper.setResourceData("{ \"resourceType\": \"Condition\" }");

        resources.add(resourceWrapper);

        return resources;
    }

    @Override
    public Resource getResource(org.hl7.fhir.instance.model.ResourceType resourceType, String resourceId) {
        String reference = resourceType.name() + "/" + resourceId;

        if (MISSING_REFERENCE.equals(reference))
            return null;

        if (VALID_REFERENCE.equals(reference))
            return new MockResource(org.hl7.fhir.instance.model.ResourceType.Organization);

        if (UNKNOWN_REFERENCE.equals(reference))
            return new MockResource(org.hl7.fhir.instance.model.ResourceType.TestScript);

        throw new IllegalArgumentException("Invalid test data used for mock!");
    }
}
