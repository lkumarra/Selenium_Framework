package com.org.bank.driverFactory;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * This class contains the method related ot chrome class
 *
 * @author Lavendra Kumar Rajput
 * @Date 09/02/2023
 */
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
        ChromeOptions chromeOptions = new ChromeOptions();
        return new ChromeDriver(chromeOptions);
    }
}
