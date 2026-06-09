package br.com.gestock.service;

import br.com.gestock.model.Sale;
import br.com.gestock.repository.SaleRepository;
import java.util.List;

public class SaleService {
    
    private final SaleRepository repository;

    public SaleService(SaleRepository repository) {
        this.repository = repository;
    }
    
    public Sale create(Sale sale){
        if(sale == null){
            throw new RuntimeException("Sale is required.");
        }
        this.repository.save(sale);
        
        return sale; 
    }
    
    public List<Sale> findAll(){
        List<Sale> sales = this.repository.getSales();
        
        return sales;
    }
}
