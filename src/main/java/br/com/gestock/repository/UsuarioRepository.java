package br.com.gestock.repository;

import br.com.gestock.model.Usuario;
import br.com.gestock.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

public class UsuarioRepository {

    public void cadastrar(Usuario usuario) {
        EntityManager manager = JPAUtil.getEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(usuario);
            manager.getTransaction().commit();
        } catch (Exception ex) {
            manager.getTransaction().rollback();
            throw new RuntimeException("Erro ao realizar o cadastro");
        } finally {
            JPAUtil.closeEntityManager();
        }
    }

    public Usuario findByUsername(String username) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "SELECT u FROM Usuarios u WHERE u.username = :username";
        try {
            return manager.createQuery(jpql, Usuario.class)
                    .setParameter("username", username)
                    .getResultList()
                    .getFirst();
        } catch (Exception exception) {
            throw new RuntimeException("Erro ao buscar usuario", exception);
        } finally {
            JPAUtil.closeEntityManager();
        }
    }

    public List<Usuario> getUsuarios() {
        EntityManager manager = JPAUtil.getEntityManager();
        try {
            return manager.createQuery("SELECT u FROM Usuarios u", Usuario.class).getResultList();
        } catch (Exception exception) {
            throw new RuntimeException("Erro ao buscar usuários", exception);
        } finally {
            JPAUtil.closeEntityManager();
        }
    }

    public void editar(Usuario usuario) {
        EntityManager manager = JPAUtil.getEntityManager();
        try {
            manager.getTransaction().begin();
            manager.merge(usuario);
            manager.getTransaction().commit();
        }catch(Exception exception){
            manager.getTransaction().rollback();
            throw new RuntimeException("Erro ao editar usuário", exception);
        }finally{
            JPAUtil.closeEntityManager();
        }
    }
    
    public Usuario getUsuariosByID(int id){
        EntityManager manager = JPAUtil.getEntityManager();
        try{
            Usuario usuario = manager.find(Usuario.class, id);
            return usuario;
        }catch(Exception exception){
            throw new RuntimeException("Erro ao buscar usuário", exception);
        }finally{
            JPAUtil.closeEntityManager();
        }
    }
    
    public void deletar(int id){
        EntityManager manager = JPAUtil.getEntityManager();
        Usuario usuario = manager.find(Usuario.class, id);
        if (usuario != null) {
            try {
                manager.getTransaction().begin();
                manager.remove(usuario);
                manager.getTransaction().commit();
            } catch (Exception exception) {
                manager.getTransaction().rollback();
                throw new RuntimeException("Erro ao deletar usuário", exception);
            } finally {
                JPAUtil.closeEntityManager();
            }
        }else{
            throw new EntityNotFoundException("Usuário com ID " + id + " não encontrado.");
        }
    }
}
