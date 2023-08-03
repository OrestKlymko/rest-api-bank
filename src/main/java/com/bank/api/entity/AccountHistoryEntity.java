package com.bank.api.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "account_history")
public class AccountHistoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	@Column(name = "value")
	private int value;
	@Column(name = "transaction_history")
	private Date transaction_history;
	@Column(name = "transaction_type", columnDefinition = "ENUM('Income', 'Outcome')")
	@Enumerated(EnumType.STRING)
	private TranasctionTypeEnum transactionType;

	@ManyToOne
	@JoinColumn(name = "account_id")
	@ToString.Exclude
	@JsonBackReference
	private AccountEntity account;
}
