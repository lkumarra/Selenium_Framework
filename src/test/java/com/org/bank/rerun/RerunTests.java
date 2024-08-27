package com.org.bank.rerun;

import com.org.bank.constants.Constants;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RerunTests {
    public static void main(String[] args) throws Exception {
        List<String> failedTests = Files.readAllLines(Paths.get(Constants.RETRY_FILE_PATH));

        if (failedTests.isEmpty()) {
            System.out.println("No failed tests to rerun.");
            return;
        }

        XmlSuite suite = new XmlSuite();
        suite.setName("Rerun Failed Tests Suite");

        XmlTest test = new XmlTest(suite);
        test.setName("Failed Tests");

        List<XmlClass> classes = new ArrayList<>();
        for (String testCase : failedTests) {
            String[] parts = testCase.split("#");
            List<XmlInclude> methods = new ArrayList<>();
            if (parts.length == 2) {
                XmlClass testClass = new XmlClass(parts[0]);
                XmlInclude testMethod = new XmlInclude(parts[1]);
                methods.add(testMethod);
                testClass.setIncludedMethods(methods);
                classes.add(testClass);
            }
        }

        test.setXmlClasses(classes);

        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);

        TestNG testng = new TestNG();
        testng.setXmlSuites(suites);
        testng.run();
    }
}
