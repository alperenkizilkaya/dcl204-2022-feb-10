package com.example.banking.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Bank {
	private final String name;
	private final List<Customer> customers 
	     = new ArrayList<>();
	
	public Bank(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Customer> getCustomers() {
		return List.copyOf(customers);
	}
	
	public Optional<Customer> findCustomerByIdentity(String identity) {
		for (var customer : customers)
			if (customer.getIdentityNo()
					    .equals(identity))
				return Optional.of(customer);
		return Optional.empty();
	}
	
	public Customer addCustomer(String identity,String fullname) {
		var customer = new Customer(identity, fullname);
		customers.add(customer);
		return customer;
	}
	
	public void hesapIsletimUcretiAl(double islemUcreti) {
		for (Customer customer : customers) {
			for (Account account : customer.getAccounts()) {
				account.withdraw(islemUcreti);	
			}
		}
	}

	public double getTotalBalance() {
		return customers.stream()
				        .mapToDouble(Customer::getBalance)
		                .sum();
	}
	
	public long getAccountNumber(Class<? extends Account> clazz) {
		return customers.stream()
				.map(Customer::getAccounts)
				.flatMap(Collection::stream)
				.filter(clazz::isInstance)
				.count();
	}
	
	public long getTotalNumberOfAccounts() {
		return customers.stream()
				.map(Customer::getAccounts)
				.mapToLong(Collection::size)
				.sum();
	}
	
}
