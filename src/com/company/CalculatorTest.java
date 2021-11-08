package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    Calculator exp;

    @Test
    void calculator_test_1() throws Exception {
        exp = new Calculator("22+(99-83)*2/4");
        exp.analyzer();
        assertEquals("30.0", exp.toString());
    }

    @Test
    void calculator_test_2() throws Exception {
        exp = new Calculator("29+7*3/(46-43)");
        exp.analyzer();
        assertEquals("36.0", exp.toString());
    }

    @Test
    void calculator_test_3() throws Exception {
        exp = new Calculator("90-60/(2+3)*7");
        exp.analyzer();
        assertEquals("6.0", exp.toString());
    }

    @Test
    void calculator_test_4() throws Exception {
        exp = new Calculator("20/4+12*(58-54)");
        exp.analyzer();
        assertEquals("53.0", exp.toString());
    }

    @Test
    void calculator_test_5() throws Exception {
        exp = new Calculator("42/(39-36)+6*7");
        exp.analyzer();
        assertEquals("56.0", exp.toString());
    }

    @Test
    void calculator_test_6() throws Exception {
        exp = new Calculator("1+2*(1+2*(1+2*(1-3)))");
        exp.analyzer();
        assertEquals("-9.0", exp.toString());
    }

    @Test
    void calculator_test_7() throws Exception {
        exp = new Calculator("22+(99-83)*2/4");
        exp.analyzer();
        assertEquals("30.0", exp.toString());
    }

    @Test
    void calculator_test_8() throws Exception {
        exp = new Calculator("5555+(182320/84-693)*66");
        exp.analyzer();
        assertEquals("103068.42857142857", exp.toString());
    }

    @Test
    void calculator_test_9() throws Exception {
        exp = new Calculator("32087-87*(67+62524/308)");
        exp.analyzer();
        assertEquals("8597.0", exp.toString());
    }

    @Test
    void calculator_test_10() throws Exception {
        exp = new Calculator("17364*(153+3452/51-(4351-2452)+35*(2345-(3451-35622)))");
        exp.analyzer();
        assertEquals("2.094761160047059E10", exp.toString());
    }

    @Test
    void calculator_test_11() throws Exception {
        try {
            exp = new Calculator("123-23++63*(435-23)");
            exp.analyzer();
        } catch (Exception ex) {
            assertEquals("Incorrect location of operations!", ex.getMessage());
        }
    }

    @Test
    void calculator_test_12() throws Exception {
        try {
            exp = new Calculator("56+238-2385(531+245)");
            exp.analyzer();
        } catch (Exception ex) {
            assertEquals("Incorrect location of operations!", ex.getMessage());
        }
    }

    @Test
    void calculator_test_13() throws Exception {
        try {
            exp = new Calculator("1246-4727*(341-245-(345+12*3)");
            exp.analyzer();
        } catch (Exception ex) {
            assertEquals("There are not enough brackets!", ex.getMessage());
        }
    }

    @Test
    void calculator_test_14() throws Exception {
        try {
            exp = new Calculator("452+123-19*)19");
            exp.analyzer();
        } catch (Exception ex) {
            assertEquals("Incorrect location of operations!", ex.getMessage());
        }
    }

    @Test
    void calculator_test_15() throws Exception {
        try {
            exp = new Calculator("452+123-19*15)19");
            exp.analyzer();
        } catch (Exception ex) {
            assertEquals("The opening parenthesis is missing!", ex.getMessage());
        }
    }

    @Test
    void calculator_test_16() throws Exception {
        try {
            exp = new Calculator("634-12352+4562*(451-23");
            exp.analyzer();
        } catch (Exception ex) {
            assertEquals("There are not enough brackets!", ex.getMessage());
        }
    }

    @Test
    void calculator_test_17() throws Exception {
        try {
            exp = new Calculator("2245-21+589*90+(492-478)(432+90)");
            exp.analyzer();
        } catch (Exception ex) {
            assertEquals("Incorrect location of operations!", ex.getMessage());
        }
    }

    @Test
    void calculator_test_18() throws Exception {
        try {
            exp = new Calculator("15+8902-(-100)/(100-100)");
            exp.analyzer();
        } catch (Exception ex) {
            assertEquals("Division by zero", ex.getMessage());
        }
    }

    @Test
    void calculator_test_19() throws Exception {
        exp = new Calculator("15+8902-(-100)/(100-23)");
        exp.analyzer();
        assertEquals("8918.298701298701", exp.toString());
    }

    @Test
    void calculator_test_20() throws Exception {
        try {
            exp = new Calculator("973615/6346-2345^3522");
            exp.analyzer();
        }
        catch (Exception ex) {
            assertEquals("Invalid character(s)", ex.getMessage());
        }
    }
}