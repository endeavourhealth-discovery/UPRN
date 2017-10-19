package org.endeavourhealth.datavalidation.dal;

import com.fasterxml.jackson.databind.JsonNode;
import org.endeavourhealth.common.config.ConfigManager;
import org.endeavourhealth.coreui.framework.ContextShutdownHook;
import org.endeavourhealth.coreui.framework.StartupConfig;
import org.endeavourhealth.datavalidation.helpers.CUIFormatter;
import org.endeavourhealth.datavalidation.models.Patient;
import org.endeavourhealth.datavalidation.models.Person;
import org.endeavourhealth.datavalidation.models.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class PersonPatientDAL_Jdbc implements PersonPatientDAL, ContextShutdownHook {
    private static final Logger LOG = LoggerFactory.getLogger(PersonPatientDAL_Jdbc.class);
    private Connection _connection = null;

    public PersonPatientDAL_Jdbc() {
        StartupConfig.registerShutdownHook("PersonPatientDAL_Jdbc", this);
    }

    @Override
    public List<Person> searchByNhsNumber(Set<String> serviceIds, String nhsNumber) {
        Connection conn = getConnection();
        try {
            String sql = "select distinct nhs_number, forenames, surname, count(*) as cnt " +
                "from patient_search " +
                "where nhs_number = ? " +
                "and service_id in ("+String.join(",", Collections.nCopies(serviceIds.size(), "?")) +") "+
                "group by nhs_number, forenames, surname";

            return searchPeople(serviceIds, nhsNumber, conn, sql);

        } catch (Exception e) {
            LOG.error("Error searching by NHS number  [" + nhsNumber + "]", e);
            return null;
        }
    }

    @Override
    public List<Person> searchByLocalId(Set<String> serviceIds, String emisNumber) {
        Connection conn = getConnection();
        try {
            String sql = "select distinct nhs_number, forenames, surname, count(*) as cnt " +
                "from patient_search p " +
                "inner join patient_search_local_identifier l on p.service_id = l.service_id and p.system_id = l.system_id and p.patient_id = l.patient_id " +
                "where l.local_id = ? " +
                "and l.service_id IN ("+String.join(",", Collections.nCopies(serviceIds.size(), "?")) +") "+
                "group by nhs_number, forenames, surname";

            return searchPeople(serviceIds, emisNumber, conn, sql);

        } catch (Exception e) {
            LOG.error("Error searching by Local ID  [" + emisNumber + "]", e);
            return null;
        }
    }

    @Override
    public List<Person> searchByDateOfBirth(Set<String> serviceIds, Date dateOfBirth) {
        Connection conn = getConnection();
        try {
            String sql = "select distinct nhs_number, forenames, surname, count(*) as cnt " +
                "from patient_search " +
                "where date_of_birth = ? " +
                "and service_id in ("+String.join(",", Collections.nCopies(serviceIds.size(), "?")) +" "+
                "group by nhs_number, forenames, surname";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                int i = 1;
                statement.setDate(i++, new java.sql.Date(dateOfBirth.getTime()));

                for (String serviceId : serviceIds)
                    statement.setString(i++, serviceId);

                ResultSet rs = statement.executeQuery();

                List<Person> result = new ArrayList<>();

                while (rs.next()) {
                    result.add(new Person()
                        .setNhsNumber(rs.getString("nhs_number"))
                        .setName(new CUIFormatter().getFormattedName(
                            null,
                            rs.getString("forenames"),
                            rs.getString("surname")
                        ))
                        .setPatientCount(rs.getInt("cnt"))
                    );
                }

                return result;
            }

        } catch (Exception e) {
            LOG.error("Error searching by NHS number  [" + dateOfBirth + "]", e);
            return null;
        }
    }

    @Override
    public List<Person> searchByNames(Set<String> serviceIds, List<String> names) {
        Connection conn = getConnection();
        try {
            String sql;
            String name1;
            String name2 = "";

            if (names.size() == 1) {
                name1 = (names.get(0)).replace(",", "") + "%";
                sql = "select distinct nhs_number, forenames, surname, count(*) as cnt " +
                    "from patient_search " +
                    "where (lower(surname) LIKE ? or lower(forenames) LIKE ?) " +
                    "and service_id in (" + String.join(",", Collections.nCopies(serviceIds.size(), "?")) + ") "+
                    "group by nhs_number, forenames, surname";
            } else {
                name1 = (names.remove(names.size() - 1)).replace(",", "") + "%";
                name2 = String.join("% ", names).replace(",", "") + "%";
                sql = "select distinct nhs_number, forenames, surname, count(*) as cnt " +
                    "from patient_search " +
                    "where (" +
                        "(lower(surname) LIKE ? and lower(forenames) LIKE ?)" +
                        " or (lower(surname) LIKE ? and lower(forenames) LIKE ?)" +
                    ") " +
                    "and service_id in (" + String.join(",", Collections.nCopies(serviceIds.size(), "?")) + ") "+
                    "group by nhs_number, forenames, surname";
            }

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                int i = 1;

                if (names.size() == 1) {
                    statement.setString(i++, name1.toLowerCase());
                    statement.setString(i++, name1.toLowerCase());
                } else {
                    statement.setString(i++, name1.toLowerCase());
                    statement.setString(i++, name2.toLowerCase());
                    statement.setString(i++, name2.toLowerCase());
                    statement.setString(i++, name1.toLowerCase());
                }

                for (String serviceId : serviceIds)
                    statement.setString(i++, serviceId);

                ResultSet rs = statement.executeQuery();

                List<Person> result = new ArrayList<>();

                while (rs.next()) {
                    result.add(new Person()
                        .setNhsNumber(rs.getString("nhs_number"))
                        .setName(new CUIFormatter().getFormattedName(
                            null,
                            rs.getString("forenames"),
                            rs.getString("surname")
                        ))
                        .setPatientCount(rs.getInt("cnt"))
                    );
                }

                return result;
            }

        } catch (Exception e) {
            LOG.error("Error searching by names  [" + String.join("],[", names) + "]", e);
            return null;
        }
    }

    @Override
    public List<Patient> getPatientsByNhsNumber(Set<String> serviceIds, String nhsNumber) {
        Connection conn = getConnection();
        try {
            String sql = "select patient_id, forenames, surname, date_of_birth, service_id " +
                "from patient_search " +
                "where nhs_number = ? " +
                "and service_id in ("+String.join(",", Collections.nCopies(serviceIds.size(), "?")) +")";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                int i = 1;
                statement.setString(i++, nhsNumber);

                for (String serviceId : serviceIds)
                    statement.setString(i++, serviceId);

                ResultSet rs = statement.executeQuery();

                List<Patient> result = new ArrayList<>();

                while (rs.next()) {
                    result.add(new Patient()
                        .setId(rs.getString("service_id") + rs.getString("patient_id")) // Cassandra has no Service_Patient_Id
                        .setPatientId(UUID.fromString(rs.getString("patient_id")))
                        .setName(new CUIFormatter().getFormattedName(
                            null,
                            rs.getString("forenames"),
                            rs.getString("surname")
                        ))
                        .setDob(rs.getDate("date_of_birth").toString())
                        .setService(
                            new Service()
                            .setId(UUID.fromString(rs.getString("service_id")))
                        )
                    );
                }

                return result;
            }
        } catch (Exception e) {
            LOG.error("Error fetching patients for person [" + nhsNumber + "]", e);
            return null;
        }
    }

    private List<Person> searchPeople(Set<String> serviceIds, String identifier, Connection conn, String sql) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            int i = 1;
            statement.setString(i++, identifier);

            for (String serviceId : serviceIds)
                statement.setString(i++, serviceId);

            ResultSet rs = statement.executeQuery();

            List<Person> result = new ArrayList<>();

            while (rs.next()) {
                result.add(new Person()
                    .setNhsNumber(rs.getString("nhs_number"))
                    .setName(new CUIFormatter().getFormattedName(
                        null,
                        rs.getString("forenames"),
                        rs.getString("surname")
                    ))
                    .setPatientCount(rs.getInt("cnt"))
                );
            }

            return result;
        }
    }

    private Connection getConnection() {
        try {
            return (_connection != null  && !_connection.isClosed()) ? _connection : (_connection = createConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection createConnection() {
        try {
            JsonNode json = ConfigManager.getConfigurationAsJson("eds_db");
            String url = json.get("url").asText();
            String user = json.get("username").asText();
            String pass = json.get("password").asText();
            String driver = json.get("class") == null ? null : json.get("class").asText();

            if (driver != null && !driver.isEmpty())
                Class.forName(driver);

            Properties props = new Properties();

            props.setProperty("user", user);
            props.setProperty("password", pass);

            return DriverManager.getConnection(url, props);
        } catch (Exception e) {
            LOG.error("Error getting connection", e);
        }
        return null;
    }

    @Override
    public void contextShutdown() {
        try{
            if (_connection != null && !_connection.isClosed())
                _connection.close();
        } catch (Exception e) {
            LOG.error("Error disconnecting", e);
        }
    }
}
