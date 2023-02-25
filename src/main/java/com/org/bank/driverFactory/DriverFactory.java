package com.org.bank.driverFactory;

import java.util.Objects;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.org.bank.utils.FileReaderUtil;

/**
 * This class contains the method related to webdriver initializaton
 * 
 * @author Lavendra Kumar Rajput
 *
 * @Date 09/02/2023
 */
public class DriverFactory {

	private final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
	private FileReaderUtil fileReaderUtil = FileReaderUtil.newFileReaderUtil();
	private String currentEnvironment;
	private String currentBrowser;
	private ThreadLocal<WebDriver> threadLocal = new ThreadLocal<WebDriver>();
	public static WebDriver screenShotDriver;

	private DriverFactory() {
		String setEnvironment = System.getProperty("env");
		String setBrowser = System.getProperty("browser");
		if (Objects.isNull(setEnvironment) && Objects.isNull(setBrowser)) {
			try {
				setEnvironment = fileReaderUtil.getPropertyValue("env");
				setBrowser = fileReaderUtil.getPropertyValue("browser");
				logger.info("Setting the environment to : {} and browser to : {}", setEnvironment, setBrowser);
			} catch (Exception e) {
				logger.error("Error occured while setting the env variables with error message : {} ", e.getMessage());
			}
		}
		currentEnvironment = setEnvironment;
		currentBrowser = setBrowser;
		logger.info("Current environment is : {} and browser is {} : ", currentEnvironment, currentBrowser);
		WebDriver driver = setupWebDriver();
		if (Objects.isNull(screenShotDriver)) {
			screenShotDriver = driver;
		}
		threadLocal.set(driver);
	}
	/**
	 * Return the instance of DriverFactory Class
	 * @return
	 */
	public static DriverFactory newDriverFactory() {
		return new DriverFactory();
	}

	/**
	 * Setup the web driver;
	 * 
	 * @return Webdriver instance
	 */
	private WebDriver setupWebDriver() {
		switch (currentBrowser.toLowerCase()) {
		case "chrome":
			logger.info("Launching the browser : {}", currentBrowser.toLowerCase());
			return new ChromerDriverManager().getWebDriver();
		case "firefox":
			logger.info("Launching the browser : {}", currentBrowser.toLowerCase());
			return new FirefoxDriverManager().getWebDriver();
		case "edge":
			logger.info("Launching the browser : {}", currentBrowser.toLowerCase());
			return new EdgerDriverManager().getWebDriver();
		case "safari":
			logger.info("Launching the browser : {}", currentBrowser.toLowerCase());
			return new SafariDriverManager().getWebDriver();
		default:
			logger.info("Launching the browser : {}", currentBrowser.toLowerCase());
			return new ChromerDriverManager().getWebDriver();
		}
	}

	/**
	 * Get the webdrivers
	 * 
	 * @return
	 */
	public WebDriver getWebDriver() {
		return threadLocal.get();
	}

}
