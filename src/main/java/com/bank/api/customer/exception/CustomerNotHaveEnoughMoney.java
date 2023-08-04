package com.bank.api.customer.exception;

public class CustomerNotHaveEnoughMoney extends Exception{
	public CustomerNotHaveEnoughMoney(String message) {
		super(message);
	}
}
