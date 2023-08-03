package com.bank.api.service;


import com.bank.api.AccountHistoryRepository;
import com.bank.api.entity.AccountEntity;
import com.bank.api.entity.AccountHistoryEntity;
import com.bank.api.entity.TranasctionTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountHistoryListService {
	@Autowired
	private AccountHistoryRepository accountHistoryRepository;

	public List<AccountHistoryEntity> updateAccountHistoryList(AccountEntity account, int balance,TranasctionTypeEnum typeEnum){
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
		List<AccountHistoryEntity> accountHistoryEntityList = account.getAccountHistoryEntityList();
		accountHistoryEntityList.add(accountHistory);
		return accountHistoryEntityList;
	}
}
