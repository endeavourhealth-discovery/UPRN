package org.endeavourhealth.datavalidation.logic.mocks;

import org.endeavourhealth.datavalidation.dal.PersonPatientDAL;
import org.endeavourhealth.datavalidation.models.Patient;
import org.endeavourhealth.datavalidation.models.Person;

import java.text.SimpleDateFormat;
import java.util.*;

public class Mock_PersonPatientDAL implements PersonPatientDAL {
    public Set<String> organisationsPresent = new HashSet<>(Arrays.asList("1", "2"));
    public Set<String> organisationsMissing = new HashSet<>(Arrays.asList("1", "2"));

    public String nhsNumberPresent = "1234567890";
    public String nhsNumberMissing = "0987654321";

    public String emisNumberPresent = "12345";
    public String emisNumberMissing = "54321";

    public String dobPresent = "01-Jan-1981";
    private Date dobPresentDate;
    public String dobMissing = "02-Feb-1982";

    public String namePresent = "Smith";
    public String nameMultiPresent = "Smith Jones";
    public String nameMissing = "Brown";


    public boolean searchByNhsNumberCalled = false;
    public boolean searchByLocalIdCalled = false;
    public boolean searchByDateOfBirthCalled = false;
    public boolean searchByNamesCalled = false;
    public boolean getPatientsByNhsNumberCalled = false;

    public Mock_PersonPatientDAL () {
        try {
            dobPresentDate = new SimpleDateFormat("dd-MMM-yyyy").parse(dobPresent);
        } catch (Exception e) {
            System.out.println("Error::" + e);
            e.printStackTrace();
        }
    }

    @Override
    public List<Person> searchByNhsNumber(Set<String> serviceIds, String nhsNumber) {
        searchByNhsNumberCalled = true;
        List<Person> persons = new ArrayList<>();
        if (nhsNumberPresent.equals(nhsNumber))
            persons.add(new Person());

        return persons;
    }

    @Override
    public List<Person> searchByLocalId(Set<String> serviceIds, String emisNumber) {
        searchByLocalIdCalled = true;
        List<Person> persons = new ArrayList<>();
        if (emisNumberPresent.equals(emisNumber))
            persons.add(new Person());

        return persons;
    }

    @Override
    public List<Person> searchByDateOfBirth(Set<String> serviceIds, Date dateOfBirth) {
        searchByDateOfBirthCalled = true;
        List<Person> persons = new ArrayList<>();
        if (dobPresentDate.equals(dateOfBirth))
            persons.add(new Person());

        return persons;
    }

    @Override
    public List<Person> searchByNames(Set<String> serviceIds, List<String> names) {
        searchByNamesCalled = true;
        List<Person> persons = new ArrayList<>();

        for(String name : names) {
            if ("Smith".equals(name))
                persons.add(new Person());

            if ("Jones".equals(name))
                persons.add(new Person());
        }

        return persons;
    }

    @Override
    public List<Patient> getPatientsByNhsNumber(Set<String> serviceIds, String nhsNumber) {
        getPatientsByNhsNumberCalled = true;
        return new ArrayList<>();
    }
}
