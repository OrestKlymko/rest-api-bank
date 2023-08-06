package com.bank.api.account;


import com.bank.api.account.entity.AccountEntity;
import com.bank.api.account.entity.AccountHistoryEntity;
import com.bank.api.account.repo.AccountHistoryRepository;
import com.bank.api.account.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account/{id}")
public class AccountController {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private AccountHistoryRepository accountHistoryRepository;


	@GetMapping("/info")
	public AccountEntity getUserAccount(@PathVariable long id){
		return accountRepository.getAccountEntityByCustomerId(id);
	}

	@GetMapping("/history")
	public List<AccountHistoryEntity> getAccountHistory(@PathVariable long id){
		return accountHistoryRepository.getAccountHistoryEntitiesByCustomerId(id);
	}

}
