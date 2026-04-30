package br.com.gestock.service;

import br.com.gestock.model.User;
import br.com.gestock.repository.UserRepository;
import br.com.gestock.service.exceptions.InvalidLoginException;
import br.com.gestock.util.Criptografia;

public class AuthService {
    private UserRepository UsuarioRepository;
    
    public AuthService(UserRepository UsuarioRepository){
        this.UsuarioRepository = UsuarioRepository;
    }
    
    public User validateLogin(String username, String password){
        User usuario = UsuarioRepository.findByUsername(username);
        
        String hashDigitado = Criptografia.getMD5(password);
        
        if(!hashDigitado.equals(usuario.getPassword_hash())){
            throw new InvalidLoginException("Senha incorreta.");
        }
        
        return usuario;
    }
}
