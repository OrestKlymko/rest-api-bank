package com.bank.api.account.repo;

import com.bank.api.account.pojo.AccountEntity;
import com.bank.api.account.pojo.AccountHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<AccountEntity,Long> {
	public AccountEntity getAccountEntityByCustomerId(long customer_id);
}
