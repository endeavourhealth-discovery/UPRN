package org.endeavourhealth.datavalidation.models;

public class Person {
    private String nhsNumber;
    private String name;
    private Integer patientCount;

    public String getNhsNumber() {
        return nhsNumber;
    }

    public Person setNhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
        return this;
    }

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getPatientCount() {
        return patientCount;
    }

    public Person setPatientCount(Integer patientCount) {
        this.patientCount = patientCount;
        return this;
    }
}
