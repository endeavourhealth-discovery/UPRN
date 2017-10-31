package org.endeavourhealth.datavalidation.helpers;

import cucumber.api.java8.En;
import org.endeavourhealth.datavalidation.BaseStepDef;

import static org.junit.Assert.assertEquals;

public class CuiFormatterStepDef extends BaseStepDef implements En {
    private String string;
    private String title;
    private String forename;
    private String surname;
    private String actual;

    public CuiFormatterStepDef() {
        Given("^A string of (.*)$", (String input) -> {
            this.string = parseString(input);
        });
        When("^the sentence is formatted$", () -> {
            actual = new CUIFormatter().toSentenceCase(this.string);
        });
        Then("^The formatted sentence will be (.*)", (String expected) -> {
            assertEquals(parseString(expected), actual);
        });


        Given("^A title of (.*)$", (String input) -> {
            this.title = parseString(input);
        });
        And("^A forename of (.*)$", (String input) -> {
            this.forename = parseString(input);
        });
        And("^A surname of (.*)$", (String input) -> {
            this.surname = parseString(input);
        });
        When("^the name is formatted$", () -> {
            actual = new CUIFormatter().getFormattedName(this.title, this.forename, this.surname);
        });
        Then("^The formatted name will be (.*)$", (String expected) -> {
            assertEquals(parseString(expected), actual);
        });
    }
}
