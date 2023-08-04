package com.bank.api.customer.repo;

import com.bank.api.customer.pojo.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
	CustomerEntity getCustomerEntityByCardNumber(String number);
	CustomerEntity getCustomerEntityById(long id);


}
