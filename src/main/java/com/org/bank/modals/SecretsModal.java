package com.org.bank.modals;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecretsModal {

	private String jdbcUrl;
	private String userName;
	private String password;
	private String driverClassName;
}
