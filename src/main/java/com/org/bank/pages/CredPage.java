package com.org.bank.pages;

import com.org.bank.constants.Constants;
import com.org.bank.constants.CredModalContext;
import com.org.bank.modals.CredModal;
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
 * @Date 08/02/2023
 */
@Slf4j
public final class CredPage {

    private final SeleniumUtils seleniumUtils;
    private DbUtils dbUtils = null;
    private String emailId;
    private String loginPageUrl;

    private CredPage(WebDriver driver) {
        seleniumUtils = SeleniumUtils.newSeleniumUtils(driver);
        try {
            var fileReaderUtil = FileReaderUtil.newFileReaderUtil(Constants.ConfigurationFile);
            dbUtils = DbUtils.newDbUtils(fileReaderUtil.getPropertyValue("jdbcUrl"), fileReaderUtil.getPropertyValue("userName"), fileReaderUtil.getPropertyValue("password"));
            PageFactory.initElements(driver, this);
            var testUrlKey = "testUrl";
            loginPageUrl = fileReaderUtil.getPropertyValue(testUrlKey);
            var emailKey = "email";
            emailId = fileReaderUtil.getPropertyValue(emailKey);
        } catch (KeyNotValidException | ValueNotFoundException e) {
            log.error("Error while getting the value for key : {} with error message : {}", "testUrl",
                    e.getMessage());
        } catch (Exception e) {
            log.error("Error occurred with error message : {}", e.getMessage());
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
     * Enter user id in UserId field
     *
     * @return Instance of {@link CredPage} class
     */
    public CredPage enterEmailIdAndSubmit() {
        seleniumUtils.enterTextInWebElement(emailTextBox, emailId, true);
        seleniumUtils.performClick(submitButton);
        return this;
    }

    /**
     * Get the latest credentials and update them in db if outdated
     *
     * @return Instance of {@link CredPage} class
     */
    public CredPage getAndUpdateCredInDb() {
        String userId = getUserIdValue();
        String password = getPasswordValues();
        CredModal credModal = CredModal.builder().userName(userId).password(password).build();
        CredModalContext.setCredModal(credModal);
        List<Map<String, Object>> dataFromDb = dbUtils.selectQueryResult("select * from bank_cred");
        Map<String, Object> credFromDB = null;
        if (!dataFromDb.isEmpty()) {
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
        } else if (Objects.isNull(credFromDB)) {
            String updateQuery = "INSERT into bank_cred (user_id ,password) values ('%s' , '%s');";
            String formattedQuery = String.format(updateQuery, userId, password);
            dbUtils.updateQuery(formattedQuery);
        } else {
            log.warn("Updated credentials can not be updated as old data is not fetched from db");
        }
        return this;
    }

    /**
     * Navigate back to cred page after getting the latest credentials
     */
    public void navigateBack() {
        seleniumUtils.navigateBackward();
    }

    /**
     * Navigate to login page
     */
    public void navigateToLoginPage() {
        seleniumUtils.launchUrl(loginPageUrl);
        log.info("Navigated to : {} with updated credentials", loginPageUrl);
    }

}
