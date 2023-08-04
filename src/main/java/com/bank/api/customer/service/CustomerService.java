package com.bank.api.customer.service;


import com.bank.api.customer.exception.*;
import com.bank.api.customer.model.CustomerModel;
import com.bank.api.customer.pojo.RegistrationInputValue;
import com.bank.api.customer.repo.CustomerRepository;
import com.bank.api.customer.pojo.CustomerEntity;
import com.bank.api.customer.pojo.LoginInputValue;
import com.bank.api.account.pojo.AccountEntity;
import com.bank.api.account.pojo.AccountHistoryEntity;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CustomerService {


	@Autowired
	private CustomerRepository customerRepository;
	private Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder(32, 64, 1, 15 * 1024, 2);


	public CustomerModel getOneClient(long id) throws CustomerNotFound {
		CustomerEntity customer = customerRepository.getCustomerEntityById(id);
		if(customer!=null){
			return CustomerModel.toModel(customer);
		}else {
			throw new CustomerNotFound("Customer with id " + id + " not found");
		}
	}

	public boolean login(LoginInputValue loginInputValue) throws IncorrectPassword, CustomerNotFound {
		CustomerEntity finderCustomer = customerRepository.getCustomerEntityByCardNumber(loginInputValue.getCard_number());
		if (finderCustomer != null) {

			boolean matches = passwordEncoder.matches(String.valueOf(loginInputValue.getCode()), finderCustomer.getCode());
			if (matches) {
				return true;
			} else {
				throw new IncorrectPassword("Incorrect password");
			}
		}
		throw new CustomerNotFound("Customer with card " + loginInputValue.getCard_number() + " not found");
	}

	public CustomerModel loginAndShowInfoAboutClient(LoginInputValue loginInputValue) throws CustomerNotFound, IncorrectPassword, LoginFailed {
		if (login(loginInputValue)) {
			String cardNumber = loginInputValue.getCard_number();
			CustomerEntity customer = customerRepository.getCustomerEntityByCardNumber(cardNumber);
			return CustomerModel.toModel(customer);

		} else {
			throw new LoginFailed("Login failed, please check your input value");
		}
	}

	public CustomerEntity registration(RegistrationInputValue registrationInputValue) throws CustomerAlreadyExist, PasswordNotHave4Digits {
		long newCardNumber=0;
		while (true) {
			Random rng = new Random();
			newCardNumber = rng.nextLong() % 1000000000000000L;
			if(newCardNumber<0) newCardNumber=-newCardNumber;
			CustomerEntity finderCustomer = customerRepository.getCustomerEntityByCardNumber(String.valueOf(newCardNumber));
			if(finderCustomer==null) break;
		}


			if (registrationInputValue.getCode() >= 1000 && registrationInputValue.getCode() <= 9999) {
				CustomerEntity customer = new CustomerEntity();
				AccountEntity account = new AccountEntity();
				AccountHistoryEntity accountHistory = new AccountHistoryEntity();
				List<AccountHistoryEntity> accountHistoryEntityList = new ArrayList<>();
				account.setBalance(0);
				account.setAccountHistory(accountHistoryEntityList);

				String encodePassword = passwordEncoder.encode(String.valueOf(registrationInputValue.getCode()));

				customer.setCardNumber(String.valueOf(newCardNumber));
				customer.setName(registrationInputValue.getName());
				customer.setCode(encodePassword);
				customer.setAccount(account);
				account.setCustomer(customer);
				accountHistory.setAccount(account);

				customerRepository.save(customer);
				return customer;
			} else {
				throw new PasswordNotHave4Digits("Password should have 4 digits");
			}
		}
	}

