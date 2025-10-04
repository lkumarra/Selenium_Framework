package com.org.bank.listeners;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Objects;

import com.aventstack.extentreports.Status;
import com.org.bank.utils.FileReaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.org.bank.constants.Constants;
import com.org.bank.constants.WebDriverContext;
import com.org.bank.utils.DbUtils;
import com.org.bank.utils.ExtentReportUtil;
import com.org.bank.utils.SeleniumUtils;

@Slf4j
public class CustomListeners implements ITestListener, ISuiteListener {

    private DbUtils dbUtils;
    private final String TOTAL_TESTS = "Total Test Cases are : %s";
    private final String PASSED_TESTS = "Passed Test Cases are : %s";
    private final String SKIPPED_TESTS = "Skipped Test Cases are : %s";
    private final String FAILED_TESTS = "Failed Test Cases are : %s";
    private final Hashtable<String, Integer> hashtable = new Hashtable<>();
    private ExtentReports extentReports;
    private ExtentTest extentTest;
    JSONObject jsonObject = new JSONObject();

    public CustomListeners() {
        try {
            FileReaderUtil fileReaderUtil = FileReaderUtil.newFileReaderUtil(Constants.CONFIG_FILE_PATH);
            dbUtils = DbUtils.newDbUtils(fileReaderUtil.getPropertyValue("jdbcUrl"), fileReaderUtil.getPropertyValue("userName"), fileReaderUtil.getPropertyValue("password"));
            ExtentReportUtil extentReportUtil = ExtentReportUtil.newExtentReportUtil();
            extentReports = extentReportUtil.getExtentReports();
        } catch (Exception e) {
            log.error("Error occurred with error message : {}", e.getMessage());
        }
    }

