package com.org.bank.pages;

import java.util.Objects;

import com.org.bank.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.org.bank.utils.FileReaderUtil;
import com.org.bank.utils.SeleniumUtils;

/**
 * This is the Base Class of the application
 *
 * @author Lavendra Kumar Rajput
 * @Date 08/02/2023
 */
@Slf4j
public class BasePage {

    private FileReaderUtil util;
    private SeleniumUtils seleniumUtils;
    private WebDriver driver;

    private BasePage(WebDriver driver) {
        try {
            this.driver = driver;
            util = FileReaderUtil.newFileReaderUtil(Constants.ConfigurationFile);
            seleniumUtils = SeleniumUtils.newSeleniumUtils(driver);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error occurred with error message : {} and throwable : {} ", e.getMessage(), e.getStackTrace());
        }
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
            log.warn("Url loaded from file is : {}", url);
        } catch (Exception e) {
            log.error("Error occurred file getting the url with error message : {} ", e.getMessage());
        }
        driver.get(url);
        log.warn("Successfully launched the url : {} with sessionId : {}", url, ((RemoteWebDriver) driver).getSessionId());
        seleniumUtils.setImplicitWait(30);
        seleniumUtils.maximizeWindow();
    }

    /**
     * Perform teardown operation
     */
    public void tearDown() {
        if (Objects.nonNull(driver)) {
            log.warn("Driver with session : {} is killed", ((RemoteWebDriver) driver).getSessionId());
            driver.quit();
        }
    }

}
