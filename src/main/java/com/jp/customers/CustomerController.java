package com.jp.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private CutomerRepository cutomerRepository;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Customer createCustomer(@RequestBody Customer customer) {
		return cutomerRepository.save(customer);
	}

}
