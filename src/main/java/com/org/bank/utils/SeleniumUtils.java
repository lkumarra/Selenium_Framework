package com.org.bank.utils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This Class contains the method related to selenium utility
 * 
 * @author Lavendra Kumar Rajput
 *
 * @Date 08/02/2023
 */
@Slf4j
public class SeleniumUtils {

	private static int defaultTime = 30;

	private final WebDriver driver;

	private SeleniumUtils(WebDriver driver) {
		this.driver = driver;
	}
	
	private SeleniumUtils(WebDriver driver, int waitTimeInSecond) {
		this.driver = driver;
		defaultTime = waitTimeInSecond;
	}
	
	public static SeleniumUtils newSeleniumUtils(WebDriver driver) {
		return new SeleniumUtils(driver);
	}
	
	public static SeleniumUtils newSeleniumUtils(WebDriver driver, int waitTimeInSecond) {
		return new SeleniumUtils(driver, waitTimeInSecond);
	}
	/**
	 * 
	 * This method will return the instance of WebDriverWait classSS
	 * 
	 * @param waitTimeInSecond : Max time to wait in seconds
	 * @return Instance of {@link WebDriverWait} class
	 */
	private WebDriverWait getWebdriverWait(int waitTimeInSecond) {
		return new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSecond));

	}

	/**
	 * This method will return the instance of Action class
	 * 
	 * @return Instance of {@link Actions} class
	 */
	private Actions getActions() {
		return new Actions(driver);
	}

	/**
	 * Get the locatoe for webelement
	 * 
	 * @param element : Webelement to fetch locator
	 * @return Locator with finding strategy Example : Xpath : //*[@className =
	 *         'abc']
	 */
	private String getLocatorFromWebElement(WebElement element) {
		return element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "");
	}

	/**
	 * Wait for the visibility of webelement for default 30 second
	 * 
	 * @param element : Webelement to check the visibility
	 */
	public void waitForElementVisibility(WebElement element) {
		getWebdriverWait(defaultTime).until(ExpectedConditions.visibilityOf(element));
		log.info("Waiting for element to be visible for : {} seconds", defaultTime);
	}

	/**
	 * Wait for the visibility of webelement for given time
	 * 
	 * @param element : Webelement to check visiblity
	 * @param time    : Time to check visiblity in seconds
	 */
	public void waitForElementVisibility(WebElement element, int time) {
		getWebdriverWait(time).until(ExpectedConditions.visibilityOf(element));
		log.info("Waiting for element to be visible for : {} seconds", time);
	}

	/**
	 * Wait for element to be in clickable state for default max 30 second
	 * 
	 * @param element : Webelement to check clickable state
	 */
	public void waitForElementToBeClickable(WebElement element) {
		getWebdriverWait(defaultTime).until(ExpectedConditions.elementToBeClickable(element));
		log.info("Waiting for element to be clickable for : {} seconds", defaultTime);
	}

	/**
	 * Wait for element to be in clickable state for given time in seconds
	 * 
	 * @param element : Webelement to check clickable state
	 * @param time    : Max time to wait
	 */
	public void waitForElementToBeClickable(WebElement element, int time) {
		getWebdriverWait(time).until(ExpectedConditions.elementToBeClickable(element));
		log.info("Waiting for element to be clickable for : {} seconds", time);
	}

	/**
	 * Wait for alert to be present for max default time of 30 second
	 */
	public void waitUntilAlertIsPresent() {
		getWebdriverWait(defaultTime).until(ExpectedConditions.alertIsPresent());
		log.info("Waiting for alert to be present for  : {} seconds", defaultTime);
	}

	/**
	 * Wait for alert to be present for max given time in seconds
	 * 
	 * @param time : Max time to wait for an alert
	 */
	public void waitUntilAlertIsPresent(int time) {
		getWebdriverWait(time).until(ExpectedConditions.alertIsPresent());
		log.info("Waiting for alert to be present for  : {} seconds", time);
	}

	/**
	 * This method will set an implicit of time in seconds for
	 * 
	 * @param timeInSecond : Maximum time to wait for an element in seconds
	 */
	public void setImplicitWait(int timeInSecond) {
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSecond));
			log.info("Implicit wait is set for : {} seconds", timeInSecond);
		} catch (Exception e) {
			log.error("Error occurred while setting the implicit wait");
		}
	}

	/**
	 * This method will wait for webelement to check the desired text of the
	 * webelement for max default time of 30 seconds
	 * 
	 * @param element      : Webelement to check the text
	 * @param expectedText : Expected text
	 */
	public void waitForExpectedText(WebElement element, String expectedText) {
		getWebdriverWait(defaultTime).until(ExpectedConditions.textToBePresentInElement(element, expectedText));
		log.info("Waiting for element until text is : {} for  : {} seconds", expectedText, defaultTime);
	}

	/**
	 * This method will perform click using actions class
	 * 
	 * @param element : Webelement to perform click action
	 */
	public void performClickUsingActionClass(WebElement element) {
		waitForElementToBeClickable(element);
		String locator = getLocatorFromWebElement(element);
		try {
			getActions().click(element).build().perform();
			log.info("Clicking on {} using action class", locator);
		} catch (Exception e) {
			log.error("Error occurred while clicking on element : {} with error message : {}", locator,
					e.getMessage());
		}
	}

	/**
	 * This method will perform click using JavaScript executor
	 * 
	 * @param element : Webelement to perform click action
	 */
	public void performClickUsingJSExecutor(WebElement element) {
		waitForElementToBeClickable(element);
		String locator = getLocatorFromWebElement(element);
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
			log.info("Clicking on {} using javascript executor", locator);
		} catch (Exception e) {
			log.error("Error occurred while clicking on element : {} with error message : {}", locator,
					e.getMessage());
		}
	}

	/**
	 * This method will perform click by trying different ways of click
	 * 
	 * @param element : Webelement to perform click action
	 */
	public void performClick(WebElement element) {
		waitForElementToBeClickable(element);
		String locator = getLocatorFromWebElement(element);
		try {
			element.click();
			log.info("Clicked on {} ", locator);
		} catch (Exception e) {
			log.warn("Unable to click on {} using element.click() method trying anther way", locator);
			try {
				performClickUsingActionClass(element);
			} catch (Exception e1) {
				try {
					performClickUsingJSExecutor(element);
				} catch (Exception e2) {
					log.error("Error occurred while clicking on : {} with error message : {}", locator, e2.getMessage());
				}
			}
		}
	}

	/**
	 * 
	 * Get the text of the webelement
	 * 
	 * @param element : Webelement to get text
	 * @return Text of the webelement
	 */
	public String getTextOfElement(WebElement element) {
		waitForElementVisibility(element);
		String locator = getLocatorFromWebElement(element);
		try {
			String text = element.getText();
			log.info("Text of {} is {}", locator, text);
			return text;
		} catch (Exception exception) {
			log.error("Error occurred while getting the text for {} with error message {}", locator,
					exception.getMessage());
		}
		return "No text is found";
	}

	/**
	 * 
	 * Get the attribute value of the webelement
	 * 
	 * @param element       : Webelement to get attribute
	 * @param attributeName : Name of the attribute
	 * @return attribute value
	 */
	public String getAttribute(WebElement element, String attributeName) {
		waitForElementVisibility(element);
		String locator = getLocatorFromWebElement(element);
		try {
			String text = element.getAttribute(attributeName);
			log.info("Attribute of {} with attribute name {} is {}", locator, attributeName, text);
			return text;
		} catch (Exception exception) {
			log.error("Error occurred while getting the attribute for {} with error message {}", locator,
					exception.getMessage());
		}
		return "No attribute value found";
	}

	/**
	 * This method will check whether a webelement is present or not
	 * 
	 * @param element : Webelement to check presence
	 * @return True if webelement is present else False
	 */
	public boolean isWebElementPresent(WebElement element) {
		boolean isElementVisible = true;
		try {
			waitForElementVisibility(element);
			isElementVisible = element.isDisplayed();
		} catch (Exception e) {
			isElementVisible = false;
		}
		return isElementVisible;
	}

	/**
	 * This method will check whether a webelement is enabled or not
	 * 
	 * @param element : Webelement to check
	 * @return True if webelement is enabled else False
	 */
	public boolean isWebElementEnabled(WebElement element) {
		waitForElementVisibility(element);
		String locator = getLocatorFromWebElement(element);
		boolean isEnabled = true;
		try {
			isEnabled = element.isEnabled();
			log.info("{} is in enabled state {}", locator, isEnabled);
		} catch (Exception e) {
			log.error("Error occurred while getting the element state for locator {} ", locator);
		}
		return isEnabled;
	}

	/**
	 * This method will take the screenshot of the webelement
	 * 
	 * @param element:    Webelement to take screenshot
	 * @param destination : Destination to copy the screenshot
	 */
	public void takeScreenShotOfWebElement(WebElement element, String destination) {
		try {
			if (isWebElementPresent(element) && Objects.nonNull(destination)) {
				File sourceFile = element.getScreenshotAs(OutputType.FILE);
				File destinationFile = new File(destination);
				FileUtils.copyFile(sourceFile, destinationFile);
				log.info("Successfully copied the screenshot on {} ", destinationFile);
			}
		} catch (Exception e) {
			log.error("Error occurred while taking the screenshot with error message {}", e.getMessage());
		}
	}

	/**
	 * This method will maximize the window
	 */
	public void maximizeWindow() {
		driver.manage().window().maximize();
	}

	/**
	 * This method will full screen the window
	 */
	public void fullScreenWindow() {
		driver.manage().window().fullscreen();
	}

	/**
	 * This method will lauch the url
	 * 
	 * @param url : Url to launch
	 */
	public void launchUrl(String url) {
		driver.navigate().to(url);
		log.info("Successfully launched the url : {}", url);
	}

	/**
	 * This method will return the title of the current page
	 * 
	 * @return : Title of the current page
	 */
	public String getTitle() {
		String title = driver.getTitle();
		log.info("Current page title is : {} ", title);
		return title;
	}

	/**
	 * This method will refresh the page
	 */
	public void refresh() {
		driver.navigate().refresh();
		log.info("{} : Page is successfully refreshed", getTitle());
	}

	/**
	 * This method will navigate forward
	 */
	public void navigateForward() {
		driver.navigate().forward();
		log.info("Successfully navigated forward from page : {} ", driver.getTitle());
	}

	/**
	 * This method will navigate backword
	 */
	public void navigateBackward() {
		driver.navigate().back();
		log.info("Successfully navigated backward from page : {} ", driver.getTitle());
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
			} else
				element.sendKeys(text);
			log.info("Entered text : {} in : {}", text, locator);
		} catch (Exception e) {
			log.error("Error while entering text in : {} with error message : {}", locator, e.getMessage());
		}
	}

	/**
	 * This method is responsible for taking the screenshot and copying that on
	 * desired destination
	 * 
	 * @param destination : Destination to copy the screenshot
	 */
	public void takesWebPageScreenShot(String destination) {
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(destination);
		try {
			FileUtils.copyFile(srcFile, destFile);
			log.info("Successfully copied the screenshot at destination : {}", destination);
		} catch (IOException e) {
			log.error("Error occurred file copying the screenshot at destination : {} with error message :{}",
					destination, e.getMessage());
		}
	}

	/**
	 * This method will return the List of text of webelements
	 * 
	 * @param elementsList : List of Webelements
	 * @return List of text of webelements
	 */
	public List<String> getWebElementsText(List<WebElement> elementsList) {
		List<String> list = new ArrayList<String>();
		try {
			for (WebElement element : elementsList) {
				waitForElementVisibility(element);
				String locatorText = getLocatorFromWebElement(element);
				String text = element.getText();
				log.info("Test of locator : {} is : {}", locatorText, text);
				list.add(element.getText());
			}
		} catch (Exception e) {
			log.error("Error occurred while getting the text of elements with error : {}", e.getMessage());
		}
		return list;
	}

	/**
	 * Select the value from webelement using select class
	 * 
	 * @param element : Webelement to select
	 * @param value   : Value to select
	 */
	public void selectByValue(WebElement element, String value) {
		try {
			waitForElementVisibility(element);
			String locatorText = getLocatorFromWebElement(element);
			Select select = new Select(element);
			select.selectByValue(value);
			log.info("Selected value : {} from : {}", value, locatorText);
		} catch (Exception e) {
			log.error("Error while selecting the value : {} with error message : {} ", value, e.getMessage());
		}
	}

	public void selectByIndex(WebElement element, int index) {
		try {
			waitForElementVisibility(element);
			String locatorText = getLocatorFromWebElement(element);
			Select select = new Select(element);
			select.selectByIndex(index);
			log.info("Selected index : {} from : {}", index, locatorText);
		} catch (Exception e) {
			log.error("Error while selecting index : {} with error message : {}", index, e.getMessage());
		}
	}

	public void selectByVisibleText(WebElement element, String visibleText) {
		try {
			waitForElementVisibility(element);
			String locatorText = getLocatorFromWebElement(element);
			Select select = new Select(element);
			select.selectByVisibleText(visibleText);
			log.info("Selected text : {} from : {}", visibleText, locatorText);
		} catch (Exception e) {
			log.error("Error while selecting visible text : {} with error message : {}", visibleText,
					e.getMessage());
		}
	}

	public void deselectAll(WebElement element) {
		try {
			waitForElementVisibility(element);
			String locatorText = getLocatorFromWebElement(element);
			Select select = new Select(element);
			select.deselectAll();
			log.info("Deselected all options from : {}", locatorText);
		} catch (Exception e) {
			log.error("Error while deselecting the options");
		}
	}

}
