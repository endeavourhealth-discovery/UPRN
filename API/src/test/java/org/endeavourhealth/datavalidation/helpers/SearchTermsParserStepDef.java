package org.endeavourhealth.datavalidation.helpers;

import cucumber.api.java8.En;
import org.endeavourhealth.datavalidation.BaseStepDef;

import java.text.SimpleDateFormat;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class SearchTermsParserStepDef extends BaseStepDef implements En {
    private String input;
    private SearchTermsParser parser = null;

    public SearchTermsParserStepDef() {
        Given("^A search terms of (.*)$", (String input) -> {
            this.input = input;
        });
        When("^the input is parsed$", () -> {
            parser = new SearchTermsParser(parseString(input));
        });
        Then("^The parser will be created$", () -> {
            assertNotNull(parser);
        });
        Then("^The parser will only have an NHS number of (.*)$", (String arg0) -> {
            assertTrue(parser.hasNhsNumber());
            assertFalse(parser.hasEmisNumber());
            assertFalse(parser.hasDateOfBirth());
            assertEquals(parseString(arg0), parser.getNhsNumber());
        });
        Then("^The parser will only have a local identifier of (.*)$", (String arg0) -> {
            assertFalse(parser.hasNhsNumber());
            assertTrue(parser.hasEmisNumber());
            assertFalse(parser.hasDateOfBirth());
            assertEquals(parseString(arg0), parser.getEmisNumber());
        });
        Then("^The parser will only have a date of birth of (.*)$", (String arg0) -> {
            assertFalse(parser.hasNhsNumber());
            assertFalse(parser.hasEmisNumber());
            assertTrue(parser.hasDateOfBirth());
            assertEquals(parseString(arg0), new SimpleDateFormat("dd/MM/yyyy").format(parser.getDateOfBirth()));
        });
        Then("^The parser will only have a names of (.*)$", (String arg0) -> {
            String[] names = parseString(arg0).split(",");
            assertFalse(parser.hasNhsNumber());
            assertFalse(parser.hasEmisNumber());
            assertFalse(parser.hasDateOfBirth());
            assertEquals(names.length, parser.getNames().size());
            assertThat(parser.getNames(), hasItems(names));
        });
    }
}
