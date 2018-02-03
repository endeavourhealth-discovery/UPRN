package org.endeavourhealth.dataassurance.dal;

import org.endeavourhealth.dataassurance.models.Patient;
import org.endeavourhealth.dataassurance.models.Person;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface PersonPatientDAL {
    List<Person> searchByNhsNumber(Set<String> serviceIds, String nhsNumber);
    List<Person> searchByLocalId(Set<String> serviceIds, String emisNumber);
    List<Person> searchByDateOfBirth(Set<String> serviceIds, Date dateOfBirth);
    List<Person> searchByNames(Set<String> serviceIds, List<String> names);

    List<Patient> getPatientsByNhsNumber(Set<String> serviceIds, String nhsNumber);
    Patient getPatient(String serviceId, String systemId, String patientId);
}
