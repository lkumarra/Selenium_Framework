package com.org.bank.driverfactory;

import com.org.bank.constants.Constants;
import com.org.bank.exceptions.KeyNotValidException;
import com.org.bank.exceptions.ValueNotFoundException;
import com.org.bank.utils.FileReaderUtil;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class RemoteDriverManager implements DriverManager {

    private final FileReaderUtil readerUtil;

    public RemoteDriverManager() {
        readerUtil = FileReaderUtil.newFileReaderUtil(Constants.CONFIG_FILE_PATH);
    }

    @Override
    public RemoteWebDriver getWebDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        // chromeOptions.addArguments("--headless");
        //chromeOptions.addArguments("--no-sandbox");
        // chromeOptions.addArguments("--disable-dev-shm-usage");
        //chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--ignore-ssl-errors=yes");
        chromeOptions.addArguments("--ignore-certificate-errors");
        String remoteDriverUrl;
        try {
            remoteDriverUrl = readerUtil.getPropertyValue("remote.driver.url");
        } catch (KeyNotValidException | ValueNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            return new RemoteWebDriver(new URL(remoteDriverUrl), chromeOptions);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
