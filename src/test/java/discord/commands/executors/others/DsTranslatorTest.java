package discord.commands.executors.others;

import org.junit.Assert;
import org.junit.Test;

public class DsTranslatorTest {
    private final DsTranslator translator = new DsTranslator();

    @Test
    public void testTranslatorNotNullTranslation1() {
        Assert.assertNotNull(translator.translate("cat"));
    }

    @Test
    public void testTranslatorNotNullTranslation2() {
        Assert.assertNotNull(translator.translate("dog"));
    }

    @Test
    public void testTranslatorTranslationFromEngToRus1() {
        Assert.assertEquals("кошка", translator.translate("cat"));
    }

    @Test
    public void testTranslatorTranslationFromEngToRus2() {
        Assert.assertEquals("собака", translator.translate("dog"));
    }

    @Test
    public void testTranslatorTranslationFromRusToEng1() {
        Assert.assertEquals("cat", translator.translate("`en` кот"));
    }

    @Test
    public void testTranslatorTranslationFromRusToEng2() {
        Assert.assertEquals("dog", translator.translate("`en` собака"));
    }
}
