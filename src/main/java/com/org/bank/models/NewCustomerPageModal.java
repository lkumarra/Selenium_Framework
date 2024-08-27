package com.org.bank.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NewCustomerPageModal {
    @JsonProperty("headingmessage")
    private String headingMessage;
    @JsonProperty("fieldslabel")
    private String fieldsLabel;
    @JsonProperty("buttons")
    private String buttons;
    @JsonProperty("customernamefield")
    private String customerNameField;
    @JsonProperty("customernameexpectedmessage")
    private String customerNameExpectedMessage;
    @JsonProperty("dateofbirthfield")
    private String dateOfBirthField;
    @JsonProperty("dateofbirthexpectedmessage")
    private String dateOfBirthExpectedMessage;
    @JsonProperty("cityfield")
    private String cityField;
    @JsonProperty("cityfieldexpectedmessage")
    private String cityFieldExpectedMessage;
    @JsonProperty("statefield")
    private String stateField;
    @JsonProperty("statefieldexpectedmessage")
    private String stateFieldExpectedMessage;
    @JsonProperty("pincodefield")
    private String pincodeField;
    @JsonProperty("pincodeexpectedmessage")
    private String pinCodeExpectedMessage;
    @JsonProperty("mobilenumberfield")
    private String mobileNumberField;
    @JsonProperty("mobilenumberexpectedmessage")
    private String mobileNumberExpectedMessage;
    @JsonProperty("emailfield")
    private String emailField;
    @JsonProperty("emailexpectedmessage")
    private String emailExpectedMessage;
    @JsonProperty("passwordfield")
    private String passwordField;
    @JsonProperty("passwordexpectedmessage")
    private String passwordExpectedMessage;
    @JsonProperty("addressfield")
    private String addressField;
    @JsonProperty("addressexpectedmessage")
    private String addressExpectedMessage;
}
