package org.example.confeitaria.domain.model;

import org.example.confeitaria.domain.enums.TipoItem;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "item_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoItem tipo;

    @Column(nullable = false, length = 80)
    private String sabor;

    // Para BOLO
    @Column(name = "peso_kg")
    private Integer pesoKg;

    // Para DOCINHO (em unidades)
    @Column
    private Integer quantidade;

    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    public ItemPedido() {
    }

    public ItemPedido(Long id, Pedido pedido, TipoItem tipo, String sabor, Integer pesoKg, Integer quantidade, BigDecimal precoUnitario, BigDecimal subtotal) {
        this.id = id;
        this.pedido = pedido;
        this.tipo = tipo;
        this.sabor = sabor;
        this.pesoKg = pesoKg;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = subtotal;
    }

    // Getters e Setters manuais
    public Long getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public TipoItem getTipo() {
        return tipo;
    }

    public String getSabor() {
        return sabor;
    }

    public Integer getPesoKg() {
        return pesoKg;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setTipo(TipoItem tipo) {
        this.tipo = tipo;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public void setPesoKg(Integer pesoKg) {
        this.pesoKg = pesoKg;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}