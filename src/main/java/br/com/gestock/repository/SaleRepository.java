package br.com.gestock.repository;

import br.com.gestock.model.Sale;
import br.com.gestock.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import java.util.List;

public class SaleRepository {
    
    public void save(Sale sale){
        EntityManager manager = JPAUtil.getEntityManager();
        try{
            manager.getTransaction().begin();
            manager.persist(sale);
            manager.getTransaction().commit();
        }catch(Exception exception){
            manager.getTransaction().rollback();
            System.out.println("Error adding a sale. "+exception.getMessage());
        }finally{
            JPAUtil.closeEntityManager();
        }
    }
    
    public List<Sale> getSales(){
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "SELECT s FROM Sale s";
        try{
            Query query = manager.createQuery(jpql, Sale.class);
            return query.getResultList();
        }catch(Exception exception){
            System.out.println("Error retrieving sales data.");
            return null;
        }finally{
            JPAUtil.closeEntityManager();
        }
    }
    
    public void update(Sale sale){
        EntityManager manager = JPAUtil.getEntityManager();
        try{
            manager.getTransaction().begin();
            manager.merge(sale);
            manager.getTransaction().commit();
        }catch(Exception exception){
            manager.getTransaction().rollback();
            System.out.println("Error updating the sale. "+exception.getMessage());
        }finally{
            JPAUtil.closeEntityManager();
        }
    }
    
    public void delete(Long id) {
        EntityManager manager = JPAUtil.getEntityManager();
        Sale sale = manager.find(Sale.class, id);
        if (sale != null) {
            try {
                manager.getTransaction().begin();
                manager.remove(sale);
                manager.getTransaction().commit();
            } catch (Exception exception) {
                manager.getTransaction().rollback();
                System.out.println("Error deleting the sale. " + exception.getMessage());
            } finally {
                JPAUtil.closeEntityManager();
            }
        }else{
            throw new EntityNotFoundException("Sale with ID " + id + " not found.");
        }
    }
}
