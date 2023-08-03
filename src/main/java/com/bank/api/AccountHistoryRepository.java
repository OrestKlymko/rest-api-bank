package com.bank.api;

import com.bank.api.entity.AccountHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHistoryRepository extends JpaRepository<AccountHistoryEntity,Long> {
}
