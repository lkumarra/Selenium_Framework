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
            FileReaderUtil fileReaderUtil = FileReaderUtil.newFileReaderUtil(Constants.ConfigurationFile);
            dbUtils = DbUtils.newDbUtils(fileReaderUtil.getPropertyValue("jdbcUrl"), fileReaderUtil.getPropertyValue("userName"), fileReaderUtil.getPropertyValue("password"));
            PageFactory.initElements(driver, this);
            String testUrlKey = "testUrl";
            loginPageUrl = fileReaderUtil.getPropertyValue(testUrlKey);
            String emailKey = "email";
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
     * This method is used to get and update the credentials in the database.
     * It first retrieves the user ID and password values, and creates a new CredModal object with these values.
     * The CredModal object is then set in the CredModalContext.
     * The method then retrieves the data from the database and checks if the data is not empty.
     * If the data is not empty, it checks if the user ID and password in the database are different from the retrieved values.
     * If they are different, it updates the user ID and password in the database.
     * If the data is empty, it inserts the user ID and password into the database.
     *
     * @return The current instance of the CredPage class.
     */
    public CredPage getAndUpdateCredInDb() {
        String userId = getUserIdValue();
        String password = getPasswordValues();
        CredModalContext.setCredModal(CredModal.builder().userName(userId).password(password).build());

        Map<String, Object> credFromDB = dbUtils.selectQueryResult("select * from bank_cred").stream().findFirst().orElse(null);

        if (credFromDB != null && !credFromDB.isEmpty()) {
            updateCredentialsIfNecessary(userId, password, credFromDB);
        } else {
            insertCredentials(userId, password);
        }

        return this;
    }

    /**
     * This private method is used to update the credentials in the database if necessary.
     * It takes the user ID, password, and a Map representing the current credentials in the database as parameters.
     * The method checks if the user ID and password in the database are different from the provided user ID and password.
     * If they are different, it constructs an SQL update query and executes it to update the user ID and password in the database.
     *
     * @param userId     The user ID to be checked and potentially updated in the database.
     * @param password   The password to be checked and potentially updated in the database.
     * @param credFromDB A Map representing the current credentials in the database.
     */
    private void updateCredentialsIfNecessary(String userId, String password, Map<String, Object> credFromDB) {
        if (!credFromDB.get("user_id").equals(userId) && !credFromDB.get("password").equals(password)) {
            String updateQuery = String.format("Update bank_cred set user_id = '%s', password = '%s' where user_id = '%s'", userId, password, credFromDB.get("user_id"));
            dbUtils.updateQuery(updateQuery);
        }
    }

    /**
     * This private method is used to insert the credentials into the database.
     * It takes the user ID and password as parameters.
     * The method constructs an SQL insert query and executes it to insert the user ID and password into the database.
     *
     * @param userId   The user ID to be inserted into the database.
     * @param password The password to be inserted into the database.
     */
    private void insertCredentials(String userId, String password) {
        String insertQuery = String.format("INSERT into bank_cred (user_id ,password) values ('%s' , '%s');", userId, password);
        dbUtils.updateQuery(insertQuery);
    }


    /**
     * This method is used to navigate back in the browser.
     * It calls the navigateBackward method of the seleniumUtils object, which performs the back navigation.
     */
    public void navigateBack() {
        seleniumUtils.navigateBackward();
    }


    /**
     * This method is used to navigate to the login page of the application.
     * It calls the launchUrl method of the seleniumUtils object, passing the login page URL as a parameter.
     * After the navigation, it logs an informational message indicating that the navigation has been successful and the credentials have been updated.
     */
    public void navigateToLoginPage() {
        seleniumUtils.launchUrl(loginPageUrl);
        log.info("Navigated to : {} with updated credentials", loginPageUrl);
    }

}
