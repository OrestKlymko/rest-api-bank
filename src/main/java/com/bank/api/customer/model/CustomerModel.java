package com.bank.api.customer.model;

import com.bank.api.account.entity.AccountEntity;
import com.bank.api.customer.entity.CustomerEntity;
import lombok.Data;

@Data
public class CustomerModel {
	private Long id;
	private String cardNumber;
	private AccountEntity account;

	public static CustomerModel toModel(CustomerEntity customerEntity) {
		CustomerModel customerModel = new CustomerModel();

		customerModel.setId(customerEntity.getId());
		customerModel.setCardNumber(customerEntity.getCardNumber());

		customerModel.setAccount(customerEntity.getAccount());
		return customerModel;
	}
}
