package br.com.gestock.repository;

import br.com.gestock.model.Product;
import br.com.gestock.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import java.util.List;

public class ProductRepository {

    public void save(Product product) {
        EntityManager manager = JPAUtil.getEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(product);
            manager.getTransaction().commit();
        } catch (Exception exception) {
            manager.getTransaction().rollback();
            System.out.println("Erro adding the product. " + exception.getMessage());
        } finally {
            JPAUtil.closeEntityManager();
        }
    }

    public List<Product> getProduct() {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "SELECT p FROM Product p";
        try {
            Query query = manager.createQuery(jpql, Product.class);
            return query.getResultList();
        } catch (Exception exception) {
            System.out.println("Error retrieving products. " + exception.getMessage());
            return null;
        } finally {
            JPAUtil.closeEntityManager();
        }
    }

    public void update(Product product) {
        EntityManager manager = JPAUtil.getEntityManager();
        try {
            manager.getTransaction().begin();
            manager.merge(product);
            manager.getTransaction().commit();
        } catch (Exception exception) {
            manager.getTransaction().rollback();
            System.out.println("Error updating the product. " + exception.getMessage());
        } finally {
            JPAUtil.closeEntityManager();
        }
    }

    public void delete(Long id) {
        EntityManager manager = JPAUtil.getEntityManager();
        Product product = manager.find(Product.class, id);
        if (product != null) {
            try {
                manager.getTransaction().begin();
                manager.remove(product);
                manager.getTransaction().commit();
            } catch (Exception exception) {
                manager.getTransaction().rollback();
                System.out.println("Error deleting the product. " + exception.getMessage());
            } finally {
                JPAUtil.closeEntityManager();
            }
        }else{
            throw new EntityNotFoundException("Product with ID " + id + " not found.");
        }
    }
}
