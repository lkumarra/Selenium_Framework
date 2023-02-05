package com.org.bank.exceptions;

@SuppressWarnings("serial")
public class KeyNotValidException extends Exception{
	
	public KeyNotValidException(String message) {
		super(message);
	}
}
