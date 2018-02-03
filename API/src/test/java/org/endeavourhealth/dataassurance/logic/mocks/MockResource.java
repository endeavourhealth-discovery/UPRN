package org.endeavourhealth.dataassurance.logic.mocks;

import org.hl7.fhir.instance.model.Resource;
import org.hl7.fhir.instance.model.ResourceType;

public class MockResource extends Resource {
    private ResourceType resourceType;

    public MockResource(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public Resource copy() {
        return null;
    }

    @Override
    public ResourceType getResourceType() {
        return resourceType;
    }
}
