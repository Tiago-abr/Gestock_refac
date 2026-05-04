package br.com.gestock.service;

import br.com.gestock.dto.UserDTO;
import br.com.gestock.model.User;
import br.com.gestock.repository.UserRepository;
import br.com.gestock.service.exceptions.InvalidLoginException;
import br.com.gestock.util.CryptographyUtils;

public class AuthService {
    private UserRepository UsuarioRepository;
    
    public AuthService(UserRepository UsuarioRepository){
        this.UsuarioRepository = UsuarioRepository;
    }
    
    public UserDTO validateLogin(String username, String password){
        User user = UsuarioRepository.findByUsername(username);
        
        String hashPassword = CryptographyUtils.getMD5(password);
        
        if(!hashPassword.equals(user.getPassword_hash())){
            throw new InvalidLoginException("Incorrect password.");
        }
        
        return new UserDTO(user);
    }
}
