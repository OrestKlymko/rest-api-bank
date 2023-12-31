package com.bank.api.account.service;


import com.bank.api.account.repo.AccountHistoryRepository;
import com.bank.api.account.entity.AccountEntity;
import com.bank.api.account.entity.AccountHistoryEntity;
import com.bank.api.account.entity.TranasctionTypeEnum;
import com.bank.api.customer.exception.CustomerNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountHistoryListService {
	@Autowired
	private AccountHistoryRepository accountHistoryRepository;

	public List<AccountHistoryEntity> updateAccountHistoryList(AccountEntity account, BigDecimal balance, TranasctionTypeEnum typeEnum){
		Date date = new Date();
		AccountHistoryEntity accountHistory = new AccountHistoryEntity();
		accountHistory.setValue(balance);
		if (typeEnum == TranasctionTypeEnum.Income) {
			accountHistory.setTransactionType(TranasctionTypeEnum.Income);
		} else {
			accountHistory.setTransactionType(TranasctionTypeEnum.Spend);
		}
		accountHistory.setTransaction_history(new java.sql.Date(date.getTime()));
		accountHistory.setAccount(account);
		accountHistoryRepository.save(accountHistory);
		List<AccountHistoryEntity> accountHistoryEntityList = account.getAccountHistory();
		accountHistoryEntityList.add(accountHistory);
		return accountHistoryEntityList;
	}


	public List<AccountHistoryEntity> getAccountHistory(long id) throws CustomerNotFound {

		Optional<List<AccountHistoryEntity>> accountHistoryEntitiesByCustomerId = accountHistoryRepository.getAccountHistoryEntitiesByCustomerId(id);
		if (accountHistoryEntitiesByCustomerId.isPresent()) {
			return accountHistoryEntitiesByCustomerId.get();
		} else throw new CustomerNotFound("Customer with id:" + id + " not found");
	}
}
