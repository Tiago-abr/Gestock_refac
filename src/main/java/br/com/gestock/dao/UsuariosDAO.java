package br.com.gestock.dao;

import br.com.gestock.model.Usuarios;
import br.com.gestock.util.Criptografia;
import br.com.gestock.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
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
            throw new RuntimeException("Erro ao realizar o cadastro");
        } finally {
            JPAUtil.closeEntityManager();
        }
    }

    public Usuarios validarLogin(String username, String password) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "SELECT u FROM Usuarios u WHERE u.username = :username";
        try {
            TypedQuery<Usuarios> query = manager.createQuery(jpql, Usuarios.class);
            query.setParameter("username", username);
            
            List<Usuarios> resultados = query.getResultList();
            if (resultados.isEmpty()) return null;
            
            Usuarios usuario = resultados.get(0);
            String hashDaSenhaDigitada = Criptografia.getMD5(password);
        
            if (hashDaSenhaDigitada.equals(usuario.getPassword_hash())) {
                return usuario;
            }
            return null;
        } catch (Exception exception) {
            throw new RuntimeException("Erro ao validar login", exception);
        } finally {
            JPAUtil.closeEntityManager();
        }
    }

    public List<Usuarios> getUsuarios() {
        EntityManager manager = JPAUtil.getEntityManager();
        try {
            return manager.createQuery("SELECT u FROM Usuarios u", Usuarios.class).getResultList();
        } catch (Exception exception) {
            throw new RuntimeException("Erro ao buscar usuários", exception);
        } finally {
            JPAUtil.closeEntityManager();
        }
    }

    public void editar(Usuarios usuario) {
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
    
    public Usuarios getUsuariosByID(int id){
        EntityManager manager = JPAUtil.getEntityManager();
        try{
            Usuarios usuario = manager.find(Usuarios.class, id);
            return usuario;
        }catch(Exception exception){
            throw new RuntimeException("Erro ao buscar usuário", exception);
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
