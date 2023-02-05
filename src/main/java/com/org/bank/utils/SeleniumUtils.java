package com.org.bank.utils;

import java.io.File;
import java.time.Duration;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Lavendra Kumar Rajput
 *
 * @Date
 */
public class SeleniumUtils {

	private static int defaultTime = 30;

	Logger logger = LoggerFactory.getLogger(SeleniumUtils.class);

	private WebDriver driver;

	public SeleniumUtils(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * 
	 * 
	 * @param waitTimeInSecond
	 * @return
	 */
	private WebDriverWait getWebdriverWait(int waitTimeInSecond) {
		return new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSecond));

	}

	/**
	 * 
	 * @return
	 */
	private Actions getActions() {
		return new Actions(driver);
	}

	/**
	 * 
	 * @param element
	 * @return
	 */
	private String getLocatorFromWebElement(WebElement element) {
		return element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "");
	}

	/**
	 * 
	 * @param element
	 */
	public void waitForElementVisiblity(WebElement element) {
		getWebdriverWait(defaultTime).until(ExpectedConditions.visibilityOf(element));
		logger.info("Waiting for element to be visible for : {} seconds", defaultTime);
	}

	/**
	 * 
	 * @param element
	 * @param time
	 */
	public void waitForElementVisiblity(WebElement element, int time) {
		getWebdriverWait(time).until(ExpectedConditions.visibilityOf(element));
		logger.info("Waiting for element to be visible for : {} seconds", time);
	}

	/**
	 * 
	 * @param element
	 */
	public void waitForElementToBeClickable(WebElement element) {
		getWebdriverWait(defaultTime).until(ExpectedConditions.elementToBeClickable(element));
		logger.info("Waiting for element to be clickable for : {} seconds", defaultTime);
	}

	/**
	 * 
	 * @param element
	 * @param time
	 */
	public void waitForElementToBeClickable(WebElement element, int time) {
		getWebdriverWait(time).until(ExpectedConditions.elementToBeClickable(element));
		logger.info("Waiting for element to be clickable for : {} seconds", time);
	}

	/**
	 * 
	 */
	public void waitUntilAlertIsPresent() {
		getWebdriverWait(defaultTime).until(ExpectedConditions.alertIsPresent());
		logger.info("Waiting for alert to be present for  : {} seconds", defaultTime);
	}

	/**
	 * 
	 * @param time
	 */
	public void waitUntilAlertIsPresent(int time) {
		getWebdriverWait(time).until(ExpectedConditions.alertIsPresent());
		logger.info("Waiting for alert to be present for  : {} seconds", time);
	}

	/**
	 * 
	 * @param element
	 */
	public void waitForInvisisblityOfElement(WebElement element) {
		getWebdriverWait(defaultTime).until(ExpectedConditions.invisibilityOf(element));
	}

	/**
	 * 
	 * @param element
	 * @param time
	 */
	public void waitForInvisisblityOfElement(WebElement element, int time) {
		getWebdriverWait(time).until(ExpectedConditions.invisibilityOf(element));
	}

	/**
	 * 
	 * @param timeInSecond
	 */
	public void setImplicitWait(int timeInSecond) {
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSecond));
			logger.info("Implicit wait is set for : {} seconds", timeInSecond);
		} catch (Exception e) {
			logger.error("Error occured while setting the implicit wait");
		}
	}

	/**
	 * 
	 * @param element
	 * @param expectedText
	 */
	public void waitForExpectedText(WebElement element, String expectedText) {
		getWebdriverWait(defaultTime).until(ExpectedConditions.textToBePresentInElement(element, expectedText));
		logger.info("Waiting for element until text is : {} for  : {} seconds", expectedText, defaultTime);
	}

	/**
	 * 
	 * @param element
	 */
	public void performClickUsingActionClass(WebElement element) {
		waitForElementToBeClickable(element);
		String locator = getLocatorFromWebElement(element);
		try {
			getActions().click(element).build().perform();
			logger.info("Clicking on {} using action class", locator);
		} catch (Exception e) {
			logger.error("Error occured while clicking on element : {} with error message : {}", locator,
					e.getMessage());
		}
	}

	/**
	 * 
	 * @param element
	 */
	public void performClickUsingJSExecutor(WebElement element) {
		waitForElementToBeClickable(element);
		String locator = getLocatorFromWebElement(element);
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
			logger.info("Clicking on {} using javascript executor", locator);
		} catch (Exception e) {
			logger.error("Error occured while clicking on element : {} with error message : {}", locator,
					e.getMessage());
		}
	}

	/**
	 * 
	 * @param element
	 */
	public void performClick(WebElement element) {
		waitForElementToBeClickable(element);
		String locator = getLocatorFromWebElement(element);
		try {
			element.click();
			logger.info("Clicked on {} ", locator);
		} catch (Exception e) {
			logger.warn("Unable to click on {} using element.click() method trying anther way", locator);
			try {
				performClickUsingActionClass(element);
			} catch (Exception e1) {
				try {
					performClickUsingJSExecutor(element);
				} catch (Exception e2) {

				}
			}
		}
	}

	/**
	 * 
	 * 
	 * @param element
	 * @return
	 */
	public String getTextOfElement(WebElement element) {
		waitForElementVisiblity(element);
		String locator = getLocatorFromWebElement(element);
		try {
			String text = element.getText();
			logger.info("Text of {} is {}", locator, text);
			return text;
		} catch (Exception exception) {
			logger.error("Error occured while getting the text for {} with error message {}", locator,
					exception.getMessage());
		}
		return "";
	}

	/**
	 * 
	 * 
	 * @param element
	 * @param attributeName
	 * @return
	 */
	public String getAttribute(WebElement element, String attributeName) {
		waitForElementVisiblity(element);
		String locator = getLocatorFromWebElement(element);
		try {
			String text = element.getAttribute(attributeName);
			logger.info("Attribute of {} with attribute name {} is {}", locator, attributeName, text);
			return text;
		} catch (Exception exception) {
			logger.error("Error occured while getting the attribute for {} with error message {}", locator,
					exception.getMessage());
		}
		return "";
	}

	/**
	 * 
	 * @param element
	 * @return
	 */
	public boolean isWebElementPresent(WebElement element) {
		boolean isElementVisible = true;
		try {
			waitForElementVisiblity(element);
			isElementVisible = element.isDisplayed();
		} catch (Exception e) {
			isElementVisible = false;
		}
		return isElementVisible;
	}

	/**
	 * 
	 * @param element
	 * @return
	 */
	public boolean isWebElementEnabled(WebElement element) {
		waitForElementVisiblity(element);
		String locator = getLocatorFromWebElement(element);
		boolean isEnabled = true;
		try {
			isEnabled = element.isEnabled();
			logger.info("{} is in enabled state {}", locator, isEnabled);
		} catch (Exception e) {
			logger.error("Error occured while getting the element state for locator {} ", locator);
		}
		return isEnabled;
	}

	/**
	 * 
	 * @param element
	 * @param destination
	 */
	public void takeScreenShotOfWebElement(WebElement element, String destination) {
		try {
			if (isWebElementPresent(element) && Objects.nonNull(destination)) {
				File sourceFile = element.getScreenshotAs(OutputType.FILE);
				File destinationFile = new File(destination);
				FileUtils.copyFile(sourceFile, destinationFile);
				logger.info("Successfully copied the screenshot on {} ", destinationFile);
			}
		} catch (Exception e) {
			logger.error("Error occured while taking the screenshot with error message {}", e.getMessage());
		}
	}

	/**
	 * 
	 */
	public void maxiMizeWindow() {
		driver.manage().window().fullscreen();
	}

	/**
	 * 
	 * @param url
	 */
	public void launchUrl(String url) {
		driver.navigate().to(url);
		logger.info("Successfully lauched the url : {}", url);
	}

	/**
	 * 
	 * @return
	 */
	public String getTitle() {
		String title = driver.getTitle();
		logger.info("Current page title is : {} ", title);
		return title;
	}

	/**
	 * 
	 */
	public void refresh() {
		driver.navigate().refresh();
		logger.info("{} : Page is successfully refreshed", getTitle());
	}

	/**
	 * 
	 */
	public void navigateForward() {
		driver.navigate().forward();
		logger.info("Successfully navigated forward from page : {} ", driver.getTitle());
	}

	/**
	 * 
	 */
	public void navigateBackward() {
		driver.navigate().back();
		logger.info("Successfully navigated backward from page : {} ", driver.getTitle());
	}

	/**
	 * Get the alert text and accept
	 * 
	 * @return Text of the alert
	 */
	public String getAlertText() {
		waitUntilAlertIsPresent();
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		alert.accept();
		return alertText;
	}

	/**
	 * Accept the alert
	 */
	public void acceptAlert() {
		waitUntilAlertIsPresent();
		driver.switchTo().alert().accept();
	}

	/**
	 * Dismiss alert
	 */
	public void dismissAlert() {
		waitUntilAlertIsPresent();
		driver.switchTo().alert().accept();
	}

	/**
	 * Enter text in Web Element
	 * 
	 * @param element : WebElement to enter text;
	 * @param text    : Text to enter
	 */
	public void enterTextInWebElement(WebElement element, String text, boolean isAfterClear) {
		waitForElementToBeClickable(element);
		String locator = getLocatorFromWebElement(element);
		try {
			if (isAfterClear) {
				element.clear();
				element.sendKeys(text);
			} else {
				element.sendKeys(text);
			}
			logger.info("Enterd text : {} in : {}", text, locator);
		} catch (Exception e) {
			logger.error("Error while entering text in : {} with error message : {}", locator, e.getMessage());
		}
	}

}
