package com.org.bank.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.org.bank.exceptions.KeyNotValidException;
import com.org.bank.exceptions.ValueNotFoundException;
import com.org.bank.utils.DbUtils;
import com.org.bank.utils.FileReaderUtil;
import com.org.bank.utils.SeleniumUtils;
import static com.org.bank.driverFactory.DriverManager.*;

import java.util.Map;

public class CredPage {

	private Logger logger = LoggerFactory.getLogger(CredPage.class);
	private SeleniumUtils seleniumUtils;
	private DbUtils dbUtils;
	private String emailId;
	private String loginPageUrl;
	private FileReaderUtil fileReaderUtil;

	public CredPage() {
		PageFactory.initElements(getWebDriver(), this);
		seleniumUtils = new SeleniumUtils(getWebDriver());
		dbUtils = new DbUtils();
		fileReaderUtil = new FileReaderUtil();
		try {
			loginPageUrl = fileReaderUtil.getPropertyValue("testUrl");
			emailId = fileReaderUtil.getPropertyValue("email");
		} catch (KeyNotValidException | ValueNotFoundException e) {
			logger.error("Error while getting the value for key : {} with error message : {}", "testUrl",
					e.getMessage());
		}

	}

	@FindBy(how = How.XPATH, using = "(//h2[@style])[2]")
	private WebElement emailIdTitle;

	@FindBy(how = How.XPATH, using = "(//td[@align = 'right'])")
	private WebElement emaiLable;

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

	public String getUserIdValue() {
		return seleniumUtils.getTextOfElement(userIdValue);
	}

	public String getPasswordValues() {
		return seleniumUtils.getTextOfElement(passwordValue);
	}

	public CredPage enterEmailIdAndSubmit() {
		seleniumUtils.enterTextInWebElement(emailTextBox, emailId, true);
		seleniumUtils.performClick(submitButton);
		return this;
	}

	public CredPage getAndUpdateCredInDb() {
		String userId = getUserIdValue();
		String password = getPasswordValues();
		Map<String, Object> credFromDB = dbUtils.selectQueryResult("select * from bank_cred").get(0);
		if (!credFromDB.get("user_id").equals(userId) && !credFromDB.get("password").equals(password)) {
			String formattedQuery = String.format(
					"Update bank_cred set user_id = '%s', password = '%s' where user_id = '%s'", userId, password,
					credFromDB.get("user_id"));
			dbUtils.updateQuery(formattedQuery);
		}
		return this;
	}

	public CredPage navigateBack() {
		seleniumUtils.navigateBackward();;
		return this;
	}
	public LoginPage navigateToLoginPage() {
		seleniumUtils.launchUrl(loginPageUrl);
		logger.info("Navigated to : {} with updated credentials ", loginPageUrl);
		return new LoginPage();
	}

}
