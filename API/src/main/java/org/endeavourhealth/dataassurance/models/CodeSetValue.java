package org.endeavourhealth.dataassurance.models;

import org.endeavourhealth.core.database.dal.reference.models.Concept;

public final class CodeSetValue {
    private String code;
    private String term;

    public CodeSetValue(Concept conceptEntity) {
        this.code = conceptEntity.getCode();
        this.term = conceptEntity.getDisplay();
    }

    /**
     * gets/sets
     */

    public String getCode() {
        return code;
    }

    public CodeSetValue setCode(String code) {
        this.code = code;
        return this;
    }

    public String getTerm() {
        return term;
    }

    public CodeSetValue setTerm(String term) {
        this.term = term;
        return this;
    }
}
