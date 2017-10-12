package org.endeavourhealth.datavalidation.logic;

import org.endeavourhealth.core.rdbms.eds.PatientSearch;
import org.endeavourhealth.datavalidation.models.Person;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class PersonPatientTest {
    @Test
    public void findPersonsInOrganisations() throws Exception {
    }

    @Test
    public void buildPersonResultList() throws Exception {
        Set<String> organisationIds = new HashSet<>();
        Collections.addAll(organisationIds, "1", "3");

        List<PatientSearch> patientSearchList = new ArrayList<>();

        PatientSearch mickeyMouseAtOrg1 = new PatientSearch();
        mickeyMouseAtOrg1.setForenames("Mickey");
        mickeyMouseAtOrg1.setSurname("Mouse");
        mickeyMouseAtOrg1.setNhsNumber("12345");
        mickeyMouseAtOrg1.setServiceId("1");

        PatientSearch mickeyMouseAtOrg3 = new PatientSearch();
        mickeyMouseAtOrg3.setForenames("Mickey");
        mickeyMouseAtOrg3.setSurname("Mouse");
        mickeyMouseAtOrg3.setNhsNumber("12345");
        mickeyMouseAtOrg3.setServiceId("3");

        PatientSearch mickeyMouseAtOrg3NoNhs = new PatientSearch();
        mickeyMouseAtOrg3NoNhs.setForenames("Mickey");
        mickeyMouseAtOrg3NoNhs.setSurname("Mouse");
        mickeyMouseAtOrg3NoNhs.setNhsNumber(null);
        mickeyMouseAtOrg3NoNhs.setServiceId("3");

        PatientSearch donaldDuckAtOrg2 = new PatientSearch();
        donaldDuckAtOrg2.setForenames("Donald");
        donaldDuckAtOrg2.setSurname("Duck");
        donaldDuckAtOrg2.setNhsNumber("54321");
        donaldDuckAtOrg2.setServiceId("2");

        Collections.addAll(patientSearchList, mickeyMouseAtOrg1, mickeyMouseAtOrg3, mickeyMouseAtOrg3NoNhs, donaldDuckAtOrg2);

        List<Person> actual = PersonPatient.buildPersonResultList(organisationIds, patientSearchList);

        Assert.assertEquals(2, actual.size());

        Assert.assertEquals("MOUSE, Mickey", actual.get(0).getName());
        Assert.assertEquals("12345", actual.get(0).getNhsNumber());

        Assert.assertEquals("MOUSE, Mickey", actual.get(1).getName());
        Assert.assertNull(actual.get(1).getNhsNumber());
    }
}