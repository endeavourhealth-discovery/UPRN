package org.endeavourhealth.datavalidation.dal;

import org.endeavourhealth.core.rdbms.eds.PatientSearch;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface PersonPatientDAL {
    List<PatientSearch> searchByNhsNumber(Set<String> organisationIds, String nhsNumber);
    List<PatientSearch> searchByLocalId(Set<String> organisationIds, String emisNumber);
    List<PatientSearch> searchByDateOfBirth(Set<String> organisationIds, Date dateOfBirth);
    List<PatientSearch> searchByNames(Set<String> organisationIds, List<String> names);
}
