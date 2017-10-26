package org.endeavourhealth.datavalidation.helpers;

import cucumber.api.java8.En;
import org.endeavourhealth.datavalidation.BaseStepDef;

import static org.junit.Assert.assertEquals;

public class CuiFormatterStepDef extends BaseStepDef implements En {
    private String string;
    private String title;
    private String forename;
    private String surname;

    public CuiFormatterStepDef() {
        Given("^A string of (.*)$", (String arg0) -> {
            this.string = parseString(arg0);
        });

        Then("^The formatted sentence will be (.*)", (String arg0) -> {
            String actual = new CUIFormatter().toSentenceCase(this.string);
            assertEquals(parseString(arg0), actual);
        });


        Given("^A title of (.*)$", (String arg0) -> {
            this.title = parseString(arg0);
        });
        And("^A forename of (.*)$", (String arg0) -> {
            this.forename = parseString(arg0);
        });
        And("^A surname of (.*)$", (String arg0) -> {
            this.surname = parseString(arg0);
        });
        Then("^The formatted name will be (.*)$", (String arg0) -> {
            String actual = new CUIFormatter().getFormattedName(this.title, this.forename, this.surname);
            assertEquals(parseString(arg0), actual);
        });
    }
}
