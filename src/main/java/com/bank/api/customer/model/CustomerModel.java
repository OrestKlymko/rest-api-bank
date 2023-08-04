package com.bank.api.customer.model;

import com.bank.api.account.pojo.AccountEntity;
import com.bank.api.customer.pojo.CustomerEntity;
import lombok.Data;

@Data
public class CustomerModel {
	private Long id;
	private String cardNumer;
	private AccountEntity account;

	public static CustomerModel toModel(CustomerEntity customerEntity) {
		CustomerModel customerModel = new CustomerModel();

		customerModel.setId(customerEntity.getId());
		customerModel.setCardNumer(customerEntity.getCardNumber());

		customerModel.setAccount(customerEntity.getAccount());
		return customerModel;
	}
}
