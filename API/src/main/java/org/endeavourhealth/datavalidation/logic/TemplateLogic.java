package org.endeavourhealth.datavalidation.logic;

import org.endeavourhealth.common.config.ConfigManager;
import org.endeavourhealth.datavalidation.dal.AdminDAL;
import org.endeavourhealth.datavalidation.dal.AdminDAL_Cassandra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateLogic {
    private static final Logger LOG = LoggerFactory.getLogger(TemplateLogic.class);

    public String getTemplate(String resourceId) {
        if (resourceId == null || resourceId.isEmpty())
            return null;

        return ConfigManager.getConfiguration("Template-"+resourceId);
    }

}
