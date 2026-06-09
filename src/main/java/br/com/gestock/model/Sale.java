package br.com.gestock.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_customer", nullable = false)
    private Customer id_customer;
    
    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    private Product id_product;
    
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    
    @Column(name = "date_sale")
    private LocalDateTime date_sale;
    
    private int quantity;

    public Sale(Long id, Customer id_customer, Product id_product, BigDecimal total, LocalDateTime date_sale, int quantity) {
        this.id = id;
        this.id_customer = id_customer;
        this.id_product = id_product;
        this.total = total;
        this.date_sale = date_sale;
        this.quantity = quantity;
    }

    public Sale() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getId_customer() {
        return id_customer;
    }

    public void setId_customer(Customer id_customer) {
        this.id_customer = id_customer;
    }

    public Product getId_product() {
        return id_product;
    }

    public void setId_product(Product id_product) {
        this.id_product = id_product;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDateTime getDate_sale() {
        return date_sale;
    }

    public void setDate_sale(LocalDateTime date_sale) {
        this.date_sale = date_sale;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
