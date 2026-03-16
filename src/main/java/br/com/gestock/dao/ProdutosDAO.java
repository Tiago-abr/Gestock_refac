package br.com.gestock.dao;

import br.com.gestock.model.Produtos;
import br.com.gestock.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import java.util.List;

public class ProdutosDAO {

    public void adicionarProduto(Produtos produto) {
        EntityManager manager = JPAUtil.getEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(produto);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            System.out.println("Erro ao adicionar produto. " + e.getMessage());
        } finally {
            JPAUtil.closeEntityManager();
        }
    }

    public List<Produtos> getProdutos() {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "SELECT p FROM Produtos p";
        try {
            Query query = manager.createQuery(jpql, Produtos.class);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro ao buscar produtos. " + e.getMessage());
            return null;
        } finally {
            JPAUtil.closeEntityManager();
        }
    }

    public void atualizar(Produtos produto) {
        EntityManager manager = JPAUtil.getEntityManager();
        try {
            manager.getTransaction().begin();
            manager.merge(produto);
            manager.getTransaction().commit();
        } catch (Exception ex) {
            manager.getTransaction().rollback();
            System.out.println("Erro ao atualizar produto. " + ex.getMessage());
        } finally {
            JPAUtil.closeEntityManager();
        }
    }

    public void deletar(int id) {
        EntityManager manager = JPAUtil.getEntityManager();
        Produtos produto = manager.find(Produtos.class, id);
        if (produto != null) {
            try {
                manager.getTransaction().begin();
                manager.remove(produto);
                manager.getTransaction().commit();
            } catch (Exception ex) {
                manager.getTransaction().rollback();
                System.out.println("Erro ao deletar produto. " + ex.getMessage());
            } finally {
                JPAUtil.closeEntityManager();
            }
        }else{
            throw new EntityNotFoundException("Produto com ID " + id + " não encontrado.");
        }
    }
}
