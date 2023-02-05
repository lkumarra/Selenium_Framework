package com.org.bank.driverFactory;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.org.bank.utils.FileReaderUtil;

public class DriverFactory {

	Logger logger = LoggerFactory.getLogger(DriverFactory.class);
	protected Driver driver;
	private static String browserName;
	private static String environment;
	private FileReaderUtil fileReaderUtil = new FileReaderUtil();

	public DriverFactory() {
		System.setProperty("env", "cloud");
		System.setProperty("browser", "firefox");
		driver = setWebDriver();
	}

	private void readEnvVariables() {
		String currentEnviroment = System.getProperty("env");
		String currentBrowser = System.getProperty("browser");
		if (Objects.isNull(currentEnviroment) && Objects.isNull(currentBrowser)) {
			try {
				currentEnviroment = fileReaderUtil.getPropertyValue("env");
				currentBrowser = fileReaderUtil.getPropertyValue("browser");
				logger.info("Setting the environment to : {} and browser to : {}",currentEnviroment, currentBrowser);
			} catch (Exception e) {
				logger.error("Error occured while setting the env variables with error message : {} ", e.getMessage());
			}
		}
		browserName = currentBrowser;
		environment = currentEnviroment;
		logger.info("Current environment is : {} and browser is {} : ", environment, browserName);
	}

	public Driver setWebDriver() {
		readEnvVariables();
		switch (browserName.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriverImpl();
			break;
		case "firefox":
			driver = new FireFoxDriverImpl();
			break;
		case "edge":
			driver = new EdgerDriverImpl();
			break;
		case "safari":
			driver = new SafariDriverImpl();
			break;
		default:
			driver = new ChromeDriverImpl();
		}
		return driver;
	}

}
