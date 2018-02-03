package org.endeavourhealth.dataassurance.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;


public class DataAssuranceMetricListener extends MetricsServlet.ContextListener {
    public static final MetricRegistry dataAssuranceMetricRegistry = DataAssuranceInstrumentedFilterContextListener.REGISTRY;

    @Override
    protected MetricRegistry getMetricRegistry() {
        return dataAssuranceMetricRegistry;
    }
}
