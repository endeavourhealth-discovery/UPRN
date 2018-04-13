package org.endeavourhealth.dataassurance.dal;

import com.fasterxml.jackson.databind.JsonNode;
import org.endeavourhealth.common.config.ConfigManager;
import org.endeavourhealth.coreui.framework.ContextShutdownHook;
import org.endeavourhealth.coreui.framework.StartupConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// Parent class for all UPRN database DAL operations
public abstract class UPRNAddressResourceDAL_Jdbc implements ContextShutdownHook {

    // Constant definitions


    // MySQL database definitions for all the UPRN DAL classes

    // Address Base Premium database info
    // ABP Database Name
    protected static String MYSQL_UPRN_DB_NAME = "uprn";

    // ABP Table Names
    protected static String MYSQL_UPRN_TABLE_NAME_DPA = "abp_dpa";

    // Config ID
    protected static String MYSQL_DB_CONFIG_CONFIG_ID = MYSQL_UPRN_DB_NAME;

    // Application ID
    protected static String MYSQL_DB_CONFIG_APP_ID = "db_common";

    // Logger
    protected static Logger LOG;

    protected  Connection _connection = null;


    protected UPRNAddressResourceDAL_Jdbc(String name) {

        // LOG = LoggerFactory.getLogger(UPRNAddressResourceDAL_Jdbc.class);
        LOG = LoggerFactory.getLogger(name);

        StartupConfig.registerShutdownHook(name, this);
    }

    protected Connection getConnection() {
        try {
            return (_connection != null  && !_connection.isClosed()) ? _connection : (_connection = createConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Connection createConnection() {

        String url="";

        try {
            // Connect to UPRN database
            JsonNode json = ConfigManager.getConfigurationAsJson(MYSQL_DB_CONFIG_CONFIG_ID, MYSQL_DB_CONFIG_APP_ID);

            url = json.get("url").asText();
            //System.out.println("Using UPRN URL: "+url);

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

            //    System.out.println("Got a UPRN connection");

                return dbConnection;
            } else {
                System.out.println("No UPRN connection - null reference");

            }

        } catch (Exception e) {
            System.out.println("No UPRN connection - exception thrown");

            LOG.error("Error getting connection", e);
        }

        return null;
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
}
