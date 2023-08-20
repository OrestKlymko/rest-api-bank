package com.bank.api.account.repo;

import com.bank.api.account.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity,Long> {
	public Optional<AccountEntity> getAccountEntityByCustomerId(long customer_id);
}
