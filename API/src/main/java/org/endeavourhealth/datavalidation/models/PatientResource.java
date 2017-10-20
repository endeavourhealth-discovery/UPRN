package org.endeavourhealth.datavalidation.models;

import com.fasterxml.jackson.databind.JsonNode;

public class PatientResource {
    private String serviceId;
    private String systemId;
    private String patientId;
    private JsonNode resourceJson;

    public PatientResource() {}

    public PatientResource(String serviceId, String systemId, String patientId, JsonNode resourceJson) {
        this.serviceId = serviceId;
        this.systemId = systemId;
        this.patientId = patientId;
        this.resourceJson = resourceJson;
    }

    public String getPatientId() {
        return patientId;
    }

    public PatientResource setPatientId(String patientId) {
        this.patientId = patientId;
        return this;
    }

    public String getServiceId() {
        return serviceId;
    }

    public PatientResource setServiceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public JsonNode getResourceJson() {
        return resourceJson;
    }

    public PatientResource setResourceJson(JsonNode resourceJson) {
        this.resourceJson = resourceJson;
        return this;
    }

    public String getSystemId() {
        return systemId;
    }

    public PatientResource setSystemId(String systemId) {
        this.systemId = systemId;
        return this;
    }
}
