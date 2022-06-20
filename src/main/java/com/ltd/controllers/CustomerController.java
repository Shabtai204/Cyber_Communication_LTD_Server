package com.ltd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ltd.boundaries.CustomerBoundary;
import com.ltd.logic.CustomerService;

@Controller
public class CustomerController {
	private CustomerService customerService;

	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("/add_customer_form")
	public String getCustomerForm(Model model) {
		model.addAttribute("customer", new CustomerBoundary());
		return "add_customer_form";
	}

	@GetMapping("/interface")
	public String users(CustomerBoundary customer, Model model) {
		model.addAttribute("customer", new CustomerBoundary());
		model.addAttribute("customers", getAllCustomers());
		return "interface";
	}

	@PostMapping("/addCustomer")
	public String addCustomer(@ModelAttribute("customer") CustomerBoundary customer, Model model) {
		customerService.addCustomer(customer);
		return users(customer, model);
	}

	@GetMapping("/getAllCustomers")
	public CustomerBoundary[] getAllCustomers() {
		return customerService.getAllCustomers().toArray(new CustomerBoundary[0]);
	}

	@RequestMapping(path = "/updateCustomer", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public CustomerBoundary updateCustomer(@RequestBody CustomerBoundary update) {
		return customerService.updateCustomer(update);
	}
}
