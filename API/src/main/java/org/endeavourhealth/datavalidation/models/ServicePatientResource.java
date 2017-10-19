package org.endeavourhealth.datavalidation.models;

import com.fasterxml.jackson.databind.JsonNode;

public class ServicePatientResource {
    private String patientId;
    private String serviceId;
    private JsonNode resourceJson;

    public String getPatientId() {
        return patientId;
    }

    public ServicePatientResource setPatientId(String patientId) {
        this.patientId = patientId;
        return this;
    }

    public String getServiceId() {
        return serviceId;
    }

    public ServicePatientResource setServiceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public JsonNode getResourceJson() {
        return resourceJson;
    }

    public ServicePatientResource setResourceJson(JsonNode resourceJson) {
        this.resourceJson = resourceJson;
        return this;
    }
}
