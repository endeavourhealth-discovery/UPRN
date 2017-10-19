package org.endeavourhealth.datavalidation.models;

import java.util.UUID;

public class Patient {
    private String id;
    private Service service;
    private UUID patientId;
    private String name;
    private String dob;

    public String getId() {
        return id;
    }

    public Patient setId(String id) {
        this.id = id;
        return this;
    }

    public Service getService() {
        return service;
    }

    public Patient setService(Service service) {
        this.service = service;
        return this;
    }

    public String getName() {
        return name;
    }

    public Patient setName(String name) {
        this.name = name;
        return this;
    }

    public String getDob() {
        return dob;
    }

    public Patient setDob(String dob) {
        this.dob = dob;
        return this;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public Patient setPatientId(UUID patientId) {
        this.patientId = patientId;
        return this;
    }
}
