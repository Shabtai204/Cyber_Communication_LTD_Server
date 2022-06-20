package com.ltd.logic;

import java.util.List;

import com.ltd.boundaries.CustomerBoundary;

public interface CustomerService {
	public CustomerBoundary addCustomer(CustomerBoundary customer);

	public CustomerBoundary updateCustomer(CustomerBoundary update);

	public List<CustomerBoundary> getAllCustomers();
}
