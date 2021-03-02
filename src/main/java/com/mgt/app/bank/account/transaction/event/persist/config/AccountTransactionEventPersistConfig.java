package com.mgt.app.bank.account.transaction.event.persist.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Configure the account transaction event persist beans.
 * 
 * @author stami
 *
 */
@Configuration
@ComponentScan(basePackages = "com.mgt.app.bank.account.transaction.event.persist")
@EnableMongoRepositories(basePackages = "com.mgt.app.bank.account.transaction.event.persist")
public class AccountTransactionEventPersistConfig {

	/**
	 * Define bean instance of {@link ModelMapper}.
	 * @return modelMapper
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
