package com.mgt.app.bank.account.transaction.event.persist.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;

import com.mgt.app.bank.account.transaction.event.common.enums.TransactionType;

import lombok.Data;

/**
 * Entity class for AccountTransaction collection.
 * 
 * @author stami
 *
 */
@Data
public class AccountTransaction {
	/**
	 * Object id for storing into db collection.
	 */
	@Id
	private String id;

	/**
	 * Customer account number.
	 */
	private String accountNumber;

	/**
	 * Money transaction time stamp in ISO.
	 */
	private Date transactionTs;

	/**
	 * Transaction type 'DEPOSIT/WITHDRAW'.
	 */
	private TransactionType type;

	/**
	 * Amount involved on this particular transaction.
	 */
	private BigDecimal amount;

	/**
	 * No-arg constructor.
	 */
	public AccountTransaction() {
	}

	/**
	 * Constructor with all params.
	 * 
	 * @param accountNumber
	 * @param transactionTs
	 * @param type
	 * @param amount
	 */
	public AccountTransaction(String accountNumber, Date transactionTs, TransactionType type, BigDecimal amount) {
		this.accountNumber = accountNumber;
		this.transactionTs = transactionTs;
		this.type = type;
		this.amount = amount;
	}

}
