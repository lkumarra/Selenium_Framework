package com.org.bank.driverFactory;

import java.util.Objects;

import com.org.bank.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import com.org.bank.utils.FileReaderUtil;

/**
 * This class contains the method related to web-driver initialization
 *
 * @author Lavendra Kumar Rajput
 * @Date 09/02/2023
 */
@Slf4j
public final class DriverFactory {

    private final String currentBrowser;
    private final ThreadLocal<WebDriver> threadLocal = new ThreadLocal<>();
    private DriverFactory() {
        threadLocal.remove();
        String setEnvironment = System.getProperty("env");
        String setBrowser = System.getProperty("browser");
        if (Objects.isNull(setEnvironment) && Objects.isNull(setBrowser)) {
            try {
                var fileReaderUtil = FileReaderUtil.newFileReaderUtil(Constants.ConfigurationFile);
                setEnvironment = fileReaderUtil.getPropertyValue("env");
                setBrowser = fileReaderUtil.getPropertyValue("browser");
                log.info("Setting the environment to : {} and browser to : {}", setEnvironment, setBrowser);
            } catch (Exception e) {
                assert log != null;
                log.error("Error occurred while setting the env variables with error message : {} ", e.getMessage());
            }
        }
        String currentEnvironment = setEnvironment;
        currentBrowser = setBrowser;
        log.info("Current environment is : {} and browser is {} : ", currentEnvironment, currentBrowser);
        threadLocal.set(setupWebDriver());
    }

    /**
     * Return the instance of DriverFactory Class
     *
     */
    public static DriverFactory newDriverFactory() {
        return new DriverFactory();
    }

    /**
     * Set up the web driver;
     *
     * @return Web driver instance
     */
    private WebDriver setupWebDriver() {
        switch (currentBrowser.toLowerCase()) {
            case "firefox":
                logInfo(currentBrowser.toLowerCase());
                return new FirefoxDriverManager().getWebDriver();
            case "edge":
                logInfo(currentBrowser.toLowerCase());
                return new EdgerDriverManager().getWebDriver();
            case "safari":
                logInfo(currentBrowser.toLowerCase());
                return new SafariDriverManager().getWebDriver();
            default:
                logInfo(currentBrowser.toLowerCase());
                return new ChromerDriverManager().getWebDriver();
        }
    }

    private void logInfo(String currentBrowser){
        log.info("Launching the browser : {}", currentBrowser.toLowerCase());
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
