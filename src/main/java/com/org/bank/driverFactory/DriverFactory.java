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
    private final ThreadLocal<WebDriver> threadLocal = new ThreadLocal<WebDriver>();

    private DriverFactory() {
        String setEnvironment = System.getProperty("env");
        String setBrowser = System.getProperty("browser");
        if (Objects.isNull(setEnvironment) && Objects.isNull(setBrowser)) {
            try {
                FileReaderUtil fileReaderUtil = FileReaderUtil.newFileReaderUtil(Constants.ConfigurationFile);
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
     * This method is used to create a new instance of the DriverFactory class.
     * It uses the private constructor of the DriverFactory class to create the new instance.
     *
     * @return A new instance of the DriverFactory class.
     */
    public static DriverFactory newDriverFactory() {
        return new DriverFactory();
    }

    /**
     * This method is responsible for setting up the WebDriver based on the current browser.
     * It first creates a DriverManager object based on the current browser.
     * Then, it logs the browser launch message and returns the WebDriver from the DriverManager.
     *
     * @return A WebDriver instance corresponding to the current browser.
     */
    private WebDriver setupWebDriver() {
        // Create a DriverManager object
        DriverManager driverManager;

        // Determine the type of DriverManager to create based on the current browser
        switch (currentBrowser.toLowerCase()) {
            case "firefox":
                driverManager = new FirefoxDriverManager();
                break;
            case "edge":
                driverManager = new EdgerDriverManager();
                break;
            case "safari":
                driverManager = new SafariDriverManager();
                break;
            default:
                driverManager = new ChromerDriverManager();
        }

        // Log the browser launch message
        log.info("Launching the browser : {}", currentBrowser.toLowerCase());

        // Return the WebDriver from the DriverManager
        return driverManager.getWebDriver();
    }

    /**
     * This method is used to get the WebDriver instance stored in the ThreadLocal variable.
     * ThreadLocal is used to store data that will be accessed only by a specific thread.
     *
     * @return The WebDriver instance for the current thread.
     */
    public WebDriver getWebDriver() {
        return threadLocal.get();
    }

}
