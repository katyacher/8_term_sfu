package edu.sfu.lab6.controllers;

import edu.sfu.lab6.model.Customer;
import edu.sfu.lab6.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
        Customer customer = customerService.getCustomerById(id);
        return customer != null 
            ? ResponseEntity.ok(customer) 
            : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Integer id, 
            @RequestBody Customer customerDetails) {
        Customer updated = customerService.updateCustomer(id, customerDetails);
        return updated != null 
            ? ResponseEntity.ok(updated) 
            : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/by-email")
    public ResponseEntity<Customer> findByEmail(@RequestParam String email) {
        Customer customer = customerService.findByEmail(email);
        return customer != null 
            ? ResponseEntity.ok(customer) 
            : ResponseEntity.notFound().build();
    }

    @GetMapping("/search/by-phone")
    public ResponseEntity<Customer> findByPhone(@RequestParam String phone) {
        Customer customer = customerService.findByPhone(phone);
        return customer != null 
            ? ResponseEntity.ok(customer) 
            : ResponseEntity.notFound().build();
    }
}