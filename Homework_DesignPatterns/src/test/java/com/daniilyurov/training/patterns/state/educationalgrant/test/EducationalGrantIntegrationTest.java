package com.daniilyurov.training.patterns.state.educationalgrant.test;

import com.daniilyurov.training.patterns.state.educationalgrant.GrantApplication;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import static org.junit.Assert.*;

public class EducationalGrantIntegrationTest {

    @Rule
    public SystemOutRule outRule = new SystemOutRule().enableLog().muteForSuccessfulTests();

    @Test
    public void test() {
        String expectedOutput;
        String actualOutput;
        GrantApplication application;

        application = new GrantApplication(1);
        application.cancel();
        application.consider();

        expectedOutput = "Application №1 has been created.\n" +
                "Grant application №1 cancelled.\n" +
                "Sorry. Not possible to consider application №1. The applicant has cancelled its application!\n";
        actualOutput = outRule.getLogWithNormalizedLineSeparator();
        outRule.clearLog();
        assertEquals(expectedOutput, actualOutput);

        application = new GrantApplication(2);
        application.consider();
        application.confirm();
        application.cancel();

        expectedOutput = "Application №2 has been created.\n" +
                "The application №2 is now under consideration!\n" +
                "Application №2 has been confirmed. Congratulations!\n" +
                "Sorry. Not possible to cancel application №2. You have already received the grant!\n";
        actualOutput = outRule.getLogWithNormalizedLineSeparator();
        outRule.clearLog();
        assertEquals(expectedOutput, actualOutput);
    }
}
