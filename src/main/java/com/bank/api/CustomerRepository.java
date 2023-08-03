package com.bank.api;

import com.bank.api.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
	CustomerEntity getCustomerEntityByCardNumber(String number);
}
