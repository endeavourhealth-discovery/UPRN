package org.endeavourhealth.dataassurance.models;

public abstract class UPRNAddress {

     // Model attributes
    // Unique property reference number
    protected long uprn;
    // Unique dependent property reference number
    protected long udprn;

    public UPRNAddress()
    {

    }

    public UPRNAddress(long uprn, long udprn) {

        this.uprn = uprn;
        this.udprn = udprn;

    }

    public long getUprn() {
        return uprn;
    }

    public void setUprn(long uprn) {
        this.uprn = uprn;
    }

    public long getUdprn() {
        return udprn;
    }

    public void setUdprn(long udprn) {
        this.udprn = udprn;
    }

    // Abstract behaviour for the Address sub-classes to implement
    abstract public boolean isMatched(UPRNPatientResource patientResource);

    abstract public String getDisplayableAddress();


    public String toString() {
        String str= "uprn: " + uprn +
                "udprn: " + udprn;

        return str;
    }
}
