package org.endeavourhealth.datavalidation.dal;

import org.endeavourhealth.core.rdbms.eds.EdsConnection;
import org.endeavourhealth.core.rdbms.eds.PatientSearch;
import org.endeavourhealth.core.rdbms.eds.PatientSearchHelper;
import org.endeavourhealth.coreui.framework.ContextShutdownHook;
import org.endeavourhealth.coreui.framework.StartupConfig;
import org.endeavourhealth.datavalidation.logic.CUIFormatter;
import org.endeavourhealth.datavalidation.models.Patient;
import org.endeavourhealth.datavalidation.models.Person;
import org.endeavourhealth.datavalidation.models.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PersonPatientDAL_Legacy implements PersonPatientDAL, ContextShutdownHook {
    private static final Logger LOG = LoggerFactory.getLogger(PersonPatientDAL_Legacy.class);

    public PersonPatientDAL_Legacy() {
        StartupConfig.registerShutdownHook("PersonPatientDAL_Legacy", this);
    }

    public List<Person> searchByNhsNumber(Set<String> serviceIds, String nhsNumber) {
        try {
            return buildPersonResultList(serviceIds, PatientSearchHelper.searchByNhsNumber(serviceIds, nhsNumber));
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
        }
        return new ArrayList<>();
    }

    public List<Person> searchByLocalId(Set<String> serviceIds, String emisNumber) {
        try {
            return buildPersonResultList(serviceIds, PatientSearchHelper.searchByLocalId(serviceIds, emisNumber));
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
        }
        return new ArrayList<>();
    }

    public List<Person> searchByDateOfBirth(Set<String> serviceIds, Date dateOfBirth) {
        try {
            return buildPersonResultList(serviceIds, PatientSearchHelper.searchByDateOfBirth(serviceIds, dateOfBirth));
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
        }
        return new ArrayList<>();
    }

    public List<Person> searchByNames(Set<String> serviceIds, List<String> names) {
        try {
            return buildPersonResultList(serviceIds, PatientSearchHelper.searchByNames(serviceIds, names));
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
        }
        return new ArrayList<>();
    }

    public List<Patient> getPatientsByNhsNumber(Set<String> serviceIds, String nhsNumber) {
        try {
            return buildPatientsFromPatientSearches(serviceIds, PatientSearchHelper.searchByNhsNumber(serviceIds, nhsNumber));
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
        }
        return new ArrayList<>();
    }

    private List<Person> buildPersonResultList(Set<String> serviceIds, List<PatientSearch> patientSearchList) {
        List<String> addedNhsNumbers = new ArrayList<>();
        List<Person> result = new ArrayList<>();

        for (PatientSearch searchPatient : patientSearchList) {
            if (serviceIds.contains(searchPatient.getServiceId())) {
                if (!addedNhsNumbers.contains(searchPatient.getNhsNumber())) {
                    if (searchPatient.getNhsNumber() != null && !searchPatient.getNhsNumber().isEmpty())
                        addedNhsNumbers.add(searchPatient.getNhsNumber());

                    Person person = new Person()
                        .setName(CUIFormatter.getFormattedName(
                            null,
                            searchPatient.getForenames(),
                            searchPatient.getSurname()
                        ))
                        .setPatientCount(1)
                        .setNhsNumber(searchPatient.getNhsNumber());

                    result.add(person);
                }
            }
        }

        return result;
    }

    private List<Patient> buildPatientsFromPatientSearches(Set<String> organisationIds, List<PatientSearch> patientSearchList) {
        List<Patient> result = new ArrayList<>();

        for (PatientSearch searchPatient : patientSearchList) {
            if (organisationIds.contains(searchPatient.getServiceId())) {
                Patient patient = new Patient()
                    .setId(UUID.fromString(searchPatient.getPatientId()))
                    .setName(CUIFormatter.getFormattedName(
                        null,
                        searchPatient.getForenames(),
                        searchPatient.getSurname()
                    ))
                    .setDob(searchPatient.getDateOfBirth().toString())
                    .setService(
                        new Service()
                            .setId(UUID.fromString(searchPatient.getServiceId()))
                            .setName(searchPatient.getServiceId())
                            .setType(searchPatient.getServiceId())
                    );

                result.add(patient);
            }
        }

        return result;
    }
    @Override
    public void contextShutdown() {
        EdsConnection.shutdown();
    }
}
