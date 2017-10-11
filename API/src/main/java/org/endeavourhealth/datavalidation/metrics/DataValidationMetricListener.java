package org.endeavourhealth.datavalidation.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;


public class DataValidationMetricListener extends MetricsServlet.ContextListener {
    public static final MetricRegistry informationManagerMetricRegistry = DataValidationInstrumentedFilterContextListener.REGISTRY;

    @Override
    protected MetricRegistry getMetricRegistry() {
        return informationManagerMetricRegistry;
    }
}
