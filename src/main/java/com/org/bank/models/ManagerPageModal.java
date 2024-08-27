package com.org.bank.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ManagerPageModal {

    @JsonProperty("menuoptions")
    private String menuOptions;
    @JsonProperty("welcomemessage")
    private String welcomeMessage;
    @JsonProperty("manageridmessage")
    private String managerIdMessage;

}
