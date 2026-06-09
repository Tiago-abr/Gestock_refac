package br.com.gestock.service;

import br.com.gestock.model.User;
import br.com.gestock.repository.UserRepository;
import java.util.List;

public class UserService {
    
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    
    public User create(User user){
        if(user == null){
            throw new RuntimeException("User is required.");
        }
        this.repository.save(user);
        
        return user; 
    }
    
    public List<User> findAll(){
        List<User> users = this.repository.getUsers();
        
        return users;
    }
    
    public User findById(Long id){
        if(id < 1){
            throw new RuntimeException("Id cannot be null.");
        }
        
        return this.repository.getUserByID(id);
    }
    
    public User findByUsername(String username){
        if(username.isBlank()){
            throw new RuntimeException("Username is required.");
        }
        
        return this.repository.findByUsername(username);
    }
}
