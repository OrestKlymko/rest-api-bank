package com.bank.api.account.service;


import com.bank.api.account.repo.AccountRepository;
import com.bank.api.customer.exception.CustomerNotFound;
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


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
	public boolean makeTransaction(UserInputTransactionValue userInputTransactionValue) throws CustomerNotHaveEnoughMoney, CustomerNotFound {
		String toCard = userInputTransactionValue.getToCard();
		String fromCard = userInputTransactionValue.getFromCard();
		BigDecimal balance = userInputTransactionValue.getBalance();
		Optional<CustomerEntity> toCustomerCard = customerRepository.getCustomerEntityByCardNumber(toCard);
		Optional<CustomerEntity> fromCustomerCard = customerRepository.getCustomerEntityByCardNumber(fromCard);
		if (toCustomerCard.isPresent() && fromCustomerCard.isPresent()) {
			CustomerEntity toCustomerEntity = toCustomerCard.get();
			CustomerEntity fromCustomerEntity = fromCustomerCard.get();
			income(toCustomerEntity.getId(), balance);
			outcome(fromCustomerEntity.getId(), balance);
			return true;
		} else if (toCustomerCard.isEmpty()) {
			throw new CustomerNotFound("Customer with card " + toCard + " not found");
		} else {
			throw new CustomerNotFound("Customer with card " + fromCard + " not found");
		}
	}

	public void income(long toCustomerId, BigDecimal balance) throws CustomerNotHaveEnoughMoney, CustomerNotFound {
		updateTransaction(toCustomerId, balance, TranasctionTypeEnum.Income);
	}

	public void outcome(long toCustomerId, BigDecimal balance) throws CustomerNotHaveEnoughMoney, CustomerNotFound {
		updateTransaction(toCustomerId, balance, TranasctionTypeEnum.Spend);
	}

	public void updateTransaction(long id, BigDecimal balance, TranasctionTypeEnum type) throws CustomerNotHaveEnoughMoney, CustomerNotFound {
		CustomerEntity customerId = customerRepository.findById(id)
				.orElseThrow(() -> new CustomerNotFound("Customer with ID " + id + " not found"));

		AccountEntity account = customerId.getAccount();
		List<AccountHistoryEntity> accountHistoryEntities = accountHistoryEntityList.updateAccountHistoryList(account, balance, type);
		account.setAccountHistory(accountHistoryEntities);

		if (type.equals(TranasctionTypeEnum.Income)) {
			account.setBalance(account.getBalance().add(balance) );
		} else {
			if ((account.getBalance().subtract(balance)).doubleValue() >= 0) {
				account.setBalance(account.getBalance().subtract(balance));
			} else {
				throw new CustomerNotHaveEnoughMoney("Customer " + customerId.getCardNumber() + " doesn't have enough money");
			}
		}
		accountRepository.save(account);
	}


	public AccountEntity getUserAccount(long id) throws CustomerNotFound {
		Optional<AccountEntity> accountEntityByCustomerId = accountRepository.getAccountEntityByCustomerId(id);
		if (accountEntityByCustomerId.isPresent()) {
			return accountEntityByCustomerId.get();
		} else throw new CustomerNotFound("Customer with id:" + id + " not found");
	}


}
