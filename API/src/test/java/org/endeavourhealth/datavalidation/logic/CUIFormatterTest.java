package org.endeavourhealth.datavalidation.logic;

import org.junit.Assert;
import org.junit.Test;

public class CUIFormatterTest {
    @Test
    public void toSentenceCase_Null() throws Exception {
        String actual = CUIFormatter.toSentenceCase(null);
        Assert.assertEquals(null, actual);
    }

    @Test
    public void toSentenceCase_Empty() throws Exception {
        String actual = CUIFormatter.toSentenceCase("");
        Assert.assertEquals("", actual);
    }

    @Test
    public void toSentenceCase_lower() throws Exception {
        String actual = CUIFormatter.toSentenceCase("hello world");
        Assert.assertEquals("Hello world", actual);
    }

    @Test
    public void toSentenceCase_Upper() throws Exception {
        String actual = CUIFormatter.toSentenceCase("HELLO WORLD");
        Assert.assertEquals("Hello world", actual);
    }

    @Test
    public void toSentenceCase_Correct() throws Exception {
        String actual = CUIFormatter.toSentenceCase("Hello world");
        Assert.assertEquals("Hello world", actual);
    }

    @Test
    public void getFormattedName_AllNull() throws Exception {
        String actual = CUIFormatter.getFormattedName(null, null, null);
        Assert.assertEquals("", actual);
    }

    @Test
    public void getFormattedName_AllEmpty() throws Exception {
        String actual = CUIFormatter.getFormattedName("", "", "");
        Assert.assertEquals("", actual);
    }

    @Test
    public void getFormattedName_SurnameOnly() throws Exception {
        String actual = CUIFormatter.getFormattedName("", "", "mouse");
        Assert.assertEquals("MOUSE", actual);
    }

    @Test
    public void getFormattedName_ForenameOnly() throws Exception {
        String actual = CUIFormatter.getFormattedName("", "mickey", "");
        Assert.assertEquals("Mickey", actual);
    }

    @Test
    public void getFormattedName_TitleOnly() throws Exception {
        String actual = CUIFormatter.getFormattedName("mr", "", "");
        Assert.assertEquals("(Mr)", actual);
    }

}