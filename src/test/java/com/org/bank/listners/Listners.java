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

	private Logger logger = LoggerFactory.getLogger(Listners.class);
	private DbUtils dbUtils = new DbUtils();
	Hashtable<String, Integer> hashtable = new Hashtable<String, Integer>();

	public void onStart(ISuite suite) {
		hashtable.put("passedTest", 0);
		hashtable.put("failedTests", 0);
		hashtable.put("skippedTests", 0);
		hashtable.put("timeOutFailedTests", 0);
	}

	public void onTestStart(ITestResult result) {

	}

	public void onTestSuccess(ITestResult result) {
		if (hashtable.containsKey("passedTest")) {
			hashtable.put("passedTest", hashtable.get("passedTest") + 1);
		} else {
			hashtable.put("passedTest", 1);
		}
		String time = String.format("%s", Instant.now().toEpochMilli());
		String status = "Passed";
		String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		String insertQuery = String.format(
				"Insert into test_execution_status (module_name, test_name, test_status, execution_time, execution_date) values ('%s', '%s', '%s', %s, '%s')",
				result.getTestClass().getRealClass().getName(), result.getName(), status, time, date);
		dbUtils.insertQuery(insertQuery);
	}

	public void onTestFailure(ITestResult result) {

		if (hashtable.containsKey("failedTests")) {
			hashtable.put("failedTests", hashtable.get("passedTest") + 1);
		} else {
			hashtable.put("failedTests", 1);
		}
		String time = String.format("%s", Instant.now().toEpochMilli());
		String status = "Failed";
		String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		String insertQuery = String.format(
				"Insert into test_execution_status (module_name, test_name, test_status, execution_time, execution_date) values ('%s', '%s', '%s', %s, '%s')",
				result.getTestClass().getRealClass().getName(), status, time, date);
		dbUtils.insertQuery(insertQuery);
	}

	public void onTestSkipped(ITestResult result) {
		if (hashtable.containsKey("skippedTests")) {
			hashtable.put("skippedTests", hashtable.get("passedTest") + 1);
		} else {
			hashtable.put("skippedTests", 1);
		}
		String time = String.format("%s", Instant.now().toEpochMilli());
		String status = "Skipped";
		String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		String insertQuery = String.format(
				"Insert into test_execution_status (module_name, test_name, test_status, execution_time, execution_date) values ('%s', '%s', '%s', %s, '%s')",
				result.getTestClass().getRealClass().getName(), status, time, date);
		dbUtils.insertQuery(insertQuery);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	public void onTestFailedWithTimeout(ITestResult result) {
		if (hashtable.containsKey("failedTests")) {
			hashtable.put("failedTests", hashtable.get("passedTest") + 1);
		} else {
			hashtable.put("failedTests", 1);
		}
		String time = String.format("%s", Instant.now().toEpochMilli());
		String status = "Failed";
		String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		String insertQuery = String.format(
				"Insert into test_execution_status (module_name, test_name, test_status, execution_time, execution_date) values ('%s', '%s', '%s', %s, '%s')",
				result.getTestClass().getRealClass().getName(), result.getName(), status, time, date);
		dbUtils.insertQuery(insertQuery);
	}

	public void onStart(ITestContext context) {

	}

	public void onFinish(ITestContext context) {

	}

	public void onFinish(ISuite suite) {
		hashtable.put("totalTests", suite.getAllMethods().size());
		String testCaseCount = "";
		for (String key : hashtable.keySet()) {
			testCaseCount = testCaseCount.concat(String.format("%s : %s ", key, hashtable.get(key)));
		}
		System.out.println(testCaseCount);
		logger.info(testCaseCount);
		String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		Integer totalTests = hashtable.get("totalTests");
		Integer passedTest = hashtable.get("passedTest");
		Integer failedTest = hashtable.get("failedTests");
		Integer skippedTests = hashtable.get("skippedTests");
		String query = String.format(
				"Insert into test_suite_status(total_tests, passed_tests, failed_tests, skipped_tests, execution_date) values (%s, %s, %s, %s,'%s')",
				totalTests, passedTest, failedTest, skippedTests, date);
		dbUtils.insertQuery(query);

	}
}
