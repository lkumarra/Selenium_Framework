package com.org.bank.driverfactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * This class contains the method related ot chrome class
 *
 * @author Lavendra Kumar Rajput
 * @Date 09/02/2023
 */
@Slf4j
public final class ChromerDriverManager implements DriverManager {


    /**
     * This method is responsible for creating and returning a ChromeDriver instance.
     * It first creates a ChromeOptions object, which can be used to customize the behavior of the ChromeDriver.
     * Then, it creates a new ChromeDriver using these options and returns it.
     *
     * @return A new instance of ChromeDriver with the specified options.
     */
    @Override
    public ChromeDriver getWebDriver() {
        WebDriverManager.chromiumdriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary("/usr/bin/chromium");
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-gpu");
        log.info("Chrome options are set");
        return new ChromeDriver(chromeOptions);
    }
}
