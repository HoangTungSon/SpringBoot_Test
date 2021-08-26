package com.example.demo5.testData;

import com.example.demo5.model.Customer;
import com.example.demo5.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    public DatabaseLoader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.customerRepository.save(new Customer("firstName", "lastName"));
    }
}
