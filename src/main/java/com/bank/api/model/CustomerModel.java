package com.bank.api.model;

import com.bank.api.entity.AccountEntity;
import com.bank.api.entity.CustomerEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class CustomerModel {
	private Long id;
	private String name;
	private int code;
	private AccountEntity account;

	public static CustomerModel toModel(CustomerEntity customerEntity){
		CustomerModel customerModel = new CustomerModel();

		return customerModel;
	}
}
