package edu.sfu.lab6.services;

import edu.sfu.lab6.dao.CustomerDAO;
import edu.sfu.lab6.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAll();
    }

    public Customer getCustomerById(Integer id) {
        return customerDAO.getById(id);
    }

    public Customer createCustomer(Customer customer) {
        Integer id = customerDAO.save(customer);
        return customerDAO.getById(id);
    }

    public Customer updateCustomer(Integer id, Customer customerDetails) {
        Customer customer = customerDAO.getById(id);
        if (customer != null) {
            customer.setName(customerDetails.getName());
            customer.setPhone(customerDetails.getPhone());
            customer.setEmail(customerDetails.getEmail());
            customerDAO.update(customer);
        }
        return customer;
    }

    public void deleteCustomer(Integer id) {
        customerDAO.delete(id);
    }

    public Customer findByEmail(String email) {
        return customerDAO.findByEmail(email);
    }

    public Customer findByPhone(String phone) {
        return customerDAO.findByPhone(phone);
    }
}