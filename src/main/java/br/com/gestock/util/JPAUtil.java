package br.com.gestock.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final String PERSISTENCE_UNIT = "Gestock-PU";
    
    private static EntityManager manager;
    private static EntityManagerFactory factory;
    
    
    public static EntityManager getEntityManager(){
        if(factory == null || !factory.isOpen()){
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        }
        
        if(manager == null || !manager.isOpen()){
            manager = factory.createEntityManager();
        }
        
        return manager;
    }
    
    public static void closeEntityManager(){
        if(manager.isOpen() && manager != null){
            manager.close();
            factory.close();
        }
    }
}
