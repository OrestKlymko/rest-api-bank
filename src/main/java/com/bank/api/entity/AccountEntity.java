package com.bank.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "account")
public class AccountEntity implements Serializable {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "balance")
	private int balance;

	@OneToOne
	@JoinColumn(name = "customer_id")
	@JsonBackReference
	private CustomerEntity customer;

	@OneToMany(mappedBy = "account",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ToString.Exclude
	@JsonManagedReference
	private List<AccountHistoryEntity> accountHistoryEntityList = new ArrayList<>();
}





