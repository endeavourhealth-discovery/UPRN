package org.endeavourhealth.datavalidation.logic;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.core.rdbms.eds.PatientSearch;
import org.endeavourhealth.datavalidation.dal.PersonPatientDAL;
import org.endeavourhealth.datavalidation.dal.PersonPatientDAL_Legacy;
import org.endeavourhealth.datavalidation.models.Patient;
import org.endeavourhealth.datavalidation.models.Person;
import org.endeavourhealth.datavalidation.models.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PersonPatient {
    static PersonPatientDAL dal = new PersonPatientDAL_Legacy();

    public static List<Person> findPersonsInOrganisations(Set<String> organisationIds, String searchTerms) throws Exception {
        List<Person> result = new ArrayList<>();

        if (!StringUtils.isEmpty(searchTerms)) {

            SearchTermsParser parser = new SearchTermsParser(searchTerms);

            List<PatientSearch> patientsFound = new ArrayList<>();

            if (parser.hasNhsNumber())
                patientsFound.addAll(dal.searchByNhsNumber(organisationIds, parser.getNhsNumber()));

            if (parser.hasEmisNumber())
                patientsFound.addAll(dal.searchByLocalId(organisationIds, parser.getEmisNumber()));

            if (parser.hasDateOfBirth())
                patientsFound.addAll(dal.searchByDateOfBirth(organisationIds, parser.getDateOfBirth()));

            patientsFound.addAll(dal.searchByNames(organisationIds, parser.getNames()));

            result = buildPersonResultList(organisationIds, patientsFound);
        }

        return result;
    }

    public static List<Patient> getPatientsForPerson(Set<String> organisationIds, String personId) throws Exception {
        List<PatientSearch> patients = dal.searchByNhsNumber(organisationIds, personId);

        return PersonPatient.buildPatientsFromPatientSearches(organisationIds, patients);
    }

    private static List<Patient> buildPatientsFromPatientSearches(Set<String> organisationIds, List<PatientSearch> patientSearchList) {
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

    static List<Person> buildPersonResultList(Set<String> organisationIds, List<PatientSearch> patientSearchList) {
        List<String> addedNhsNumbers = new ArrayList<>();
        List<Person> result = new ArrayList<>();

        for (PatientSearch searchPatient : patientSearchList) {
            if (organisationIds.contains(searchPatient.getServiceId())) {
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
}
