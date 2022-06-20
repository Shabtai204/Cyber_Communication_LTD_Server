package com.ltd.logic.jpa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ltd.boundaries.CustomerBoundary;
import com.ltd.data.CustomerEntity;
import com.ltd.data.dao.CustomerDao;
import com.ltd.logic.CustomerService;
import com.ltd.logic.converters.CustomerEntityConverter;
import com.ltd.utilities.NotFoundException;
import com.ltd.utilities.Validations;

@Service
public class CustomerServiceJpa implements CustomerService {

	private CustomerDao customerDao;
	private Validations utils;
	private CustomerEntityConverter customerEntityConverter;

	public CustomerServiceJpa() {
	}

	@Autowired
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	@Autowired
	public void setUtils(Validations utils) {
		this.utils = utils;
	}

	@Autowired
	public void setCustomerEntityConverter(CustomerEntityConverter customerEntityConverter) {
		this.customerEntityConverter = customerEntityConverter;
	}

	@Override
	@Transactional
	public CustomerBoundary addCustomer(CustomerBoundary customer) {
		utils.assertNull(customer);
		utils.assertNull(customer.getId());
		utils.assertNull(customer.getFirstName());
		utils.assertNull(customer.getLastName());
		utils.assertNull(customer.getCity());
		utils.assertNull(customer.getCountry());
		utils.assertNull(customer.getCreditCard());
		utils.assertNull(customer.getInternetBundle());
		utils.assertNull(customer.getPostalCode());
		utils.assertNull(customer.getSector());
		utils.assertNull(customer.getStreet());
		utils.assertNull(customer.getStreetNumber());

		CustomerEntity customerEntity = this.customerEntityConverter.fromBoundary(customer);
		customerEntity.setCity(customer.getCity());
		customerEntity.setCountry(customer.getCountry());
		customerEntity.setCreditCard(customer.getCreditCard());
		customerEntity.setFirstName(customer.getFirstName());
		customerEntity.setLastName(customer.getLastName());
		customerEntity.setId(customer.getId());
		customerEntity.setInternetBundle(customer.getInternetBundle());
		customerEntity.setPostalCode(customer.getPostalCode());
		customerEntity.setSector(customer.getSector());
		customerEntity.setStreet(customer.getStreet());
		customerEntity.setStreetNumber(customer.getStreetNumber());

		customerDao.save(customerEntity);
		return customerEntityConverter.toBoundary(customerEntity);
	}

	@Override
	@Transactional
	public CustomerBoundary updateCustomer(CustomerBoundary update) {
		utils.assertNull(update);
		utils.assertValidEmail(update.getId());
		utils.assertNull(update.getFirstName());
		utils.assertNull(update.getLastName());
		utils.assertNull(update.getCity());
		utils.assertNull(update.getCountry());
		utils.assertNull(update.getCreditCard());
		utils.assertNull(update.getInternetBundle());
		utils.assertNull(update.getPostalCode());
		utils.assertNull(update.getSector());
		utils.assertNull(update.getStreet());
		utils.assertNull(update.getStreetNumber());

		Optional<CustomerEntity> existingCustomer = customerDao.findById(update.getId());
		if (existingCustomer.isPresent()) {
			CustomerEntity customerEntity = existingCustomer.get();
			if (update.getCity() != null)
				customerEntity.setCity(update.getCity());
			if (update.getCountry() != null)
				customerEntity.setCountry(update.getCountry());
			if (update.getCreditCard() != null)
				customerEntity.setCreditCard(update.getCreditCard());
			if (update.getFirstName() != null)
				customerEntity.setFirstName(update.getFirstName());
			if (update.getLastName() != null)
				customerEntity.setLastName(update.getLastName());
			if (update.getInternetBundle() != null)
				customerEntity.setInternetBundle(update.getInternetBundle());
			if (update.getPostalCode() != null)
				customerEntity.setPostalCode(update.getPostalCode());
			if (update.getSector() != null)
				customerEntity.setSector(update.getSector());
			if (update.getStreet() != null)
				customerEntity.setStreet(update.getStreet());
			if (update.getStreetNumber() != null)
				customerEntity.setStreetNumber(update.getStreetNumber());

			customerEntity = customerDao.save(customerEntity);
			return customerEntityConverter.toBoundary(customerEntity);
		} else {
			throw new NotFoundException("Could not find customer by id " + update.getId());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<CustomerBoundary> getAllCustomers() {
		return StreamSupport.stream(customerDao.findAll().spliterator(), false)
				.map(this.customerEntityConverter::toBoundary).collect(Collectors.toList());

	}
}
