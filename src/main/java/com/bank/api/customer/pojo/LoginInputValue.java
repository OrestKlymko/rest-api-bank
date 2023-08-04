package com.bank.api.customer.pojo;


import lombok.Data;

@Data
public class LoginInputValue {
	private String card_number;
	private int code;
}
