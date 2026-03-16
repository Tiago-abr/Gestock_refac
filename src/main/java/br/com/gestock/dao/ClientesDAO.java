package br.com.gestock.dao;

import br.com.gestock.model.Clientes;
import br.com.gestock.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import java.util.List;

public class ClientesDAO {
    
    public void adicionar(Clientes cliente){
        EntityManager manager = JPAUtil.getEntityManager();
        
        try{
            manager.getTransaction().begin();
            manager.persist(cliente);
            manager.getTransaction().commit();
        }catch(Exception ex){
            manager.getTransaction().rollback();
            System.out.println("Erro ao inserir produto. "+ex.getMessage());
        }finally{
            JPAUtil.closeEntityManager();
        }
    }
    
    public List<Clientes> getClientes(){
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "SELECT c FROM Clientes c";
        try{
            Query query = manager.createQuery(jpql, Clientes.class);
            return query.getResultList();
        }catch(Exception e){
            System.out.println("Erro ao buscar clientes. "+e.getMessage());
            return null;
        }finally{
            JPAUtil.closeEntityManager();
        }
    }
    
    public void atualizar(Clientes cliente){
        EntityManager manager = JPAUtil.getEntityManager();
        try{
            manager.getTransaction().begin();
            manager.merge(cliente);
            manager.getTransaction().commit();
        }catch(Exception ex){
            manager.getTransaction().rollback();
            System.out.println("Erro ao atualizar clientes. "+ex.getMessage());
        }finally{
            JPAUtil.closeEntityManager();
        }
    }
    
    
    public void deletar(int id) {
        EntityManager manager = JPAUtil.getEntityManager();
        Clientes cliente = manager.find(Clientes.class, id);
        if (cliente != null) {
            try {
                manager.getTransaction().begin();
                manager.remove(cliente);
                manager.getTransaction().commit();
            } catch (Exception ex) {
                manager.getTransaction().rollback();
                System.out.println("Erro ao deletar cliente. " + ex.getMessage());
            } finally {
                JPAUtil.closeEntityManager();
            }
        }else{
            throw new EntityNotFoundException("Cliente com ID " + id + " não encontrado.");
        }
    }
}
