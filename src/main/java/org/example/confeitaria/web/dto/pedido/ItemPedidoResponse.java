package org.example.confeitaria.web.dto.pedido;

import org.example.confeitaria.domain.enums.TipoItem;

import java.math.BigDecimal;

public class ItemPedidoResponse {

    private Long id;
    private TipoItem tipo;
    private String sabor;
    private Integer pesoKg;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal subtotal;

    public ItemPedidoResponse() {
    }

    public Long getId() {
        return id;
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