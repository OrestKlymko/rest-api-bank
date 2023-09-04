package com.bank.api.account;

import com.bank.api.account.service.AccountService;
import com.bank.api.customer.exception.CustomerNotFound;
import com.bank.api.customer.exception.CustomerNotHaveEnoughMoney;
import com.bank.api.customer.pojo.UserInputTransactionValue;
import com.bank.api.customer.repo.CustomerRepository;
import com.bank.api.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

	@InjectMocks
	private AccountController accountController;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private CustomerService customerService;

	@Mock
	private AccountService accountService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSuccessfulTransaction() throws CustomerNotFound, CustomerNotHaveEnoughMoney {
		String fromCard = "1111222233334444";
		String toCard = "1111222233332222";
		BigDecimal balance = BigDecimal.valueOf(10.0);
		UserInputTransactionValue inputTransactionValue = new UserInputTransactionValue(fromCard, toCard, balance);

		when(accountService.makeTransaction(inputTransactionValue)).thenReturn(true);

		ResponseEntity response = accountController.transaction(inputTransactionValue);

		verify(accountService, times(1)).makeTransaction(inputTransactionValue);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue((boolean) response.getBody());
	}

	@Test
	void testTransactionWithCustomerNotFound() throws CustomerNotFound, CustomerNotHaveEnoughMoney {
		String fromCard = "1111222233334444";
		String toCard = "1111222233332222";
		BigDecimal balance = BigDecimal.valueOf(10.0);
		UserInputTransactionValue inputTransactionValue = new UserInputTransactionValue(fromCard, toCard, balance);

		doThrow(CustomerNotFound.class).when(accountService).makeTransaction(inputTransactionValue);

		ResponseEntity response = accountController.transaction(inputTransactionValue);

		verify(accountService, times(1)).makeTransaction(inputTransactionValue);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void testTransactionWithCustomerNotHaveEnoughMoney() throws CustomerNotFound, CustomerNotHaveEnoughMoney {
		String fromCard = "1111222233334444";
		String toCard = "1111222233332222";
		BigDecimal balance = BigDecimal.valueOf(10.0);
		UserInputTransactionValue inputTransactionValue = new UserInputTransactionValue(fromCard, toCard, balance);

		doThrow(CustomerNotHaveEnoughMoney.class).when(accountService).makeTransaction(inputTransactionValue);

		ResponseEntity response = accountController.transaction(inputTransactionValue);

		verify(accountService, times(1)).makeTransaction(inputTransactionValue);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}


}
