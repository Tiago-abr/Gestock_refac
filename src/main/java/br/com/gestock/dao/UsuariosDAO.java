package br.com.gestock.dao;

import br.com.gestock.model.Usuarios;
import br.com.gestock.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDAO {

    public void cadastrar(Usuarios usuario) {
        EntityManager manager = JPAUtil.getEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(usuario);
            manager.getTransaction().commit();
        } catch (Exception ex) {
            manager.getTransaction().rollback();
            System.out.println("Erro ao cadastrar. " + ex.getMessage());
        } finally {
            JPAUtil.closeEntityManager();
        }
    }

    public Usuarios validarLogin(String username, String password) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "SELECT u FROM Usuarios u WHERE u.username = :username AND u.password_hash = :password";
        try {
            TypedQuery<Usuarios> query = manager.createQuery(jpql, Usuarios.class);

            query.setParameter("username", username);
            query.setParameter("password", password);

            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao validar login. " + e.getMessage());
            return null;
        } finally {
            JPAUtil.closeEntityManager();
        }
    }

    public List<Usuarios> getUsuarios() {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "SELECT u FROM Usuarios u";
        try {
            Query query = manager.createQuery(jpql, Usuarios.class);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no retorno de usuarios.");
            return null;
        } finally {
            JPAUtil.closeEntityManager();
        }
    }

    public void Editar(Usuarios usuario) {
        EntityManager manager = JPAUtil.getEntityManager();
        try {
            manager.getTransaction().begin();
            manager.merge(usuario);
            manager.getTransaction().commit();
        }catch(Exception ex){
            manager.getTransaction().rollback();
            System.out.println("Erro ao editar usuario. "+ex.getMessage());
        }finally{
            JPAUtil.closeEntityManager();
        }
    }
    
    public Usuarios getUsuariosID(int id){
        EntityManager manager = JPAUtil.getEntityManager();
        try{
            Usuarios usuario = manager.find(Usuarios.class, id);
            return usuario;
        }catch(Exception ex){
            System.out.println("Erro ao buscar usuario. "+ex.getMessage());
            return null;
        }finally{
            JPAUtil.closeEntityManager();
        }
    }
    
    public void deletar(int id){
        EntityManager manager = JPAUtil.getEntityManager();
        Usuarios usuario = manager.find(Usuarios.class, id);
        if (usuario != null) {
            try {
                manager.getTransaction().begin();
                manager.remove(usuario);
                manager.getTransaction().commit();
            } catch (Exception ex) {
                manager.getTransaction().rollback();
                System.out.println("Erro ao deletar usuário. " + ex.getMessage());
            } finally {
                JPAUtil.closeEntityManager();
            }
        }else{
            throw new EntityNotFoundException("Usuário com ID " + id + " não encontrado.");
        }
    }
}
