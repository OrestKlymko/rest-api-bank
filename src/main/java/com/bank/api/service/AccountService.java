package com.bank.api.service;


import com.bank.api.AccountRepository;
import com.bank.api.CustomerRepository;
import com.bank.api.entity.AccountEntity;
import com.bank.api.entity.AccountHistoryEntity;
import com.bank.api.entity.CustomerEntity;
import com.bank.api.entity.TranasctionTypeEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AccountHistoryListService accountHistoryEntityList;

	@Transactional(rollbackOn = Exception.class)
	public void makeTransaction(long toCustomerId, long fromCustomerId, int balance) {
		income(toCustomerId, balance);
		outcome(fromCustomerId, balance);
	}

	public void income(long toCustomerId, int balance) {
		updateTransaction(toCustomerId, balance, TranasctionTypeEnum.Income);
	}

	public void outcome(long toCustomerId, int balance) {
		updateTransaction(toCustomerId, balance, TranasctionTypeEnum.Spend);
	}


	public void updateTransaction(long id, int balance, TranasctionTypeEnum type) {

		CustomerEntity customerId = customerRepository.findById(id).get();
		AccountEntity account = customerId.getAccount();



		List<AccountHistoryEntity> accountHistoryEntities = accountHistoryEntityList.updateAccountHistoryList(account, balance,type);
		account.setAccountHistoryEntityList(accountHistoryEntities);
		if (type.equals(TranasctionTypeEnum.Income)) {
			account.setBalance(account.getBalance() + balance);
		} else {
			account.setBalance(account.getBalance() - balance);
		}

		accountRepository.save(account);
	}

}
