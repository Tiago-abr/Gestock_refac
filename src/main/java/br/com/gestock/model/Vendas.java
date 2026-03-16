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
@Table(name="vendas")
public class Vendas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Clientes id_cliente;
    
    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private Produtos id_produto;
    
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    
    @Column(name = "data_venda")
    private LocalDateTime data_venda;
    
    private int quantidade;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Clientes getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Clientes id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Produtos getProduto() {
        return id_produto;
    }

    public void setProduto(Produtos produto) {
        this.id_produto = produto;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDateTime getData_venda() {
        return data_venda;
    }

    public void setData_venda(LocalDateTime data_venda) {
        this.data_venda = data_venda;
    }
    
    
}
