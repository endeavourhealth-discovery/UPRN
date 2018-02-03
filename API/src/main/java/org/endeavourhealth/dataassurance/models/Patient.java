package org.endeavourhealth.dataassurance.models;

import java.util.HashMap;
import java.util.Map;

public class Patient {
    private ResourceId id;
    private String patientName;
    private String dob;
    private Map<String, String> localIds = new HashMap<>();

    public Patient() {}

    public Patient(String serviceId, String systemId, String patientId, String patientName, String dob) {
        this.id = new ResourceId()
            .setServiceId(serviceId)
            .setSystemId(systemId)
            .setPatientId(patientId);
        this.patientName = patientName;
        this.dob = dob;
    }

    public ResourceId getId() {
        return id;
    }

    public Patient setId(ResourceId id) {
        this.id = id;
        return this;
    }

    public String getPatientName() {
        return patientName;
    }

    public Patient setPatientName(String patientName) {
        this.patientName = patientName;
        return this;
    }

    public String getDob() {
        return dob;
    }

    public Patient setDob(String dob) {
        this.dob = dob;
        return this;
    }

    public Map<String, String> getLocalIds() {
        return localIds;
    }

    public Patient setLocalIds(Map<String, String> localIds) {
        this.localIds = localIds;
        return this;
    }
}
