package com.bank.api.service;


import com.bank.api.CustomerRepository;
import com.bank.api.entity.CustomerEntity;
import com.bank.api.inputValue.LoginInputValue;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {



	@Autowired
	private CustomerRepository customerRepository;


	public CustomerEntity findById(Long id) {
		return customerRepository.findById(id).get();
	}




	public List<CustomerEntity> list() {
		return customerRepository.findAll();
	}

	public boolean login(LoginInputValue loginInputValue){
		CustomerEntity finderCustomer = customerRepository.getCustomerEntityByCardNumber(loginInputValue.getCard_number());
		if(finderCustomer!=null){
			if(loginInputValue.getCode()==finderCustomer.getCode()){
				return true;
			}
		}
		return false;
	}

}
