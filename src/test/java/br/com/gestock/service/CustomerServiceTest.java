package br.com.gestock.service;

import br.com.gestock.model.Customer;
import br.com.gestock.repository.CustomerRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    
    @Mock
    private CustomerRepository customerRepositoryMock;
    
    @InjectMocks
    private CustomerService customerService;
    
    @Test
    public void createShouldReturnUserWhenCustomerIsValid(){
        Customer customer = new Customer(1L, "João", "(11)9999-99999", "São paulo");
        
        Customer result = customerService.create(customer);
        
        assertNotNull(result);
        assertEquals("João", result.getName());
        verify(customerRepositoryMock, times(1)).save(any());
    }
    
    @Test
    public void findAllShouldReturnListOfCustomers(){
        List<Customer> customersMock = Arrays.asList(
                new Customer(1L, "João", "(11)9999-99999", "São paulo"),
                new Customer(2L, "Pedro", "(21)9999-99999", "Rio de Janeiro")
        );
        
        when(customerRepositoryMock.getCustomers()).thenReturn(customersMock);
        
        List<Customer> result = customerService.findAll();
        
        assertEquals(2, result.size());
        assertEquals("João", result.get(0).getName());
    }
    
    @Test
    public void findByIdShouldReturnUserWhenIdIsValid(){
        Long id = 1L;
        Customer mockCustomer = new Customer(1L, "João", "(11)9999-99999", "São paulo");
        when(customerRepositoryMock.getCustomerByID(id)).thenReturn(mockCustomer);
        
        Customer result = customerService.findById(id);
        
        assertNotNull(result);
        assertEquals("João", result.getName());
        verify(customerRepositoryMock, times(1)).getCustomerByID(id);
    }
    
    @Test
    public void findByIdShouldThrowExceptionWhenIdLessThanOne(){
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.findById(0L);
        });
        
        assertEquals("Id cannot be null.", exception.getMessage());
        verify(customerRepositoryMock, never()).getCustomerByID(anyLong());
    }
    
}
