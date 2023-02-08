package com.org.bank.pages;

import java.util.Objects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.org.bank.utils.FileReaderUtil;
import com.org.bank.utils.SeleniumUtils;

/**
 * This is the Base Class of the application
 * 
 * @author Lavendra Kumar Rajput
 *
 * @Date 08/02/2023
 */
public class BasePage {

	Logger logger = LoggerFactory.getLogger(BasePage.class);
	private FileReaderUtil util;
	private SeleniumUtils seleniumUtils;
	private WebDriver driver;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		util = new FileReaderUtil();
		logger.info("Successfully created the instace of class : {} inside class : {}", util.getClass().getName(),
				BasePage.class.getName());
		seleniumUtils = new SeleniumUtils(driver);
		logger.info("Successfully created the instace of class : {} inside class : {}",
				seleniumUtils.getClass().getName(), BasePage.class.getName());
	}

	/**
	 * Perform initialization operation
	 */
	public void initialization() {
		String url = "";
		try {
			url = util.getPropertyValue("url");
			logger.info("Url loaded from file is : {}", url);
		} catch (Exception e) {
			logger.error("Error occcured file getting the url with error message : {} ", e.getMessage());
		}
		driver.get(url);
		logger.info("Successfully launched the url : {} with sessioId : {}", url, ((RemoteWebDriver) driver).getSessionId());
		seleniumUtils.setImplicitWait(30);
	}

	/**
	 * Perform teardown operation
	 */
	public void teadDown() {
		if (Objects.nonNull(driver)) {
			logger.info("Driver with session : {} is killed", ((RemoteWebDriver) driver).getSessionId());
			driver.quit();
		}
	}

}
