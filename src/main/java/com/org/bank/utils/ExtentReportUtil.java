package com.org.bank.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.org.bank.constants.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExtentReportUtil {

	private final ExtentReports extentReports;

	private ExtentReportUtil() {
		FileReaderUtil fileReaderUtil =  FileReaderUtil.newFileReaderUtil();	
		extentReports = new ExtentReports();
		String reportName = null;
		String reportNameKey = "extentReport.name";
		try {
			reportName = fileReaderUtil.getPropertyValue(reportNameKey);
		}catch(Exception e) {
			log.error("Error while fetching the value for key : {}", reportNameKey);
		}
		ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(Constants.ReportPath.concat("/").concat(reportName));
		extentSparkReporter.config().setTheme(Theme.DARK);
		extentSparkReporter.config().setDocumentTitle("Guru99 Bank Test Automation");
		extentSparkReporter.config().setEncoding("UTF-8");
		extentSparkReporter.config().setReportName("Guru99BankReport");
		extentReports.attachReporter(extentSparkReporter);
	}
	
	public static ExtentReportUtil newExtentReportUtil() {
		return new ExtentReportUtil();
	}
	
	/**
	 * Return the extent report after setting the configuration
	 * @return {@link ExtentReports}
	 */
	public ExtentReports getExtentReports() {
		return extentReports;
	}

}
