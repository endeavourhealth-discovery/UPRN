package org.endeavourhealth.datavalidation.models;

import java.util.UUID;

public class Patient {
    private UUID id;
    private Service service;
    private String name;
    private String dob;

    public UUID getId() {
        return id;
    }

    public Patient setId(UUID id) {
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
}
