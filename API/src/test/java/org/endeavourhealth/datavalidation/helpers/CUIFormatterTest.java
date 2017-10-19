package org.endeavourhealth.datavalidation.helpers;

import org.endeavourhealth.datavalidation.helpers.CUIFormatter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CUIFormatterTest {
    private CUIFormatter cuiFormatter;

    @Before
    public void setup() {
        cuiFormatter =  new CUIFormatter();
    }

    @Test
    public void toSentenceCase_Null() throws Exception {
        String actual = cuiFormatter.toSentenceCase(null);
        Assert.assertEquals(null, actual);
    }

    @Test
    public void toSentenceCase_Empty() throws Exception {
        String actual = cuiFormatter.toSentenceCase("");
        Assert.assertEquals("", actual);
    }

    @Test
    public void toSentenceCase_lower() throws Exception {
        String actual = cuiFormatter.toSentenceCase("hello world");
        Assert.assertEquals("Hello world", actual);
    }

    @Test
    public void toSentenceCase_Upper() throws Exception {
        String actual = cuiFormatter.toSentenceCase("HELLO WORLD");
        Assert.assertEquals("Hello world", actual);
    }

    @Test
    public void toSentenceCase_Correct() throws Exception {
        String actual = cuiFormatter.toSentenceCase("Hello world");
        Assert.assertEquals("Hello world", actual);
    }

    @Test
    public void getFormattedName_AllNull() throws Exception {
        String actual = cuiFormatter.getFormattedName(null, null, null);
        Assert.assertEquals("", actual);
    }

    @Test
    public void getFormattedName_AllEmpty() throws Exception {
        String actual = cuiFormatter.getFormattedName("", "", "");
        Assert.assertEquals("", actual);
    }

    @Test
    public void getFormattedName_SurnameOnly() throws Exception {
        String actual = cuiFormatter.getFormattedName("", "", "mouse");
        Assert.assertEquals("MOUSE", actual);
    }

    @Test
    public void getFormattedName_ForenameOnly() throws Exception {
        String actual = cuiFormatter.getFormattedName("", "mickey", "");
        Assert.assertEquals("Mickey", actual);
    }

    @Test
    public void getFormattedName_TitleOnly() throws Exception {
        String actual = cuiFormatter.getFormattedName("mr", "", "");
        Assert.assertEquals("(Mr)", actual);
    }

}