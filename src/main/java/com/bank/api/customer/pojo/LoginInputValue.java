package com.bank.api.customer.pojo;


import lombok.Data;

@Data
public class LoginInputValue {
	private String card_number;
	private double code;

	public LoginInputValue(String card_number, double code) {
		this.card_number = card_number;
		this.code = code;
	}

	public LoginInputValue() {
	}
}
