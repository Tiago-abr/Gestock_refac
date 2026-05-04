package br.com.gestock.dto;

import br.com.gestock.model.User;
import br.com.gestock.model.enums.AccessLevel;

public record UserDTO(Long id, String username, AccessLevel role) {
    
    public UserDTO(User user){
        this(user.getId(), user.getUsername(), user.getAccessLevel());
    }
}
