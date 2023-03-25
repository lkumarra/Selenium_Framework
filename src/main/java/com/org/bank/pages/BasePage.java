package com.org.bank.pages;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.org.bank.utils.FileReaderUtil;
import com.org.bank.utils.SeleniumUtils;

/**
 * This is the Base Class of the application
 * 
 * @author Lavendra Kumar Rajput
 *
 * @Date 08/02/2023
 */
@Slf4j
public class BasePage {

	private final FileReaderUtil util;
	private final SeleniumUtils seleniumUtils;
	private final WebDriver driver;

	private BasePage(WebDriver driver) {
		this.driver = driver;
		util = FileReaderUtil.newFileReaderUtil();
		log.info("Successfully created the instance of class : {} inside class : {}", util.getClass().getName(),
				BasePage.class.getName());
		seleniumUtils = SeleniumUtils.newSeleniumUtils(driver);
		log.info("Successfully created the instance of class : {} inside class : {}",
				seleniumUtils.getClass().getName(), BasePage.class.getName());
	}
	
	public static BasePage newBasePage(WebDriver driver) {
		return new BasePage(driver);
	}

	/**
	 * Perform initialization operation
	 */
	public void initialization() {
		String url = "";
		try {
			url = util.getPropertyValue("url");
			log.info("Url loaded from file is : {}", url);
		} catch (Exception e) {
			log.error("Error occurred file getting the url with error message : {} ", e.getMessage());
		}
		driver.get(url);
		log.info("Successfully launched the url : {} with sessionId : {}", url, ((RemoteWebDriver) driver).getSessionId());
		seleniumUtils.setImplicitWait(30);
		seleniumUtils.maximizeWindow();
	}

	/**
	 * Perform teardown operation
	 */
	public void tearDown() {
		if (Objects.nonNull(driver)) {
			log.info("Driver with session : {} is killed", ((RemoteWebDriver) driver).getSessionId());
			driver.quit();
		}
	}

}
