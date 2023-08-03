package com.bank.api.inputValue;


import lombok.Data;

@Data
public class LoginInputValue {
	private long id;
	private String card_number;
	private int code;
}
