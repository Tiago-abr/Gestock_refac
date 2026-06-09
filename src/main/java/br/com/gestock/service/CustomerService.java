package br.com.gestock.service;

import br.com.gestock.model.Customer;
import br.com.gestock.repository.CustomerRepository;
import java.util.List;

public class CustomerService {
    
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }
    
    public Customer create(Customer customer){
        if(customer == null){
            throw new RuntimeException("Customer is required.");
        }
        this.repository.save(customer);
        
        return customer; 
    }
    
    public List<Customer> findAll(){
        List<Customer> customers = this.repository.getCustomers();
        
        return customers;
    }
    
    public Customer findById(Long id){
        if(id < 1){
            throw new RuntimeException("Id cannot be null.");
        }
        
        return this.repository.getCustomerByID(id);
    }
    
}
