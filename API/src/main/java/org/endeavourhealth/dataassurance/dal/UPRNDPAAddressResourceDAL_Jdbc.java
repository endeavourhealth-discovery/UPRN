package org.endeavourhealth.dataassurance.dal;

import com.fasterxml.jackson.databind.JsonNode;
import org.endeavourhealth.common.config.ConfigManager;
import org.endeavourhealth.coreui.framework.ContextShutdownHook;
import org.endeavourhealth.coreui.framework.StartupConfig;
import org.endeavourhealth.dataassurance.models.UPRNDPAAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class UPRNDPAAddressResourceDAL_Jdbc extends UPRNAddressResourceDAL_Jdbc implements UPRNDPAAddressResourceDAL{


    public UPRNDPAAddressResourceDAL_Jdbc() {
        super("UPRNDPAAddressResourceDAL_Jdbc");
    }


    @Override
    public List<UPRNDPAAddress> getCandidateAddresses(String postcode) {
        Connection conn = getConnection();

        List<UPRNDPAAddress> result = new ArrayList<>();

        try {

            String sql;

            // Bring back all DPA addresses matching the post code (upper-case)
            sql = "select * from "+MYSQL_UPRN_TABLE_NAME_DPA+" where "+
                    UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_POSTCODE +
                    " like '"+postcode.toUpperCase()+"%'";;

            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                result = getStatementResults(statement);
            }

        } catch (Exception e) {

            LOG.error("Error searching by patient]", e);

            return null;
        }

        return result;
    }

    private List<UPRNDPAAddress> getStatementResults(PreparedStatement statement) throws SQLException {
        ResultSet rs = statement.executeQuery();

        List<UPRNDPAAddress> result = new ArrayList<>();

        while (rs.next()) {
            UPRNDPAAddress uprnDPAAddress = new UPRNDPAAddress(
                    rs.getLong(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_UPRN),
                    rs.getLong(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_UDPRN),
                    rs.getString(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_ORGANISATION_NAME),
                    rs.getString(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_DEPARTMENT_NAME),
                    rs.getString(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_SUB_BUILDING_NAME),
                    rs.getString(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_BUILDING_NAME),
                    rs.getString(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_BUILDING_NUMBER),
                    rs.getString(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_DEPENDENT_THOROUGHFARE),
                    rs.getString(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_THOROUGHFARE),
                    rs.getString(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_DOUBLE_DEPENDENT_LOCALITY),
                    rs.getString(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_DEPENDENT_LOCALITY),
                    rs.getString(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_POST_TOWN),
                    rs.getString(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_POSTCODE),
                    rs.getString(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_POSTCODE_TYPE),
                    rs.getString(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_DELIVERY_POINT_SUFFIX),
                    rs.getString(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_WELSH_DEPENDENT_THOROUGHFARE),
                    rs.getString(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_WELSH_THOROUGHFARE),
                    rs.getString(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_WELSH_DOUBLE_DEPENDENT_LOCALITY),
                    rs.getString(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_WELSH_DEPENDENT_LOCALITY),
                    rs.getString(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_WELSH_POST_TOWN),
                    rs.getString(UPRNDPAAddress.UPRN_DPA_ADDR_SQL_COL_PO_BOX_NUMBER)
            );

            result.add(uprnDPAAddress);
        }

        // LOG.debug("Found " + result.size() + "rows");

        return result;
    }


}
