package utils;

import org.junit.Assert;
import org.junit.Test;

public class LogMessageTest {
    @Test
    public void testLogMessageNoParts() throws Exception {
        LogMessage logMessage = new LogMessage("Admin", false);
        String expected = "[Admin]: ;";
        String result = logMessage.ToString();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testLogMessageOnePart() throws Exception {
        LogMessage logMessage = new LogMessage("Admin", false);
        logMessage.addMessagePart("Test", "I am a test method!");
        String expected = "[Admin]: Test: \"I am a test method!\";";
        String result = logMessage.ToString();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testLogMessageTwoParts() throws Exception {
        LogMessage logMessage = new LogMessage("Admin", false);
        logMessage.addMessagePart("Test", "I am a test method!");
        logMessage.addMessagePart("Test_2", "I am an another test method!");
        String expected = "[Admin]: Test: \"I am a test method!\";\n" +
                "         Test_2: \"I am an another test method!\";";
        String result = logMessage.ToString();

        Assert.assertEquals(expected, result);
    }
}
