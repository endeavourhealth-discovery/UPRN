package org.endeavourhealth.dataassurance.dal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.endeavourhealth.common.config.ConfigManager;
import org.endeavourhealth.coreui.framework.ContextShutdownHook;
import org.endeavourhealth.coreui.framework.StartupConfig;
import org.endeavourhealth.dataassurance.models.UPRNPatientResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.io.IOException;

import java.util.Properties;

public class UPRNPatientResourceDAL_Jdbc implements UPRNPatientResourceDAL, ContextShutdownHook {

    private static final Logger LOG = LoggerFactory.getLogger(UPRNPatientResourceDAL_Jdbc.class);
    private Connection _connection = null;


    public UPRNPatientResourceDAL_Jdbc() {
        StartupConfig.registerShutdownHook("UPRNPatientResourceDAL_Jdbc", this);
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

        String url="";

        try {
            // Connect to EDS database
            JsonNode json = ConfigManager.getConfigurationAsJson("eds", "db_common");

            url = json.get("url").asText();
            //System.out.println("Using this URL: "+url);

            String user = json.get("username").asText();
            String pass = json.get("password").asText();

            String driver = json.get("class") == null ? null : json.get("class").asText();

            if (driver != null && !driver.isEmpty())
                Class.forName(driver);

            Properties props = new Properties();

            props.setProperty("user", user);
            props.setProperty("password", pass);
            props.setProperty("autoReconnect", "true");


            Connection dbConnection = DriverManager.getConnection(url, props);;

            if (dbConnection != null) {

                //System.out.println("UPRNPatientResourceDAL_Jdbc - Got a connection");

                return dbConnection;
            } else {
                //System.out.println("UPRNPatientResourceDAL_Jdbc - No connection!");
                LOG.error("Error getting connection");
            }

        } catch (Exception e) {
            //System.out.println("UPRNPatientResourceDAL_Jdbc - No connection!!");
            LOG.error("Error getting connection", e);
        }

        return null;
    }




    @Override
    public List<UPRNPatientResource> searchByPatientPostcode(String filter) {

        List<UPRNPatientResource> result = null;

        Connection conn = getConnection();
        try {


            String sql;

            if (filter == null || filter.isEmpty()) {
                // Bring back all patients
                sql = "select * from patient_address_sample_distinct_100k";

                //System.out.println("Bring back all patients");

            } else {
                // Bring back only patients whose forenames begin with the prefix in filter

                //System.out.println("Bring back some patients");

                sql = "select * from patient_address_sample_distinct_100k where postcode like '"+filter+"%'";
                // System.out.println("UPRNPatientResourceDAL_Jdbc - running sql: "+sql);

            }

            try (PreparedStatement statement = conn.prepareStatement(sql)) {


                result = getStatementResults(statement);

                //System.out.println("Got back this many records: "+String.valueOf(result.size()));
            }

        } catch (Exception e) {

            LOG.error("Error searching by patients by match criteria", e);

            return null;
        }

        return result;
    }


    private List<UPRNPatientResource> getStatementResults(PreparedStatement statement) throws SQLException {
        ResultSet rs = statement.executeQuery();


        List<UPRNPatientResource> result = new ArrayList<>();

        while (rs.next()) {

            UPRNPatientResource uprnPatientResource = new UPRNPatientResource(
                    "",
                    //rs.getString(UPRNPatientResource.UPRN_PATIENT_SQL_COL_FORENAMES),
                    "",
                    //rs.getString(UPRNPatientResource.UPRN_PATIENT_SQL_COL_SURNAME),
                    rs.getString(UPRNPatientResource.UPRN_PATIENT_SQL_COL_ADDR1),
                    rs.getString(UPRNPatientResource.UPRN_PATIENT_SQL_COL_ADDR2),
                    rs.getString(UPRNPatientResource.UPRN_PATIENT_SQL_COL_ADDR3),
                    rs.getString(UPRNPatientResource.UPRN_PATIENT_SQL_COL_CITY),
                    rs.getString(UPRNPatientResource.UPRN_PATIENT_SQL_COL_DISTRICT),
                    rs.getString(UPRNPatientResource.UPRN_PATIENT_SQL_COL_POST_CODE)

            );

            result.add(uprnPatientResource);
        }

        LOG.debug("Found " + result.size() + "rows");

        return result;
    }


    private List<UPRNPatientResource> applyFilterCriteria(String filter, List<UPRNPatientResource> rawResult) {
        List<UPRNPatientResource> filteredResult = new ArrayList<>();

        List<UPRNPatientResource> listToProcess = rawResult;

        Iterator iter = listToProcess.iterator();

        int i=0;
        // while(iter.hasNext() && i<100) { // Limit returned patients to 100 max
        while(iter.hasNext()) {
                UPRNPatientResource rawRes = (UPRNPatientResource) iter.next();

              // Add this entry to the search results
            filteredResult.add(rawRes);

            i++;
        }


        return filteredResult;
    }

    @Override
    public void contextShutdown(){
        try{
            if (_connection != null && !_connection.isClosed())
                _connection.close();
        } catch (Exception e) {
            LOG.error("Error disconnecting", e);
        }
    }



    @Override
    public boolean updatePatientUPRN(UPRNPatientResource patientResource, String uprn) {
        boolean isSuccess = false;

        Connection conn = getConnection();
        try {

            String sql;

            // Update th epatient with UPRN valus who fulfils the address match criteria

            //System.out.println("Bring back some patients");

            sql = "update patient_address_sample_distinct_100k set UPRN = '"+uprn+"' where ADDRESS_LINE_1 LIKE '"+patientResource.getAddress_line1()+"'";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                int numUpdated = 0;

                numUpdated = statement.executeUpdate();

                if (numUpdated > 0) {

                    //System.out.println("Update success - number of rows updated: "+String.valueOf(numUpdated));

                    isSuccess = true;
                } else {

                    //System.out.println("Update not successful");

                    LOG.error("Error updating patient uprn]");
                }

            }

        } catch (Exception e) {

            LOG.error("Error updating patient uprn]", e);

        }

        return isSuccess;
    }

    @Override
    public List<UPRNPatientResource> searchByPatientAddr1(String filter) {

        List<UPRNPatientResource> result = null;

        Connection conn = getConnection();
        try {


            String sql;

            if (filter == null || filter.isEmpty()) {
                // Bring back all patients
                sql = "select * from patient_address_sample_distinct_100k";

                //System.out.println("Bring back all patients");

            } else {
                // Bring back only patients whose forenames begin with the prefix in filter

                //System.out.println("Bring back some patients");

                sql = "select * from patient_address_sample_distinct_100k where address_line_1 like '"+filter+"%'";

            }

            try (PreparedStatement statement = conn.prepareStatement(sql)) {


                result = getStatementResults(statement);

                //System.out.println("Got back records: "+String.valueOf(result.size()));
            }

        } catch (Exception e) {

            LOG.error("Error searching by patients by match criteria", e);

            return null;
        }

        return result;
    }

}
