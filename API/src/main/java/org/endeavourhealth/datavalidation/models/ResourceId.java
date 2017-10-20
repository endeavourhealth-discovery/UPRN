package org.endeavourhealth.datavalidation.models;

public class ResourceId {
    protected String serviceId;
    protected String systemId;
    protected String patientId;


    public String getPatientId() {
        return patientId;
    }

    public ResourceId setPatientId(String patientId) {
        this.patientId = patientId;
        return this;
    }

    public String getServiceId() {
        return serviceId;
    }

    public ResourceId setServiceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String getSystemId() {
        return systemId;
    }

    public ResourceId setSystemId(String systemId) {
        this.systemId = systemId;
        return this;
    }

}
