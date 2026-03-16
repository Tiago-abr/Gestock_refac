package br.com.gestock.dao;

import br.com.gestock.model.Vendas;
import br.com.gestock.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import java.util.List;

public class VendasDAO {
    
    public void adicionar(Vendas venda){
        EntityManager manager = JPAUtil.getEntityManager();
        try{
            manager.getTransaction().begin();
            manager.persist(venda);
            manager.getTransaction().commit();
        }catch(Exception e){
            manager.getTransaction().rollback();
            System.out.println("Erro ao adicionar venda.");
        }finally{
            JPAUtil.closeEntityManager();
        }
    }
    
    public List<Vendas> getVendas(){
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "SELECT v FROM Vendas v";
        try{
            Query query = manager.createQuery(jpql, Vendas.class);
            return query.getResultList();
        }catch(Exception e){
            System.out.println("Erro ao retornar vendas.");
            return null;
        }finally{
            JPAUtil.closeEntityManager();
        }
    }
    
    public void atualizar(Vendas venda){
        EntityManager manager = JPAUtil.getEntityManager();
        try{
            manager.getTransaction().begin();
            manager.merge(venda);
            manager.getTransaction().commit();
        }catch(Exception ex){
            manager.getTransaction().rollback();
            System.out.println("Erro ao atualizar venda. "+ex.getMessage());
        }finally{
            JPAUtil.closeEntityManager();
        }
    }
    
    public void deletar(int id) {
        EntityManager manager = JPAUtil.getEntityManager();
        Vendas venda = manager.find(Vendas.class, id);
        if (venda != null) {
            try {
                manager.getTransaction().begin();
                manager.remove(venda);
                manager.getTransaction().commit();
            } catch (Exception ex) {
                manager.getTransaction().rollback();
                System.out.println("Erro ao deletar venda. " + ex.getMessage());
            } finally {
                JPAUtil.closeEntityManager();
            }
        }else{
            throw new EntityNotFoundException("Venda com ID " + id + " não encontrado.");
        }
    }
}
