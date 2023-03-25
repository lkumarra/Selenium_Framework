package com.org.bank.modals;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LoginPageModal {

	private String passwordlabel;
	private String password;
	private String resetbuttonlabel;
	private String expectedmessage;
	private String usedidlabel;
	private String submitbuttonlabel;
	private String title;
	private String userid;

}
