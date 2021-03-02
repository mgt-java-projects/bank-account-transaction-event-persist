package com.mgt.app.bank.account.transaction.event.persist.repository.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mgt.app.bank.account.transaction.event.common.dto.AccountTransactionDTO;
import com.mgt.app.bank.account.transaction.event.common.enums.TransactionType;
import com.mgt.app.bank.account.transaction.event.common.exception.AccountTransactionDataAccessException;
import com.mgt.app.bank.account.transaction.event.persist.entity.AccountTransaction;
import com.mgt.app.bank.account.transaction.event.persist.repository.AccountTransactionRepository;

/**
 * Service class to interact with {@link AccountTransactionRepository} and
 * process database records.
 * 
 * @author stami
 *
 */
@Service
public class AccountTransactionRepositoryService {
	/**
	 * Logger instance.
	 */
	private static final Logger log = LoggerFactory.getLogger(AccountTransactionRepositoryService.class);

	/**
	 * Instance of {@link AccountTransactionRepository}
	 */
	@Autowired
	AccountTransactionRepository repository;

	/**
	 * Instance of {@link ModelMapper}
	 */
	@Autowired
	ModelMapper modelMapper;

	/**
	 * Add a new account transaction {@link AccountTransaction} to persist layer
	 * 
	 * @param AccountTransactionDTO
	 * @return AccountTransactionDTO
	 */
	public AccountTransactionDTO addNewAccountTransaction(AccountTransactionDTO dto) {
		log.debug("In addNewAccountTransaction...");
		AccountTransactionDTO savedAccountTransactionDTO = null;
		try {
			savedAccountTransactionDTO = convertEntityToDto(repository.save(convertDtoToEntity(dto)));
			log.debug("Out addNewAccountTransaction...");

		} catch (DataAccessException exp) {
			log.error("Exception occured while adding NewAccountTransaction : " + dto.getAccountNumber(), exp);
		}

		return savedAccountTransactionDTO;
	}


	/**
	 * Find the list of transactions {@link AccountTransaction} for the given
	 * account number.
	 * 
	 * @param accountNumber
	 * @return List<AccountTransaction>
	 */
	public List<AccountTransactionDTO> findAllTransactionsForAccount(String accountNumber) {
		log.debug("In findAllTransactionsForAccount...");
		List<AccountTransactionDTO> transactions = null;
		try {
			transactions = convertEntityToDto(repository.findByAccountNumber(accountNumber));

			log.debug("Out findAllTransactionsForAccount...");

		} catch (DataAccessException exp) {
			log.error("Exception occured while fetching findAllTransactionsForAccount : " + accountNumber, exp);
			throw new AccountTransactionDataAccessException("Exception occured while fetching findAllTransactionsForAccount "+accountNumber,exp);
		}

		return transactions;
	}

	/**
	 * Find the list of transactions {@link AccountTransaction} for the given
	 * account number and transaction type.
	 * 
	 * @param accountNumber
	 * @param type
	 * @return List<AccountTransaction>
	 */
	public List<AccountTransactionDTO> findAllTransactionsForAccountAndType(String accountNumber, String type) {
		log.debug("In findAllTransactionsForAccountAndType...");
		List<AccountTransactionDTO> transactions = null;
		try {
			transactions = convertEntityToDto(repository.findByAccountNumberAndType(accountNumber, type));

			log.debug("Out findAllTransactionsForAccountAndType...");

		} catch (DataAccessException exp) {
			log.error("Exception occured while fetching findAllTransactionsForAccountAndType : " + accountNumber, exp);
			throw new AccountTransactionDataAccessException("Exception occured while fetching findAllTransactionsForAccountAndType "+accountNumber,exp);
		}

		return transactions;
	}

