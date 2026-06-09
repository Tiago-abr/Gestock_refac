package br.com.gestock.service;

import br.com.gestock.model.Customer;
import br.com.gestock.model.Product;
import br.com.gestock.model.Sale;
import br.com.gestock.repository.SaleRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class SaleServiceTest {
    
    @Mock
    private SaleRepository saleRepositoryMock;
    
    @InjectMocks
    private SaleService saleService;
    
    @Test
    public void createShouldReturnSaleWhenSaleIsValid(){
        Sale sale = new Sale
                (1L,
                new Customer(1L, "João", "(11)9999-99999", "São paulo"),
                new Product(1L, "Computador", BigDecimal.valueOf(3500.00), "Eletrônico"),
                BigDecimal.valueOf(3500.00), LocalDateTime.now(),
                1);
        
        Sale result = saleService.create(sale);
        
        assertNotNull(result);
        assertEquals("Computador", result.getId_product().getName());
        assertEquals("João", result.getId_customer().getName());
        verify(saleRepositoryMock, times(1)).save(any());
    }
    
    @Test
    public void findAllShouldReturnListOfSales(){
        List<Sale> mockSales = Arrays.asList(
             new Sale
                (1L,
                new Customer(1L, "João", "(11)9999-99999", "São paulo"),
                new Product(1L, "Computador", BigDecimal.valueOf(3500.00), "Eletrônico"),
                BigDecimal.valueOf(3500.00), LocalDateTime.now(),
                1),
                new Sale
                (2L,
                new Customer(1L, "Pedro", "(21)9999-99999", "Rio de Janeiro"),
                new Product(2L, "Playstation 5", BigDecimal.valueOf(4000.00), "Eletrônico"),
                BigDecimal.valueOf(4000.00), LocalDateTime.now(),
                1)
        );
        
        when(saleRepositoryMock.getSales()).thenReturn(mockSales);
        
        List<Sale> result = saleService.findAll();
        
        assertEquals(2, result.size());
        assertEquals("Computador", result.get(0).getId_product().getName());
        assertEquals("João", result.get(0).getId_customer().getName());
        assertEquals("Playstation 5", result.get(1).getId_product().getName());
        assertEquals("Pedro", result.get(1).getId_customer().getName());
    } 
    
}
