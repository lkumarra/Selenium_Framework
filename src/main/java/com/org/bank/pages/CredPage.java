package com.org.bank.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.org.bank.exceptions.KeyNotValidException;
import com.org.bank.exceptions.ValueNotFoundException;
import com.org.bank.utils.DbUtils;
import com.org.bank.utils.FileReaderUtil;
import com.org.bank.utils.SeleniumUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class contains the methods and webelements related to CredPage of the
 * application
 * 
 * @author Lavendra Kumar Rajput
 *
 * @Date 08/02/2023
 */
@Slf4j
public final class CredPage {
	
	private final SeleniumUtils seleniumUtils;
	private final DbUtils dbUtils;
	private String emailId;
	private String loginPageUrl;

	private CredPage(WebDriver driver) {
		seleniumUtils = SeleniumUtils.newSeleniumUtils(driver);
		log.info("Successfully created the instance of class : {} inside class : {}",
				seleniumUtils.getClass().getName(), CredPage.class.getName());
		dbUtils = DbUtils.newDbUtils();
		log.info("Successfully created the instance of class : {} inside class : {}", dbUtils.getClass().getName(),
				CredPage.class.getName());
		FileReaderUtil fileReaderUtil = FileReaderUtil.newFileReaderUtil();
		log.info("Successfully created the instance of class : {} inside class : {}",
				fileReaderUtil.getClass().getName(), CredPage.class.getName());
		PageFactory.initElements(driver, this);
		try {
			String testUrlKey = "testUrl";
			loginPageUrl = fileReaderUtil.getPropertyValue(testUrlKey);
			String emailKey = "email";
			emailId = fileReaderUtil.getPropertyValue(emailKey);
		} catch (KeyNotValidException | ValueNotFoundException e) {
			log.error("Error while getting the value for key : {} with error message : {}", "testUrl",
					e.getMessage());
		}

	}
	
	public static CredPage newCredPage(WebDriver driver) {
		return new CredPage(driver);
	}

	@FindBy(how = How.XPATH, using = "(//h2[@style])[2]")
	private WebElement emailIdTitle;

	@FindBy(how = How.XPATH, using = "(//td[@align = 'right'])")
	private WebElement emailLabel;

	@FindBy(how = How.NAME, using = "emailid")
	private WebElement emailTextBox;

	@FindBy(how = How.ID, using = "message9")
	private WebElement errorMessage;

	@FindBy(how = How.NAME, using = "btnLogin")
	private WebElement submitButton;

	@FindBy(how = How.XPATH, using = "//td[contains(text(), 'User ID')]/following-sibling::td")
	private WebElement userIdValue;

	@FindBy(how = How.XPATH, using = "//td[contains(text(), 'Password')]/following-sibling::td")
	private WebElement passwordValue;

	/**
	 * Get the value of userId label
	 * 
	 * @return : UserId Label Value
	 */
	public String getUserIdValue() {
		return seleniumUtils.getTextOfElement(userIdValue);
	}

	/**
	 * Get the value of password label
	 * 
	 * @return : Password Label Value
	 */
	public String getPasswordValues() {
		return seleniumUtils.getTextOfElement(passwordValue);
	}

	/**
	 * Enter UserId in UserId field
	 * 
	 * @return Instance of {@link CredPage} class
	 */
	public CredPage enterEmailIdAndSubmit() {
		seleniumUtils.enterTextInWebElement(emailTextBox, emailId, true);
		seleniumUtils.performClick(submitButton);
		return this;
	}

	/**
	 * Get the latest credentials and update them in Db if outdated
	 * 
	 * @return Instance of {@link CredPage} class
	 */
	public CredPage getAndUpdateCredInDb() {
		String userId = getUserIdValue();
		String password = getPasswordValues();
		System.setProperty("userId", userId);
		System.setProperty("password", password);
		List<Map<String, Object>> dataFromDb = dbUtils.selectQueryResult("select * from bank_cred");
		Map<String, Object> credFromDB = null;
		if (Objects.nonNull(dataFromDb)) {
			credFromDB = dataFromDb.get(0);
		}
		if (Objects.nonNull(credFromDB) && !credFromDB.isEmpty()) {
			String userIdKey = "user_id";
			String passwordKey = "password";
			if (!credFromDB.get(userIdKey).equals(userId) && !credFromDB.get(passwordKey).equals(password)) {
				String updateQuery = "Update bank_cred set user_id = '%s', password = '%s' where user_id = '%s'";
				String formattedQuery = String.format(updateQuery, userId, password, credFromDB.get("user_id"));
				dbUtils.updateQuery(formattedQuery);
			}
		} else {
			log.warn("Updated credentials can not be updated as old data is not fetched from db");
		}
		return this;
	}

	/**
	 * Navigate Back to Cred Page after Getting Latest Credentials
	 */
	public void navigateBack() {
		seleniumUtils.navigateBackward();
	}

	/**
	 * Navigate to LoginPage
	 */
	public void navigateToLoginPage() {
		seleniumUtils.launchUrl(loginPageUrl);
		log.info("Navigated to : {} with updated credentials ", loginPageUrl);
	}

}
