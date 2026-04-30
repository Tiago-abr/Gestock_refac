package br.com.gestock.repository;

import br.com.gestock.model.User;
import br.com.gestock.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

public class UserRepository {

    public void save(User user) {
        EntityManager manager = JPAUtil.getEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(user);
            manager.getTransaction().commit();
        } catch (Exception exception) {
            manager.getTransaction().rollback();
            throw new RuntimeException("Error adding the user "+exception.getMessage());
        } finally {
            JPAUtil.closeEntityManager();
        }
    }

    public User findByUsername(String username) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "SELECT u FROM User u WHERE u.username = :username";
        try {
            return manager.createQuery(jpql, User.class)
                    .setParameter("username", username)
                    .getResultList()
                    .getFirst();
        } catch (Exception exception) {
            throw new RuntimeException("Error retrieving user "+exception.getMessage());
        } finally {
            JPAUtil.closeEntityManager();
        }
    }

    public List<User> getUsers() {
        EntityManager manager = JPAUtil.getEntityManager();
        try {
            return manager.createQuery("SELECT u FROM User u", User.class).getResultList();
        } catch (Exception exception) {
            throw new RuntimeException("Error retrieving user "+exception.getMessage());
        } finally {
            JPAUtil.closeEntityManager();
        }
    }

    public void update(User user) {
        EntityManager manager = JPAUtil.getEntityManager();
        try {
            manager.getTransaction().begin();
            manager.merge(user);
            manager.getTransaction().commit();
        }catch(Exception exception){
            manager.getTransaction().rollback();
            throw new RuntimeException("Error updating the user", exception);
        }finally{
            JPAUtil.closeEntityManager();
        }
    }
    
    public User getUserByID(Long id){
        EntityManager manager = JPAUtil.getEntityManager();
        try{
            User user = manager.find(User.class, id);
            return user;
        }catch(Exception exception){
            throw new RuntimeException("Error retrieving user "+exception.getMessage());
        }finally{
            JPAUtil.closeEntityManager();
        }
    }
    
    public void delete(Long id){
        EntityManager manager = JPAUtil.getEntityManager();
        User user = manager.find(User.class, id);
        if (user != null) {
            try {
                manager.getTransaction().begin();
                manager.remove(user);
                manager.getTransaction().commit();
            } catch (Exception exception) {
                manager.getTransaction().rollback();
                throw new RuntimeException("Error deleting the user "+exception.getMessage());
            } finally {
                JPAUtil.closeEntityManager();
            }
        }else{
            throw new EntityNotFoundException("User with ID " + id + " not found.");
        }
    }
}
