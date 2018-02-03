package org.endeavourhealth.dataassurance.helpers;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/",
    glue = "org.endeavourhealth.dataassurance",
    format = { "pretty", "html:target/cucumber"}
)
public class HelperTests {
}
