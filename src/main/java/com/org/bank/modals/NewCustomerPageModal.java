package com.org.bank.modals;

import lombok.Data;

@Data
public class NewCustomerPageModal {
	
	private String headingmessage;
	private String fieldslabel;
	private String buttons;
	private String customernamefield;
	private String customernameexpectedmessage;
	private String dateofbirthfield;
	private String dateofbirthexpectedmessage;
	private String cityfield;
	private String cityfieldexpectedmessage;
	private String statefield;
	private String statefieldexpectedmessage;
	private String pincodefield;
	private String pincodeexpectedmessage;
	private String mobilenumberfield;
	private String mobilenumberexpectedmessage;
	private String emailfield;
	private String emailexpectedmessage;
	private String passwordfield;
	private String passwordexpectedmessage;
	private String addressfield;
	private String addressexpectedmessage;
}
