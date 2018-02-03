package org.endeavourhealth.dataassurance.models;

import java.util.List;

public class ResourceRequest {
    private List<ResourceId> patients;
    private List<String> resourceTypes;

    public ResourceRequest() {}

    public List<ResourceId> getPatients() {
        return patients;
    }

    public ResourceRequest setPatients(List<ResourceId> patients) {
        this.patients = patients;
        return this;
    }

    public List<String> getResourceTypes() {
        return resourceTypes;
    }

    public ResourceRequest setResourceTypes(List<String> resourceTypes) {
        this.resourceTypes = resourceTypes;
        return this;
    }
}
