package com.bank.api.customer.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserInputTransactionValue {
	 private String fromCard;
	 private String toCard;
	 private BigDecimal balance;

	public UserInputTransactionValue(String toCard, String fromCard, BigDecimal balance) {
		this.toCard=toCard;
		this.fromCard=fromCard;
		this.balance=balance;
	}
}
