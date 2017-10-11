package org.endeavourhealth.datavalidation.models;

import java.util.UUID;

public class Service {
    private UUID id;
    private String name;
    private String type;

    public UUID getId() {
        return id;
    }

    public Service setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Service setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public Service setType(String type) {
        this.type = type;
        return this;
    }
}
