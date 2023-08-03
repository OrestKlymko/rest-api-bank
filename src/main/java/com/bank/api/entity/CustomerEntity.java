package com.bank.api.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

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
	private int code;
	@OneToOne(mappedBy = "customer")
	@JsonManagedReference
	private AccountEntity account;


}

