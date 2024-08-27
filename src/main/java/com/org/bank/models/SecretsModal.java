package com.org.bank.models;

import lombok.Data;

@Data
public class SecretsModal {

	private String jdbcUrl;
	private String userName;
	private String password;
	private String driverClassName;
}
