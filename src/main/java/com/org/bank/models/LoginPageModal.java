package com.org.bank.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class LoginPageModal {
    @JsonProperty("passwordlabel")
    private String passwordLabel;
    @JsonProperty("password")
    private String password;
    @JsonProperty("resetbuttonlabel")
    private String resetButtonLabel;
    @JsonProperty("expectedmessage")
    private String expectedMessage;
    @JsonProperty("usedidlabel")
    private String usedIdLabel;
    @JsonProperty("submitbuttonlabel")
    private String submitButtonLabel;
    @JsonProperty("title")
    private String title;
    @JsonProperty("userid")
    private String userId;
}
