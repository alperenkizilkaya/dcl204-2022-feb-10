package com.example.banking.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class AccountTest {

	@ParameterizedTest
	@CsvFileSource(resources = "accounts.csv")
	@DisplayName("Creating an account successfuly")
	void creatingAnAccountShouldSuccess(String iban, double balance) {
		// test fixture/setup
		// call exercise method: constructor
		var acc = new Account(iban, balance);
		// verification
		assertEquals(iban, acc.getIban());
		assertEquals(balance, acc.getBalance());
		// test tear-down
	}

	@ParameterizedTest
	@CsvFileSource(resources = "deposit-negative.csv")
	@DisplayName("Deposit with negative amount should fail")
	void depositWithNegativeAmountShouldFail(String iban, double balance, double amount) {
		// test fixture/setup
		var acc = new Account(iban, balance);
		// call exercise method -> mut
		assertThrows(IllegalArgumentException.class,
			() ->acc.deposit(amount));
		// verification
		assertEquals(balance, acc.getBalance());
		// test tear-down
	}

	@ParameterizedTest
	@CsvFileSource(resources = "deposit-success.csv")
	@DisplayName("Deposit with positive amount should success")
	void depositWithPoisitiveAmountShouldSuccess(String iban, double balance, double amount, double newBalance) {
		// test fixture/setup
		var acc = new Account(iban, balance);
		// call exercise method + verification
		assertEquals(newBalance, acc.deposit(amount));
		// test tear-down
	}

	@ParameterizedTest
	@CsvFileSource(resources = "withdraw-negative.csv")
	@DisplayName("Withdraw with negative amount should fail")
	void withWithNegativeAmountShouldFail(String iban, double balance, double amount) {
		// test fixture/setup
		var acc = new Account(iban, balance);
		// call exercise method -> mut
		assertThrows(IllegalArgumentException.class,
				() -> acc.withdraw(amount) );
		assertEquals(balance, acc.getBalance());
		// test tear-down
	}

	@ParameterizedTest
	@CsvFileSource(resources = "withdraw-success.csv")
	@DisplayName("Withdraw Under Balance should success")
	void withdrawUnderBalanceShouldSuccess(String iban, double balance, double amount, double newBalance) 
			 throws Exception {
		// test fixture/setup
		var acc = new Account(iban, balance);
		// call exercise method
		assertEquals(newBalance, acc.withdraw(amount));
		// test tear-down
	}

	@ParameterizedTest
	@CsvFileSource(resources = "withdraw-over-balance.csv")
	@DisplayName("Withdraw Under Balance should success")
	void withdrawOverBalanceShouldFail(String iban, double balance, double amount) {
		// test fixture/setup
		var acc = new Account(iban, balance);
		// call exercise method
		assertThrows(InsufficientBalanceException.class,
			() ->	acc.withdraw(amount) );
		// verification
		assertEquals(balance, acc.getBalance());
		// test tear-down
	}

	@Test
	void testDoubleComparison() throws Exception {
		double money = 4.35;
		money = 100.0 * money; // FPU
		assertEquals(435.0, money, 0.000001);
	}
}
