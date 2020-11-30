package utils;

import org.junit.Assert;
import org.junit.Test;


public class FunctionsTest {
    @Test
    public void testMultiplyStringByZero(){
        String expected = "";
        String result = Functions.multiplyString("123", 0);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testMultiplyStringByOne(){
        String expected = "123";
        String result = Functions.multiplyString("123", 1);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testMultiplyStringByThree(){
        String expected = "123123123";
        String result = Functions.multiplyString("123", 3);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testMultiplyStringByMinusTwo(){
        String expected = "321321";
        String result = Functions.multiplyString("123", -2);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testTitleEmptyString() throws Exception {
        try {
            Functions.title("");
        } catch (StringIndexOutOfBoundsException exception) {
            Assert.assertTrue(true);
        } catch (Exception exception) {
            Assert.fail();
        }

    }

    @Test
    public void testTitleStringStartsWithSpace() throws Exception {
        String expected = " abc";
        String result = Functions.title(" abc");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testTitleStringCase1() throws Exception {
        String expected = "Abc";
        String result = Functions.title("abc");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testTitleStringCase2() throws Exception {
        String expected = "Abc";
        String result = Functions.title("Abc");
        Assert.assertEquals(expected, result);
    }
}
