package com.bank.api.customer;


import com.bank.api.account.entity.AccountEntity;
import com.bank.api.customer.entity.CustomerEntity;
import com.bank.api.customer.exception.CustomerAlreadyExist;
import com.bank.api.customer.exception.CustomerNotFound;
import com.bank.api.customer.exception.IncorrectPassword;
import com.bank.api.customer.exception.PasswordNotHave4Digits;
import com.bank.api.customer.model.CustomerModel;
import com.bank.api.customer.pojo.LoginInputValue;
import com.bank.api.customer.pojo.RegistrationInputValue;
import com.bank.api.customer.pojo.UserInputTransactionValue;
import com.bank.api.customer.repo.CustomerRepository;
import com.bank.api.customer.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.Arrays;
;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerRepository customerRepository;

	@MockBean
	private CustomerService customerService;


	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	void success_login_test() throws Exception {
		LoginInputValue inputValue = new LoginInputValue("1111222233334444",1234);

		when(customerService.login(inputValue)).thenReturn(true);

		mockMvc.perform(post("/api/v1/user/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(inputValue)))
						.andExpect(status().isOk());
	}

	@Test
	void failed_login_test() throws Exception {
		LoginInputValue inputValue = new LoginInputValue("1111222233334444",1234);
		CustomerNotFound customerNotFound = new CustomerNotFound("Customer with card 1111222233334444 not found");

		when(customerService.login(inputValue)).thenThrow(customerNotFound);

		mockMvc.perform(post("/api/v1/user/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(inputValue)))
						.andExpect(status().is4xxClientError());
	}

	@Test
	void success_loginAndShowInfoAboutClient_test() throws Exception {
		LoginInputValue inputValue = new LoginInputValue("1111222233334444",1234);



		CustomerEntity customer = new CustomerEntity();
		customer.setId(0);
		customer.setName("John Doe");
		String encode = "$argon2id$v=19$m=15360,t=2,p=1$JpdX7F/9v9HEren8U5Nhoh1M2KtIkcWffJmB8p7TmA8$cPzxDIzC8+BWTk5H9P8N4lLCMkBR6S/yddYCZisUoCYdp6cYdlmVxzPNADwzUzEu7uoTjAtEG3XzEI+HyrWnvQ";
		customer.setCode(encode);
		customer.setCardNumber("1111222233334444");
		AccountEntity account = new AccountEntity();
		account.setId(0);
		account.setBalance(0.0);
		account.setAccountHistory(new ArrayList<>());
		customer.setAccount(account);

			when(customerService.loginAndShowInfoAboutClient(inputValue)).thenReturn(CustomerModel.toModel(customer));

		mockMvc.perform(post("/api/v1/user/login/client")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(inputValue)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.cardNumber",is("1111222233334444")));


	}

	@Test
	void success_registration_test() throws Exception {
		Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder(32, 64, 1, 15 * 1024, 2);
		RegistrationInputValue registrationInputValue = new RegistrationInputValue();
		registrationInputValue.setCode(1234);
		registrationInputValue.setName("John Doe");

		CustomerEntity customer = new CustomerEntity();
		customer.setId(0);
		customer.setName("John Doe");
		String encode = passwordEncoder.encode("1234");
		customer.setCode(encode);
		customer.setCardNumber("1111222233334444");
		AccountEntity account = new AccountEntity();
		account.setId(0);
		account.setBalance(0.0);
		account.setAccountHistory(new ArrayList<>());
		customer.setAccount(account);

		when(customerService.registration(registrationInputValue)).thenReturn(customer);

		mockMvc.perform(post("/api/v1/user/registration")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(registrationInputValue)))
				.andExpect(jsonPath("$.id",is(0)))
				.andExpect(jsonPath("$.code",not(equalTo("1234"))))
				.andExpect(jsonPath("$.name",is("John Doe")))
				.andExpect(jsonPath("$.account.balance",is(0.0)))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void success_findClient_test() throws Exception {
		CustomerEntity customer = new CustomerEntity();
		customer.setId(0);
		customer.setCardNumber("1111222233334444");
		customer.setCode("1234");
		customer.setName("John Doe");

		when(customerService.getOneClient(0)).thenReturn(CustomerModel.toModel(customer));

		mockMvc.perform(get("/api/v1/user/client/0"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(0)))
				.andExpect(jsonPath("$.cardNumber", is("1111222233334444")));
	}

	@Test
	void success_returnAll_test() throws Exception {
		CustomerEntity customer1 = new CustomerEntity();
		customer1.setId(0);
		customer1.setCardNumber("1111222233334444");
		customer1.setCode("1234");
		customer1.setName("John Doe");
		CustomerEntity customer2 = new CustomerEntity();
		customer1.setId(1);
		customer2.setCardNumber("5555666677778888");
		customer2.setCode("5678");
		customer2.setName("Alice Smith");

		when(customerRepository.findAll()).thenReturn(Arrays.asList(
				customer1, customer2
		));

		mockMvc.perform(get("/api/v1/user/list"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[*].id", containsInAnyOrder(0, 1)))
				.andExpect(jsonPath("$[*].cardNumber", containsInAnyOrder("1111222233334444", "5555666677778888")));
	}
}