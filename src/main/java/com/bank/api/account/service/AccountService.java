package com.bank.api.account.service;


import com.bank.api.account.repo.AccountRepository;
import com.bank.api.customer.exception.CustomerNotHaveEnoughMoney;
import com.bank.api.customer.pojo.UserInputTransactionValue;
import com.bank.api.customer.repo.CustomerRepository;
import com.bank.api.account.entity.AccountEntity;
import com.bank.api.account.entity.AccountHistoryEntity;
import com.bank.api.customer.entity.CustomerEntity;
import com.bank.api.account.entity.TranasctionTypeEnum;
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
	public boolean makeTransaction(UserInputTransactionValue userInputTransactionValue) throws CustomerNotHaveEnoughMoney {
		String toCard = userInputTransactionValue.getToCard();
		String fromCard = userInputTransactionValue.getFromCard();
		int balance = userInputTransactionValue.getBalance();
		CustomerEntity toCustomerCard = customerRepository.getCustomerEntityByCardNumber(toCard);
		CustomerEntity fromCustomerCard = customerRepository.getCustomerEntityByCardNumber(fromCard);
		if (toCustomerCard != null && fromCustomerCard != null) {
			income(toCustomerCard.getId(), balance);
			outcome(fromCustomerCard.getId(), balance);
			return true;
		}
		return false;
	}

	public void income(long toCustomerId, int balance) throws CustomerNotHaveEnoughMoney {
		updateTransaction(toCustomerId, balance, TranasctionTypeEnum.Income);
	}

	public void outcome(long toCustomerId, int balance) throws CustomerNotHaveEnoughMoney {
		updateTransaction(toCustomerId, balance, TranasctionTypeEnum.Spend);
	}


	public void updateTransaction(long id, int balance, TranasctionTypeEnum type) throws CustomerNotHaveEnoughMoney {

		CustomerEntity customerId = customerRepository.findById(id).get();
		AccountEntity account = customerId.getAccount();

		List<AccountHistoryEntity> accountHistoryEntities = accountHistoryEntityList.updateAccountHistoryList(account, balance, type);
		account.setAccountHistory(accountHistoryEntities);
		if (type.equals(TranasctionTypeEnum.Income)) {
			account.setBalance(account.getBalance() + balance);
		} else {
			if ((account.getBalance() - balance) >= 0) {
				account.setBalance(account.getBalance() - balance);
			} else {
				throw new CustomerNotHaveEnoughMoney("Customer " + customerId.getCardNumber() + " don't have enough money");
			}

		}

		accountRepository.save(account);
	}

}
