package org.example.confeitaria.web.dto.pedido;

import org.example.confeitaria.domain.enums.TipoItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ItemPedidoRequest {

    @NotNull
    private TipoItem tipo;

    @NotBlank
    private String sabor;

    // Para BOLO
    @Min(1)
    private Integer pesoKg;

    // Para DOCINHO (em unidades)
    @Min(1)
    private Integer quantidade;

    public ItemPedidoRequest() {
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
}