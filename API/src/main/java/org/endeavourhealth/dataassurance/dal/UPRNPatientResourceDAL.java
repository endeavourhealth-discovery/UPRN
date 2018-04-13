package org.endeavourhealth.dataassurance.dal;

import org.endeavourhealth.dataassurance.models.UPRNPatientResource;

import java.util.List;

public interface UPRNPatientResourceDAL {

    // Retrieve Patient resource subset by Address Line 1
    List<UPRNPatientResource> searchByPatientAddr1(String filter);

    // Retrieve Patient resource subset by Postcode
    List<UPRNPatientResource> searchByPatientPostcode(String filter);

    // Update patient who matches address criteria with ABP UPRN
    boolean updatePatientUPRN(UPRNPatientResource patientResource, String uprn);
}
