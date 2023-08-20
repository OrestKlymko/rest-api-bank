package com.bank.api.customer;


import com.bank.api.customer.exception.*;
import com.bank.api.customer.entity.CustomerEntity;
import com.bank.api.customer.pojo.LoginInputValue;
import com.bank.api.customer.pojo.RegistrationInputValue;
import com.bank.api.customer.pojo.UserInputTransactionValue;
import com.bank.api.customer.repo.CustomerRepository;
import com.bank.api.account.service.AccountService;
import com.bank.api.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/user",produces = "application/json")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private AccountService accountService;

	@Autowired
	private CustomerRepository customerRepository;


	@PostMapping("/transaction")
	public ResponseEntity transaction(@RequestBody UserInputTransactionValue userInputTransactionValue) {
		try {
			return ResponseEntity.ok(accountService.makeTransaction(userInputTransactionValue));
		} catch (CustomerNotHaveEnoughMoney e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginInputValue loginInputValue) {
		try {
			return ResponseEntity.ok(customerService.login(loginInputValue));
		} catch (IncorrectPassword | CustomerNotFound e) {
			return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(e.getMessage());
		}
	}

	@PostMapping("/login/client")
	public ResponseEntity loginAndShowInfoAboutClient(@RequestBody LoginInputValue loginInputValue) {
		try {
			return ResponseEntity.ok(customerService.loginAndShowInfoAboutClient(loginInputValue));
		} catch (IncorrectPassword | CustomerNotFound | LoginFailed e) {
			return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(e.getMessage());
		}
	}

	@PostMapping("/registration")
	public ResponseEntity registration(@RequestBody RegistrationInputValue registrationInputValue) {
		try {
			return ResponseEntity.ok(customerService.registration(registrationInputValue));
		} catch (CustomerAlreadyExist | PasswordNotHave4Digits e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/client/{id}")
	public ResponseEntity findClient(@PathVariable int id) {
		try {
			return ResponseEntity.ok(customerService.getOneClient(id));
		} catch (CustomerNotFound e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/list")
	public List<CustomerEntity> returnAll() {
		return customerRepository.findAll();
	}
}
