package com.bank.api.customer.service;
import com.bank.api.customer.exception.CustomerNotFound;
import com.bank.api.customer.exception.IncorrectPassword;
import com.bank.api.customer.exception.LoginFailed;
import com.bank.api.customer.exception.PasswordNotHave4Digits;
import com.bank.api.customer.model.CustomerModel;
import com.bank.api.customer.pojo.LoginInputValue;
import com.bank.api.customer.pojo.RegistrationInputValue;
import com.bank.api.customer.entity.CustomerEntity;
import com.bank.api.customer.repo.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private CustomerService customerService;

	private Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder(32, 64, 1, 15 * 1024, 2);

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetOneClient_CustomerFound() throws CustomerNotFound {
		// Arrange
		long customerId = 1L;
		CustomerEntity mockCustomer = new CustomerEntity();
		mockCustomer.setId(customerId);
		when(customerRepository.getCustomerEntityById(customerId)).thenReturn(mockCustomer);

		// Act
		CustomerModel result = customerService.getOneClient(customerId);

		// Assert
		assertNotNull(result);
		assertEquals(customerId, result.getId());
	}

	@Test
	public void testGetOneClient_CustomerNotFound() {
		// Arrange
		long customerId = 1L;
		when(customerRepository.getCustomerEntityById(customerId)).thenReturn(null);

		// Act & Assert
		assertThrows(CustomerNotFound.class, () -> customerService.getOneClient(customerId));
	}

	@Test
	public void testLogin_ValidCredentials() throws CustomerNotFound, IncorrectPassword {
		// Arrange
		String cardNumber = "1234567890123456";
		int password = 1234;
		CustomerEntity mockCustomer = new CustomerEntity();
		mockCustomer.setCardNumber(cardNumber);
		mockCustomer.setCode(passwordEncoder.encode(String.valueOf(password)));
		when(customerRepository.getCustomerEntityByCardNumber(cardNumber)).thenReturn(mockCustomer);

		// Act
		boolean result = customerService.login(new LoginInputValue(cardNumber, password));

		// Assert
		assertTrue(result);
	}

	@Test
	public void testLogin_CustomerNotFound() {
		String cardNumber = "1234567890123456";
		LoginInputValue inputValue = new LoginInputValue();
		inputValue.setCode(1234);
		inputValue.setCard_number(cardNumber);
		// Arrange
		when(customerRepository.getCustomerEntityByCardNumber(cardNumber)).thenReturn(null);

		// Act & Assert
		assertThrows(CustomerNotFound.class, () -> customerService.login(new LoginInputValue(cardNumber, 1234)));
	}

	@Test
	public void testLogin_IncorrectPassword() {
		// Arrange
		String cardNumber = "1234567890123456";
		int correctPassword = 1234;
		int incorrectPassword = 5678;
		CustomerEntity mockCustomer = new CustomerEntity();
		mockCustomer.setCardNumber(cardNumber);
		mockCustomer.setCode(passwordEncoder.encode(String.valueOf(correctPassword)));
		when(customerRepository.getCustomerEntityByCardNumber(cardNumber)).thenReturn(mockCustomer);

		// Act & Assert
		assertThrows(IncorrectPassword.class, () -> customerService.login(new LoginInputValue(cardNumber, incorrectPassword)));
	}

	@Test
	public void testLoginAndShowInfoAboutClient_ValidCredentials() throws CustomerNotFound, IncorrectPassword, LoginFailed {
		// Arrange
		String cardNumber = "1234567890123456";
		int password = 1234;
		CustomerEntity mockCustomer = new CustomerEntity();
		mockCustomer.setCardNumber(cardNumber);
		mockCustomer.setCode(passwordEncoder.encode(String.valueOf(password)));
		when(customerRepository.getCustomerEntityByCardNumber(cardNumber)).thenReturn(mockCustomer);

		// Act
		CustomerModel result = customerService.loginAndShowInfoAboutClient(new LoginInputValue(cardNumber, password));

		// Assert
		assertNotNull(result);
		assertEquals(cardNumber, result.getCardNumer());
	}



	@Test
	public void testRegistration_PasswordNotHave4Digits() {
		// Arrange
		RegistrationInputValue registrationInputValue = new RegistrationInputValue();
		registrationInputValue.setName("John Doe");
		registrationInputValue.setCode(123);

		// Act & Assert
		assertThrows(PasswordNotHave4Digits.class, () -> customerService.registration(registrationInputValue));
	}

}
