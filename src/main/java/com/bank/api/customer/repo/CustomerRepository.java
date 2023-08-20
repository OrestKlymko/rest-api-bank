package com.bank.api.customer.repo;

import com.bank.api.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
	Optional<CustomerEntity> getCustomerEntityByCardNumber(String number);
	Optional<CustomerEntity> getCustomerEntityById(long id);


}
