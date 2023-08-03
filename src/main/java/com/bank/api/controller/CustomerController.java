package com.bank.api.controller;


import com.bank.api.entity.CustomerEntity;
import com.bank.api.inputValue.LoginInputValue;
import com.bank.api.inputValue.UserInputTransactionValue;
import com.bank.api.service.AccountService;
import com.bank.api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private AccountService accountService;



	@GetMapping("/client/{id}")
	public CustomerEntity getCustomerById(@PathVariable("id") long id){
		return customerService.findById(id);
	}


	@PostMapping("/transaction")
	public List<CustomerEntity> transaction( @RequestBody UserInputTransactionValue userInputTransactionValue){
		accountService.makeTransaction(userInputTransactionValue.getToCustomerId(), userInputTransactionValue.getFromCustomerId(), userInputTransactionValue.getBalance());
		return customerService.list();
	}


	@PostMapping("/login")
	public boolean login(@RequestBody LoginInputValue loginInputValue){
		return customerService.login(loginInputValue);
	}
}
