package br.com.gestock.service;

import br.com.gestock.model.Product;
import br.com.gestock.repository.ProductRepository;
import java.util.List;

public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }
    
    public Product create(Product product){
        if(product == null){
            throw new RuntimeException("Product is required.");
        }
        this.repository.save(product);
        
        return product; 
    }
    
    public List<Product> findAll(){
        List<Product> products = this.repository.getProduct();
        
        return products;
    }
}
