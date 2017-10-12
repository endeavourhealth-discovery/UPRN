package org.endeavourhealth.datavalidation.dal;

import org.endeavourhealth.core.rdbms.eds.PatientSearch;
import org.endeavourhealth.core.rdbms.eds.PatientSearchHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class PersonPatientDAL_Legacy implements PersonPatientDAL {
    private static final Logger LOG = LoggerFactory.getLogger(PersonPatientDAL_Legacy.class);

    public List<PatientSearch> searchByNhsNumber(Set<String> organisationIds, String nhsNumber) {
        try {
            return PatientSearchHelper.searchByNhsNumber(organisationIds, nhsNumber);
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
        }
        return new ArrayList<>();
    }

    public List<PatientSearch> searchByLocalId(Set<String> organisationIds, String emisNumber) {
        try {
            return PatientSearchHelper.searchByLocalId(organisationIds, emisNumber);
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
        }
        return new ArrayList<>();
    }

    public List<PatientSearch> searchByDateOfBirth(Set<String> organisationIds, Date dateOfBirth) {
        try {
            return PatientSearchHelper.searchByDateOfBirth(organisationIds, dateOfBirth);
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
        }
        return new ArrayList<>();
    }

    public List<PatientSearch> searchByNames(Set<String> organisationIds, List<String> names) {
        try {
            return PatientSearchHelper.searchByNames(organisationIds, names);
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
        }
        return new ArrayList<>();
    }
}
