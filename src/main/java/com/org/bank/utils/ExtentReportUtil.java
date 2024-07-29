package com.org.bank.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.org.bank.constants.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ExtentReportUtil {

    private final ExtentReports extentReports;

    private ExtentReportUtil() {
        FileReaderUtil fileReaderUtil = FileReaderUtil.newFileReaderUtil(Constants.CONFIG_FILE_PATH);
        extentReports = new ExtentReports();
        String reportName = getReportName(fileReaderUtil);
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(Constants.REPORT_PATH.concat("/").concat(reportName));
        configureReporter(extentSparkReporter, fileReaderUtil);
        extentReports.attachReporter(extentSparkReporter);
    }

    /**
     * Fetches the report name from the provided FileReaderUtil.
     * <p>
     * This method attempts to get the value of the "extentReport.name" property from the FileReaderUtil.
     * If an error occurs while fetching the property, an error message is logged and null is returned.
     *
     * @param fileReaderUtil The FileReaderUtil used to fetch the report name property.
     * @return The report name, or null if an error occurred.
     */
    private String getReportName(FileReaderUtil fileReaderUtil) {
        try {
            return fileReaderUtil.getPropertyValue("extentReport.name");
        } catch (Exception e) {
            log.error("Error while fetching the value for key : extentReport.name");
            return null;
        }
    }

    /**
     * Configures the provided ExtentSparkReporter with the necessary settings.
     * <p>
     * This method sets the theme, document title, encoding, report name, and offline mode of the reporter.
     * It also attempts to set the report name from a property in the provided FileReaderUtil.
     * If an error occurs while fetching the property, an error message is logged and the method continues with the default report name.
     *
     * @param reporter       The ExtentSparkReporter to be configured.
     * @param fileReaderUtil The FileReaderUtil used to fetch the report name property.
     */
    private void configureReporter(ExtentSparkReporter reporter, FileReaderUtil fileReaderUtil) {
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setDocumentTitle("Guru99 Bank Test Automation");
        reporter.config().setEncoding("UTF-8");
        reporter.config().setReportName("Guru99BankReport");
        reporter.config().setOfflineMode(true);
        try {
            reporter.config().setReportName(fileReaderUtil.getPropertyValue("reporter.name"));
        } catch (Exception e) {
            log.error("Error while fetching the value for key : reporter.name");
        }
    }

    public static ExtentReportUtil newExtentReportUtil() {
        return new ExtentReportUtil();
    }

    /**
     * Return the extent report after setting the configuration
     *
     * @return {@link ExtentReports}
     */
    public ExtentReports getExtentReports() {
        return extentReports;
    }

}
