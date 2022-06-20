package com.ltd.logic.converters;

import org.springframework.stereotype.Component;

import com.ltd.boundaries.CustomerBoundary;
import com.ltd.data.CustomerEntity;

@Component
public class CustomerEntityConverter {

	public CustomerBoundary toBoundary(CustomerEntity entity) {
		CustomerBoundary customerBoundary = new CustomerBoundary();
		customerBoundary.setCity(entity.getCity());
		customerBoundary.setCountry(entity.getCountry());
		customerBoundary.setCreditCard(entity.getCreditCard());
		customerBoundary.setFirstName(entity.getFirstName());
		customerBoundary.setId(entity.getId());
		customerBoundary.setInternetBundle(entity.getInternetBundle());
		customerBoundary.setLastName(entity.getLastName());
		customerBoundary.setPostalCode(entity.getPostalCode());
		customerBoundary.setSector(entity.getSector());
		customerBoundary.setStreet(entity.getStreet());
		customerBoundary.setStreetNumber(entity.getStreetNumber());
		return customerBoundary;
	}

	public CustomerEntity fromBoundary(CustomerBoundary boundary) {
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setCity(boundary.getCity());
		customerEntity.setCountry(boundary.getCountry());
		customerEntity.setCreditCard(boundary.getCreditCard());
		customerEntity.setFirstName(boundary.getFirstName());
		customerEntity.setId(boundary.getId());
		customerEntity.setInternetBundle(boundary.getInternetBundle());
		customerEntity.setLastName(boundary.getLastName());
		customerEntity.setPostalCode(boundary.getPostalCode());
		customerEntity.setSector(boundary.getSector());
		return customerEntity;
	}
}
