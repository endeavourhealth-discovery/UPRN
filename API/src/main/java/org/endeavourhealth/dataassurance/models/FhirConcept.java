package org.endeavourhealth.dataassurance.models;

public final class FhirConcept {
    private String id;
    private String preferredTerm;

    public FhirConcept(org.endeavourhealth.core.database.dal.reference.models.Concept conceptEntity) {
        this.id = conceptEntity.getCode();
        this.preferredTerm = conceptEntity.getDisplay();
    }

    /**
     * gets/sets
     */


    public String getPreferredTerm() {
        return preferredTerm;
    }

    public void setPreferredTerm(String preferredTerm) {
        this.preferredTerm = preferredTerm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
