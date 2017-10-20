package org.endeavourhealth.datavalidation.models;

public class ResourceType {
    private String id;
    private String name;

    public ResourceType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public ResourceType setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ResourceType setName(String name) {
        this.name = name;
        return this;
    }
}
