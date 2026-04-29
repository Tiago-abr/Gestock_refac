package br.com.gestock.service;

import br.com.gestock.model.Usuario;
import br.com.gestock.repository.UsuarioRepository;
import br.com.gestock.service.exceptions.InvalidLoginException;
import br.com.gestock.util.Criptografia;

public class AuthService {
    private UsuarioRepository UsuarioRepository;
    
    public AuthService(UsuarioRepository UsuarioRepository){
        this.UsuarioRepository = UsuarioRepository;
    }
    
    public Usuario validateLogin(String username, String password){
        Usuario usuario = UsuarioRepository.findByUsername(username);
        
        String hashDigitado = Criptografia.getMD5(password);
        
        if(!hashDigitado.equals(usuario.getPassword_hash())){
            throw new InvalidLoginException("Senha incorreta.");
        }
        
        return usuario;
    }
}
