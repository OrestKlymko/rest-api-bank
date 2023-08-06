package com.bank.api.customer.pojo;

import lombok.Data;

@Data
public class UserInputTransactionValue {
	 private String fromCard;
	 private String toCard;
	 private int balance;

	public UserInputTransactionValue(String toCard, String fromCard, int balance) {
		this.toCard=toCard;
		this.fromCard=fromCard;
		this.balance=balance;
	}
}
