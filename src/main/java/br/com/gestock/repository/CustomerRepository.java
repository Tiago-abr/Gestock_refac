package br.com.gestock.repository;

import br.com.gestock.model.Customer;
import br.com.gestock.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import java.util.List;

public class CustomerRepository {
    
    public void save(Customer customer){
        EntityManager manager = JPAUtil.getEntityManager();
        
        try{
            manager.getTransaction().begin();
            manager.persist(customer);
            manager.getTransaction().commit();
        }catch(Exception exception){
            manager.getTransaction().rollback();
            throw new RuntimeException("Error adding the customer. "+exception.getMessage());
        }finally{
            JPAUtil.closeEntityManager();
        }
    }
    
    public List<Customer> getCustomers(){
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "SELECT c FROM Customers c";
        try{
            Query query = manager.createQuery(jpql, Customer.class);
            return query.getResultList();
        }catch(Exception exception){
            System.out.println("Error retrieving customers "+exception.getMessage());
            return null;
        }finally{
            JPAUtil.closeEntityManager();
        }
    }
    
    public Customer getCustomerByID(Long id){
        EntityManager manager = JPAUtil.getEntityManager();
        try{
            Customer customer = manager.find(Customer.class, id);
            return customer;
        }catch(Exception exception){
            throw new RuntimeException("Error retrieving user "+exception.getMessage());
        }finally{
            JPAUtil.closeEntityManager();
        }
    }
    
    public void update(Customer customer){
        EntityManager manager = JPAUtil.getEntityManager();
        try{
            manager.getTransaction().begin();
            manager.merge(customer);
            manager.getTransaction().commit();
        }catch(Exception exception){
            manager.getTransaction().rollback();
            System.out.println("Error updating the customer. "+exception.getMessage());
        }finally{
            JPAUtil.closeEntityManager();
        }
    }
    
    public void delete(Long id) {
        EntityManager manager = JPAUtil.getEntityManager();
        Customer customer = manager.find(Customer.class, id);
        if (customer != null) {
            try {
                manager.getTransaction().begin();
                manager.remove(customer);
                manager.getTransaction().commit();
            } catch (Exception exception) {
                manager.getTransaction().rollback();
                System.out.println("Error deleting the customer. " + exception.getMessage());
            } finally {
                JPAUtil.closeEntityManager();
            }
        }else{
            throw new EntityNotFoundException("Customer with ID " + id + " not found.");
        }
    }
}
