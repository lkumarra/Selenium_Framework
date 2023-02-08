package com.org.bank.listners;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Hashtable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.org.bank.utils.DbUtils;

public class Listners implements ITestListener, ISuiteListener {

	private final Logger logger = LoggerFactory.getLogger(Listners.class);
	private DbUtils dbUtils = new DbUtils();
	private final String TOTAL_TESTS = "Total Test Cases are : %s";
	private final String PASSED_TESTS = "Passed Test Cases are : %s";
	private final String SKIPPED_TESTS = "Skipped Test Cases are : %s";
	private final String FAILED_TESTS = "Failed Test Cases are : %s";
	private String rawQuery = "Insert into test_execution_status (module_name, test_name, test_status, execution_time, execution_date) values ('%s', '%s', '%s', %s, '%s')";
	private String suiteQuery = "Insert into test_suite_status(total_tests, passed_tests, failed_tests, skipped_tests, execution_date) values (%s, %s, %s, %s,'%s')";
	private Hashtable<String, Integer> hashtable = new Hashtable<String, Integer>();

	private String getCurrentDate() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
	}

	public void onStart(ISuite suite) {
		hashtable.put(TOTAL_TESTS, 0);
		hashtable.put(FAILED_TESTS, 0);
		hashtable.put(SKIPPED_TESTS, 0);
		hashtable.put(PASSED_TESTS, 0);
	}

	public void onTestStart(ITestResult result) {

	}

	public void onTestSuccess(ITestResult result) {
		if (hashtable.containsKey(PASSED_TESTS)) {
			hashtable.put(PASSED_TESTS, hashtable.get(PASSED_TESTS) + 1);
		} else {
			hashtable.put(PASSED_TESTS, 1);
		}
		String time = String.format("%s", Instant.now().toEpochMilli());
		String status = "Passed";
		String date = getCurrentDate();
		String insertQuery = String.format(rawQuery, result.getTestClass().getRealClass().getName(), result.getName(),
				status, time, date);
		dbUtils.insertQuery(insertQuery);
	}

	public void onTestFailure(ITestResult result) {

		if (hashtable.containsKey(FAILED_TESTS)) {
			hashtable.put(FAILED_TESTS, hashtable.get(FAILED_TESTS) + 1);
		} else {
			hashtable.put(FAILED_TESTS, 1);
		}
		String time = String.format("%s", Instant.now().toEpochMilli());
		String status = "Failed";
		String date = getCurrentDate();
		String insertQuery = String.format(rawQuery, result.getTestClass().getRealClass().getName(), result.getName(),
				status, time, date);
		dbUtils.insertQuery(insertQuery);
	}

	public void onTestSkipped(ITestResult result) {
		if (hashtable.containsKey(SKIPPED_TESTS)) {
			hashtable.put(SKIPPED_TESTS, hashtable.get(SKIPPED_TESTS) + 1);
		} else {
			hashtable.put(SKIPPED_TESTS, 1);
		}
		String time = String.format("%s", Instant.now().toEpochMilli());
		String status = "Skipped";
		String date = getCurrentDate();
		String insertQuery = String.format(rawQuery, result.getTestClass().getRealClass().getName(), result.getName(),
				status, time, date);
		dbUtils.insertQuery(insertQuery);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	public void onTestFailedWithTimeout(ITestResult result) {
		if (hashtable.containsKey(FAILED_TESTS)) {
			hashtable.put(FAILED_TESTS, hashtable.get(FAILED_TESTS) + 1);
		} else {
			hashtable.put(FAILED_TESTS, 1);
		}
		String time = String.format("%s", Instant.now().toEpochMilli());
		String status = "Failed";
		String date = getCurrentDate();
		String insertQuery = String.format(rawQuery, result.getTestClass().getRealClass().getName(), result.getName(),
				status, time, date);
		dbUtils.insertQuery(insertQuery);
	}

	public void onStart(ITestContext context) {

	}

	public void onFinish(ITestContext context) {

	}

	public void onFinish(ISuite suite) {
		hashtable.put(TOTAL_TESTS, suite.getAllMethods().size());
		String testCaseCount = "";
		for (String key : hashtable.keySet()) {
			testCaseCount = testCaseCount.concat(String.format(key, hashtable.get(key)));
		}
		System.out.println(testCaseCount);
		logger.info(testCaseCount);
		String date = getCurrentDate();
		Integer totalTests = hashtable.get(TOTAL_TESTS);
		Integer passedTest = hashtable.get(PASSED_TESTS);
		Integer failedTest = hashtable.get(FAILED_TESTS);
		Integer skippedTests = hashtable.get(SKIPPED_TESTS);
		String query = String.format(suiteQuery, totalTests, passedTest, failedTest, skippedTests, date);
		dbUtils.insertQuery(query);
	}
}