	/**
	 * Find the list of transactions {@link AccountTransaction} for the given
	 * account number and date range.
	 * 
	 * @param accountNumber
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return List<AccountTransaction>
	 */
	public List<AccountTransactionDTO> findAllTransactionsForAccountWithTypeAndDateRange(String accountNumber,
			String type, Date startDate, Date endDate) {
		log.debug("In findAllTransactionsForAccountWithTypeAndDateRange...");
		List<AccountTransactionDTO> transactions = null;
		try {
			transactions = convertEntityToDto(repository
					.findByAccountNumberAndTypeAndTransactionTsBetween(accountNumber, type, startDate, endDate));

			log.debug("Out findAllTransactionsForAccountWithTypeAndDateRange...");

		} catch (DataAccessException exp) {
			log.error("Exception occured while fetching findAllTransactionsForAccountWithTypeAndDateRange : "
					+ accountNumber, exp);
			throw new AccountTransactionDataAccessException("Exception occured while fetching findAllTransactionsForAccountWithTypeAndDateRange "+accountNumber,exp);
		}

		return transactions;
	}

	/**
	 * Find the list of transactions {@link AccountTransaction} for the given
	 * account number and type or date range filter.
	 * 
	 * @param accountNumber
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return List<AccountTransaction>
	 */
	public List<AccountTransactionDTO> findAllTransactionsForAccountWithFilterParams(String accountNumber,
			Optional<String> type, Optional<Date> startDate, Optional<Date> endDate) {
		log.debug("In findAllTransactionsForAccountWithFilterParams...");
		List<AccountTransactionDTO> transactions = null;
		try {
			if (type.isPresent()) {
				if (startDate.isPresent() && endDate.isPresent()) {
					transactions = convertEntityToDto(repository.findByAccountNumberAndTypeAndTransactionTsBetween(
							accountNumber, type.get(), startDate.get(), endDate.get()));
				} else {
					transactions = convertEntityToDto(repository.findByAccountNumberAndType(accountNumber, type.get()));
				}
			} else {
				transactions = convertEntityToDto(repository.findByAccountNumber(accountNumber));
			}
			log.debug("Out findAllTransactionsForAccountWithFilterParams...");

		} catch (DataAccessException exp) {
			log.error(
					"Exception occured while fetching findAllTransactionsForAccountWithFilterParams : " + accountNumber,
					exp);
		}

		return transactions;
	}

	/**
	 * Converts data from {@link AccountTransaction} Dto to
	 * {@link AccountTransactionDTO} entity object
	 * 
	 * @param accountTransactionDTO
	 * @return
	 */
	private AccountTransaction convertDtoToEntity(final AccountTransactionDTO accountTransactionDTO) {
		log.debug("In convertDtoToEntity...");
		AccountTransaction accountTransaction = null;
		if (null != accountTransactionDTO) {
			accountTransaction = modelMapper.map(accountTransactionDTO, AccountTransaction.class);
		}
		log.debug("Out convertDtoToEntity...");
		return accountTransaction;

	}

	/**
	 * Converts data from {@link AccountTransaction} entity to
	 * {@link AccountTransactionDTO} dto object
	 * 
	 * @param accountTransaction
	 * @return AccountTransactionDTO
	 */
	private AccountTransactionDTO convertEntityToDto(final AccountTransaction accountTransaction) {
		log.debug("In convertEntityToDto...");
		AccountTransactionDTO accountTransactionDTO = null;
		if (null != accountTransaction) {
			accountTransactionDTO = modelMapper.map(accountTransaction, AccountTransactionDTO.class);
		}
		log.debug("Out convertEntityToDto...");
		return accountTransactionDTO;

	}

	/**
	 * Converts data from {@link AccountTransaction} entity list to
	 * {@link AccountTransactionDTO} dto list object
	 * 
	 * @param accountTransactions
	 * @return List<AccountTransactionDTO>
	 */
	private List<AccountTransactionDTO> convertEntityToDto(final List<AccountTransaction> accountTransactions) {
		log.debug("In convertEntityToDto...");
		List<AccountTransactionDTO> accountTransactionDTOs = null;
		if (null != accountTransactions) {
			accountTransactionDTOs = accountTransactions.stream().map(act -> convertEntityToDto(act))
					.collect(Collectors.toList());
		}
		log.debug("Out convertEntityToDto...");
		return accountTransactionDTOs;

	}

}
