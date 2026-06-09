package br.com.gestock.service;

import br.com.gestock.model.User;
import br.com.gestock.model.enums.AccessLevel;
import br.com.gestock.repository.UserRepository;
import br.com.gestock.util.CryptographyUtils;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepositoryMock;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    public void createShouldSaveUserWhenUserIsValid(){
        User user = new User(1L, "João", AccessLevel.standard, CryptographyUtils.getMD5("Senhasuperforte123"));
        
        User result = userService.create(user);
        
        assertNotNull(result);
        assertEquals("João", result.getUsername());
        
        verify(userRepositoryMock, times(1)).save(user);
    }
    
    @Test
    public void createShouldThrowExceptionWhenUserIsNull(){
        RuntimeException exception = assertThrows(RuntimeException.class, () ->{
           userService.create(null);
        });
        
        assertEquals("User is required.", exception.getMessage());
        
        verify(userRepositoryMock, never()).save(any());
    }
    
    @Test
    public void findAllShouldReturnListOfUsers(){
        List<User> mockUsers = Arrays.asList(
                new User(1L, "João", AccessLevel.standard, CryptographyUtils.getMD5("Senhasuperforte123")),
                new User(2L, "Pedro", AccessLevel.standard, CryptographyUtils.getMD5("Senhasuperfraca321"))
        );
        
        when(userRepositoryMock.getUsers()).thenReturn(mockUsers);
        
        List<User> result = userService.findAll();
        
        assertEquals(2, result.size());
        assertEquals("João", result.get(0).getUsername());
        
        verify(userRepositoryMock, times(1)).getUsers();
    }
    
    @Test
    public void findByIdShouldReturnUserWhenIdIsValid(){
        Long id = 1L;
        User mockUser = new User(1L, "João", AccessLevel.standard, CryptographyUtils.getMD5("Senhasuperforte123"));
        when(userRepositoryMock.getUserByID(id)).thenReturn(mockUser);
        
        User result = userService.findById(id);
        
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(userRepositoryMock, times(1)).getUserByID(id);
    }
    
    @Test
    public void findByIdShouldThrowExceptionWhenIdIsLessThanOne(){
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.findById(0L);
        });
        
        assertEquals("Id cannot be null.", exception.getMessage());
        verify(userRepositoryMock, never()).getUserByID(anyLong());
    }
    
    @Test
    public void findByUsernameShouldReturnUserWhenUsernameIsValid(){
        String username = "João";
        User mockUser = new User(1L, "João", AccessLevel.standard, CryptographyUtils.getMD5("Senhasuperforte123"));
        when(userRepositoryMock.findByUsername(username)).thenReturn(mockUser);
        
        User result = userService.findByUsername(username);
        
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        verify(userRepositoryMock, times(1)).findByUsername(username);
    }
    
    @Test
    public void findByUsernameShouldThrowExceptionWhenUsernameIsBlank(){
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.findByUsername("");
        });
        
        assertEquals("Username is required.", exception.getMessage());
        verify(userRepositoryMock, never()).findByUsername(anyString());
    }
}
