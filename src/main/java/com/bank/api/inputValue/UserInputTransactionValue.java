package com.bank.api.inputValue;

import lombok.Data;

@Data
public class UserInputTransactionValue {
	 private int fromCustomerId;
	 private int toCustomerId;
	 private int balance;
}
