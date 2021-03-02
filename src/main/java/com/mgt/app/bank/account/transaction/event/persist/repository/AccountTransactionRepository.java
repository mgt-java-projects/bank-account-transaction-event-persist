package com.mgt.app.bank.account.transaction.event.persist.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mgt.app.bank.account.transaction.event.persist.entity.AccountTransaction;

/**
 * MongoRepository to handle db operation for {@link AccountTransaction} entity.
 * 
 * @author stami
 *
 */
@Repository
public interface AccountTransactionRepository extends MongoRepository<AccountTransaction, String> {

	/**
	 * finds list of {link AccountTransaction} transaction for the given account
	 * number.
	 * 
	 * @param accountNumber
	 * @return List<AccountTransaction>
	 */
	public List<AccountTransaction> findByAccountNumber(@Param("accountNumber") String accountNumber);

	/**
	 * finds list of {link AccountTransaction} transaction for the given account
	 * number and type.
	 * 
	 * @param accountNumber
	 * @param type
	 * @return List<AccountTransaction>
	 */
	public List<AccountTransaction> findByAccountNumberAndType(@Param("accountNumber") String accountNumber,
			@Param("type") String type);

	/**
	 * finds list of {link AccountTransaction} transaction for the given account
	 * number, type, startDate and endDate.
	 * 
	 * @param accountNumber
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return List<AccountTransaction>
	 */
	public List<AccountTransaction> findByAccountNumberAndTypeAndTransactionTsBetween(
			@Param("accountNumber") String accountNumber, @Param("type") String type,
			@Param("transactionTs") Date startDate, @Param("transactionTs") Date endDate);

}
