package com.org.bank.pages;

import static com.org.bank.driverFactory.DriverManager.*;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.org.bank.utils.FileReaderUtil;
import com.org.bank.utils.SeleniumUtils;

public class BasePage {

	Logger logger = LoggerFactory.getLogger(FileReaderUtil.class);

	private FileReaderUtil util;
	private SeleniumUtils seleniumUtils;
	public BasePage() {
		util = new FileReaderUtil();
		seleniumUtils = new SeleniumUtils(getWebDriver());
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
		getWebDriver().get(url);
		logger.info("Successfully launched the url : {}", url);
		seleniumUtils.setImplicitWait(30);
		
	}

	/**
	 * Perform teardown operation
	 */
	public void teadDown() {
		if (Objects.nonNull(getWebDriver())) {
			getWebDriver().quit();
		}
	}

}
