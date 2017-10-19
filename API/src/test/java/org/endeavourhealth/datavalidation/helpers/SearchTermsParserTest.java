package org.endeavourhealth.datavalidation.helpers;

import org.endeavourhealth.datavalidation.helpers.SearchTermsParser;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class SearchTermsParserTest {
    @Test
    public void constructorNull() throws Exception {
        SearchTermsParser parser = new SearchTermsParser(null);
        Assert.assertNotNull(parser);
    }

    @Test
    public void constructorEmpty() throws Exception {
        SearchTermsParser parser = new SearchTermsParser("");
        Assert.assertNotNull(parser);
    }

    @Test
    public void constructorOneWord() throws Exception {
        SearchTermsParser parser = new SearchTermsParser("One");
        Assert.assertNotNull(parser);
    }

    @Test
    public void constructorTwoWords() throws Exception {
        SearchTermsParser parser = new SearchTermsParser("One Two");
        Assert.assertNotNull(parser);
    }

    @Test
    public void constructorThreeWords() throws Exception {
        SearchTermsParser parser = new SearchTermsParser("One Two Three");
        Assert.assertNotNull(parser);
    }

    @Test
    public void constructorDoubleSpace() throws Exception {
        SearchTermsParser parser = new SearchTermsParser("One  Two");
        Assert.assertNotNull(parser);
    }

    @Test
    public void nhsNumberFull() throws Exception {
        SearchTermsParser parser = new SearchTermsParser("1234567890");
        Assert.assertTrue(parser.hasNhsNumber());
        Assert.assertEquals("1234567890", parser.getNhsNumber());
        Assert.assertFalse(parser.hasEmisNumber());
        Assert.assertFalse(parser.hasDateOfBirth());
    }

    @Test
    public void nhsNumberSplit() throws Exception {
        SearchTermsParser parser = new SearchTermsParser("123 456 7890");
        Assert.assertTrue(parser.hasNhsNumber());
        Assert.assertEquals("1234567890", parser.getNhsNumber());
        Assert.assertFalse(parser.hasEmisNumber());
        Assert.assertFalse(parser.hasDateOfBirth());
    }

    @Test
    public void emisNumberSmall() throws Exception {
        SearchTermsParser parser = new SearchTermsParser("12345678901234567890");
        Assert.assertTrue(parser.hasEmisNumber());
        Assert.assertEquals("12345678901234567890", parser.getEmisNumber());
        Assert.assertFalse(parser.hasNhsNumber());
        Assert.assertFalse(parser.hasDateOfBirth());
    }

    @Test
    public void emisNumberLarge() throws Exception {
        SearchTermsParser parser = new SearchTermsParser("12345");
        Assert.assertTrue(parser.hasEmisNumber());
        Assert.assertEquals("12345", parser.getEmisNumber());
        Assert.assertFalse(parser.hasNhsNumber());
        Assert.assertFalse(parser.hasDateOfBirth());
    }

    @Test
    public void dateOfBirth() throws Exception {
        SearchTermsParser parser = new SearchTermsParser("23-Apr-1985");
        Assert.assertTrue(parser.hasDateOfBirth());
        Assert.assertEquals(new SimpleDateFormat("dd-MMM-yyyy").parse("23-Apr-1985"), parser.getDateOfBirth());
        Assert.assertFalse(parser.hasNhsNumber());
        Assert.assertFalse(parser.hasEmisNumber());
    }


    @Test
    public void oneName() throws Exception {
        SearchTermsParser parser = new SearchTermsParser("Smith");
        Assert.assertEquals(1, parser.getNames().size());
        assertThat(parser.getNames(), hasItems("Smith"));
        Assert.assertFalse(parser.hasDateOfBirth());
        Assert.assertFalse(parser.hasNhsNumber());
        Assert.assertFalse(parser.hasEmisNumber());
    }

    @Test
    public void twoNames() throws Exception {
        SearchTermsParser parser = new SearchTermsParser("John Smith");
        Assert.assertEquals(2, parser.getNames().size());
        assertThat(parser.getNames(), hasItems("John", "Smith"));
        Assert.assertFalse(parser.hasDateOfBirth());
        Assert.assertFalse(parser.hasNhsNumber());
        Assert.assertFalse(parser.hasEmisNumber());
    }

    @Test
    public void threeNames() throws Exception {
        SearchTermsParser parser = new SearchTermsParser("John David Smith");
        Assert.assertEquals(3, parser.getNames().size());
        assertThat(parser.getNames(), hasItems("John", "David", "Smith"));
        Assert.assertFalse(parser.hasDateOfBirth());
        Assert.assertFalse(parser.hasNhsNumber());
        Assert.assertFalse(parser.hasEmisNumber());
    }

}