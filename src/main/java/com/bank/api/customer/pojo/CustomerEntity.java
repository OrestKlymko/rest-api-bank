package com.bank.api.customer.pojo;
import com.bank.api.account.pojo.AccountEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Entity
@Data
@Table(name = "customers")
public class CustomerEntity implements Serializable {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "card_number")
	private String cardNumber;
	@Column(name = "code")
	private String code;
	@Column(name = "name")
	private String name;
	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
	@JsonManagedReference
	@ToString.Exclude
	private AccountEntity account;


}

