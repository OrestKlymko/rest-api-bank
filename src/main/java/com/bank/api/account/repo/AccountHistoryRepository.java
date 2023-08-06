package com.bank.api.account.repo;

import com.bank.api.account.entity.AccountHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountHistoryRepository extends JpaRepository<AccountHistoryEntity,Long> {

	@Query("SELECT ahe " +
			"FROM AccountHistoryEntity ahe " +
			"JOIN ahe.account acc " +
			"JOIN acc.customer cust " +
			"WHERE cust.id = :id")
	public List<AccountHistoryEntity> getAccountHistoryEntitiesByCustomerId(@Param("id") long id);
}