    /**
     * This method returns the current date and time as a string.
     * The date and time are formatted as "dd/MM/yyyy HH:mm:ss".
     *
     * @return A string representing the current date and time.
     */
    private String getCurrentDate() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }

    /**
     * This method is called when a TestNG suite starts executing.
     * It initializes the count of total, failed, skipped, and passed tests to 0.
     * It also prints a message indicating the start of execution.
     *
     * @param suite The TestNG suite that is starting execution.
     */
    public void onStart(ISuite suite) {
        try (FileWriter writer = new FileWriter(Constants.RETRY_FILE_PATH)) {
            writer.write("");
        } catch (IOException e) {
            log.error("Stack trace is : {}", Arrays.toString(e.getStackTrace()));
        }
        // Initialize the count of total tests to 0
        hashtable.put(TOTAL_TESTS, 0);
        // Initialize the count of failed tests to 0
        hashtable.put(FAILED_TESTS, 0);
        // Initialize the count of skipped tests to 0
        hashtable.put(SKIPPED_TESTS, 0);
        // Initialize the count of passed tests to 0
        hashtable.put(PASSED_TESTS, 0);
        // Print a message indicating the start of execution
        printExecutionStartMessage();

    }

    /**
     * This method prints a message indicating the start of execution.
     * It prints a line of asterisks, followed by the current date and time, and another line of asterisks.
     */
    private void printExecutionStartMessage() {
        System.out.println("*********************************************************");
        System.out.println("Execution started at : " + new Date());
        System.out.println("*********************************************************");
    }

    /**
     * This method is called at the start of each test case in TestNG.
     * It creates a new test in the extent report and initializes a new JSON object to store test details.
     * It also logs the start of the test case in the extent report.
     *
     * @param result The result object for the test case that is starting. It contains methods to access test case details.
     */
    public void onTestStart(ITestResult result) {
        // Create a new test in the extent report with the name of the test case
        extentTest = extentReports.createTest(result.getName());

        // If the jsonObject is not null, print it
        if (Objects.nonNull(jsonObject)) {
            System.out.println(jsonObject.toString(4));
        }


        // Store the name of the test class in the JSON object
        jsonObject.put("testClass", result.getTestClass().getName());

        // Store the name of the test case in the JSON object
        jsonObject.put("testCaseName", result.getName());

        // Store the description of the test case in the JSON object
        jsonObject.put("testCaseDescription", result.getMethod().getDescription());

        // Log the start of the test case in the extent report
        extentTest.log(Status.INFO, result.getMethod().getDescription() + " is started");
    }

    /**
     * This method is called when a test case passes in TestNG.
     * It updates the count of passed tests, logs the test result, marks the test as passed in the extent report,
     * and stores the test result in a JSON object.
     *
     * @param result The result object for the test case that passed. It contains methods to access test case details.
     */
    public void onTestSuccess(ITestResult result) {
        updateTestResult(PASSED_TESTS);
        logTestResult(result, "Passed");
        extentTest.pass(result.getMethod().getDescription() + " is passed");
        jsonObject.put("testCaseResult", "Passed");
    }

    /**
     * This method is called when a test case fails in TestNG.
     * It updates the count of failed tests, logs the test result, marks the test as failed in the extent report,
     * captures a screenshot of the state at the time of failure, and stores the test result and stack trace in a JSON object.
     *
     * @param result The result object for the test case that failed. It contains methods to access test case details.
     */
    public void onTestFailure(ITestResult result) {
        updateTestResult(FAILED_TESTS);
        logTestResult(result, "Failed");
        extentTest.fail(String.format("%s : is failed with error message : %s and stack trace : %s", result.getMethod().getDescription(), result.getThrowable().getMessage(), Arrays.toString(result.getThrowable().getStackTrace())));
        captureScreenshot(result);
        jsonObject.put("testCaseResult", "Failed");
        jsonObject.put("failureStackTrace", Arrays.toString(result.getThrowable().getStackTrace()));
        try (FileWriter writer = new FileWriter(Constants.RETRY_FILE_PATH, true)) {
            writer.write(result.getTestClass().getName() + "#" + result.getMethod().getMethodName() + "\n");
        } catch (IOException e) {
            log.error("Stack trace is : {}", Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * This method is called when a test case is skipped in TestNG.
     * It updates the count of skipped tests, logs the test result, marks the test as skipped in the extent report,
     * captures a screenshot of the state at the time of skipping, and stores the test result in a JSON object.
     *
     * @param result The result object for the test case that was skipped. It contains methods to access test case details.
     */
    public void onTestSkipped(ITestResult result) {
        updateTestResult(SKIPPED_TESTS);
        logTestResult(result, "Skipped");
        extentTest.skip(String.format("%s : is skipped", result.getName()));
        captureScreenshot(result);
        jsonObject.put("testCaseResult", "Skipped");
    }

    /**
     * This method updates the count of test results in a hashtable.
     * The key represents the type of test result (total, passed, failed, skipped).
     * If the key is not present in the hashtable, it defaults to 0 and then increments the count by 1.
     *
     * @param key The key representing the type of test result.
     */
    private void updateTestResult(String key) {
        hashtable.put(TOTAL_TESTS, hashtable.getOrDefault(TOTAL_TESTS, 0) + 1);
        hashtable.put(key, hashtable.getOrDefault(key, 0) + 1);
    }

    /**
     * This method logs the result of a test case execution in a database.
     * It creates a SQL insert query with the module name, test name, test status, execution time, and execution date.
     * The query is then executed using a DbUtils instance.
     *
     * @param result The result object for the test case. It contains methods to access test case details.
     * @param status The status of the test case execution. It can be "Passed", "Failed", or "Skipped".
     */
    private void logTestResult(ITestResult result, String status) {
        String time = String.valueOf(System.currentTimeMillis());
        String date = getCurrentDate();
        String rawQuery = "Insert into test_execution_status (module_name, test_name, test_status, execution_time, execution_date) values ('%s', '%s', '%s', %s, '%s')";
        String query = String.format(rawQuery, result.getTestClass().getRealClass().getName(), result.getName(), status, time, date);
        dbUtils.insertQuery(query);
    }

    /**
     * This method captures a screenshot of the current state of the test case execution.
     * It constructs the screenshot name using the test case name and saves it in the specified directory.
     * It then uses a SeleniumUtils instance to take the screenshot and adds it to the extent report.
     *
     * @param result The result object for the test case. It contains methods to access test case details.
     */
    private void captureScreenshot(ITestResult result) {
        String screenshotName = Constants.SCREEN_SHOT_DIR.concat("/").concat(result.getName()).concat(".png");
        SeleniumUtils seleniumUtils = SeleniumUtils.newSeleniumUtils(WebDriverContext.getWebDriverContext(result.getTestClass().getRealClass().getName()));
        seleniumUtils.takesWebPageScreenShot(screenshotName);
        extentTest.addScreenCaptureFromPath(screenshotName);
    }

    /**
     * This method is called when a test case fails due to a timeout in TestNG.
     * It updates the count of failed tests, logs the test result, marks the test as failed in the extent report,
     * captures a screenshot of the state at the time of failure, and stores the test result, screenshot path, and stack trace in a JSON object.
     *
     * @param result The result object for the test case that failed due to a timeout. It contains methods to access test case details.
     */
    public void onTestFailedWithTimeout(ITestResult result) {
        updateTestResult(FAILED_TESTS);
        logTestResult(result, "Failed");
        extentTest.fail(String.format("%s is failed with error message %s", result.getName(), result.getThrowable().getMessage()));
        String screenshotName = Constants.SCREEN_SHOT_DIR.concat("/").concat(result.getName()).concat(".png");
        SeleniumUtils seleniumUtils = SeleniumUtils.newSeleniumUtils(WebDriverContext.getWebDriverContext(result.getTestClass().getRealClass().getName()));
        seleniumUtils.takesWebPageScreenShot(screenshotName);
        extentTest.addScreenCaptureFromPath(screenshotName);
        jsonObject.put("screenShotPath", screenshotName);
        jsonObject.put("testCaseResult", "Failed");
        jsonObject.put("failureStackTrace", Arrays.toString(result.getThrowable().getStackTrace()));
    }

    /**
     * This method is called when a TestNG test context finishes executing.
     * It prints the JSON object containing the test details to the console.
     *
     * @param context The test context that has finished execution. It contains methods to access test context details.
     */
    public void onFinish(ITestContext context) {
        System.out.println(jsonObject.toString(4));
        int total = context.getAllTestMethods().length;
        int passed = context.getPassedTests().size();
        int failed = context.getFailedTests().size();
        int skipped = context.getSkippedTests().size();

        System.out.println("Total: " + total);
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Skipped: " + skipped);
    }

    /**
     * This method is called when a TestNG suite finishes executing.
     * It updates the total test count in the hashtable with the total number of methods in the suite.
     * It then prints the count of test cases, logs the status of the test suite, flushes the extent reports, and prints a message indicating the completion of execution.
     *
     * @param suite The TestNG suite that has finished execution. It contains methods to access suite details.
     */
    public void onFinish(ISuite suite) {
        printTestCaseCount();
        logTestSuiteStatus();
        extentReports.flush();
        printExecutionCompletionMessage();
    }


    /**
     * This method prints the count of test cases for each status (total, passed, failed, skipped).
     * It constructs a string with the count of test cases for each status and prints it to the console and the log.
     */
    private void printTestCaseCount() {
        StringBuilder testCaseCount = new StringBuilder();
        for (String key : hashtable.keySet()) {
            testCaseCount.append(" ").append(String.format(key, hashtable.get(key))).append("\n");
        }
        System.out.println(testCaseCount);
        log.info(testCaseCount.toString());
    }

    /**
     * This method logs the status of a test suite execution in a database.
     * It creates a SQL insert query with the total tests, passed tests, failed tests, skipped tests, and execution date.
     * The query is then executed using a DbUtils instance.
     */
    private void logTestSuiteStatus() {
        String date = getCurrentDate();
        String suiteQuery = "Insert into test_suite_status(total_tests, passed_tests, failed_tests, skipped_tests, execution_date) values (%s, %s, %s, %s,'%s')";
        String query = String.format(suiteQuery, hashtable.get(TOTAL_TESTS), hashtable.get(PASSED_TESTS), hashtable.get(FAILED_TESTS), hashtable.get(SKIPPED_TESTS), date);
        dbUtils.insertQuery(query);
    }

    /**
     * This method prints a message indicating the completion of execution.
     * It prints two lines of asterisks, followed by the current date and time, and another two lines of asterisks.
     */
    private void printExecutionCompletionMessage() {
        System.out.println("*********************************************************");
        System.out.println("*********************************************************");
        System.out.println("Execution Completed at : " + new Date());
        System.out.println("*********************************************************");
        System.out.println("*********************************************************");
    }
}
