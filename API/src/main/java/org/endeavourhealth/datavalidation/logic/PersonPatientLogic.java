package org.endeavourhealth.datavalidation.logic;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.datavalidation.dal.PersonPatientDAL;
import org.endeavourhealth.datavalidation.dal.PersonPatientDAL_Jdbc;
import org.endeavourhealth.datavalidation.helpers.SearchTermsParser;
import org.endeavourhealth.datavalidation.models.Patient;
import org.endeavourhealth.datavalidation.models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PersonPatientLogic {
    private PersonPatientDAL dal;

    public PersonPatientLogic() {
         dal = new PersonPatientDAL_Jdbc();
    }

    PersonPatientLogic(PersonPatientDAL dal) {
        this.dal = dal;
    }


    public List<Person> findPersonsInOrganisations(Set<String> organisationIds, String searchTerms) throws Exception {
        List<Person> result = new ArrayList<>();

        if (!StringUtils.isEmpty(searchTerms)) {

            SearchTermsParser parser = new SearchTermsParser(searchTerms);

            if (parser.hasNhsNumber())
                result.addAll(dal.searchByNhsNumber(organisationIds, parser.getNhsNumber()));

            if (parser.hasEmisNumber())
                result.addAll(dal.searchByLocalId(organisationIds, parser.getEmisNumber()));

            if (parser.hasDateOfBirth())
                result.addAll(dal.searchByDateOfBirth(organisationIds, parser.getDateOfBirth()));

            result.addAll(dal.searchByNames(organisationIds, parser.getNames()));
        }

        return result;
    }

    public List<Patient> getPatientsForPerson(Set<String> serviceIds, String personId) throws Exception {
        return dal.getPatientsByNhsNumber(serviceIds, personId);
    }
}
