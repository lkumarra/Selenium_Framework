package com.org.bank.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.org.bank.constants.Constants;

public class ExtentReportUtil {
	
	private final Logger logger = LoggerFactory.getLogger(ExtentReportUtil.class);
	private ExtentReports extentReports;
	private ExtentSparkReporter extentSparkReporter;
	private final String reportNameKey = "extentReport.name";
	public ExtentReportUtil() {
		FileReaderUtil fileReaderUtil =  new FileReaderUtil();	
		extentReports = new ExtentReports();
		String reportName = null;
		try {
			reportName = fileReaderUtil.getPropertyValue(reportNameKey);
		}catch(Exception e) {
			logger.error("Error while fetching the value for key : {}", reportNameKey);
		}
		extentSparkReporter = new ExtentSparkReporter(Constants.ReportPath.concat("/").concat(reportName));
		extentSparkReporter.config().setTheme(Theme.DARK);
		extentSparkReporter.config().setDocumentTitle("Guru99 Bank Test Automation");
		extentSparkReporter.config().setEncoding("UTF-8");
		extentSparkReporter.config().setReportName("Guru99BankReport");
		extentReports.attachReporter(extentSparkReporter);
	}
	
	/**
	 * Return the extent report after setting the configuration
	 * @return {@link ExtentReports}
	 */
	public ExtentReports getExtentReports() {
		return extentReports;
	}

}
