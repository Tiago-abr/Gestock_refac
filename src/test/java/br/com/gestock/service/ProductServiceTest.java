package br.com.gestock.service;

import br.com.gestock.model.Product;
import br.com.gestock.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    
    @Mock
    private ProductRepository productRepositoryMock;
    
    @InjectMocks
    private ProductService productService;
    
    @Test
    public void createShouldReturnProductWhenProductIsValid(){
        Product product = new Product(1L, "Computador", BigDecimal.valueOf(3500.00), "Eletrônico");
        
        Product result = productService.create(product);
        
        assertNotNull(result);
        assertEquals("Computador", result.getName());
        verify(productRepositoryMock, times(1)).save(any());
    }
    
    @Test
    public void findAllShouldReturnListOfProducts(){
        List<Product> mockProducts = Arrays.asList(
                new Product(1L, "Computador", BigDecimal.valueOf(3500.00), "Eletrônico"),
                new Product(1L, "Playstation 5", BigDecimal.valueOf(4000.00), "Eletrônico")
        );
        
        when(productRepositoryMock.getProduct()).thenReturn(mockProducts);
        
        List<Product> result = productService.findAll();
        
        assertEquals(2, result.size());
        assertEquals("Computador", result.get(0).getName());
        assertEquals("Playstation 5", result.get(1).getName());
    }
}
