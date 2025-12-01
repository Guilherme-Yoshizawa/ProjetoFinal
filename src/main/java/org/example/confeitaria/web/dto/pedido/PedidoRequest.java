package org.example.confeitaria.web.dto.pedido;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.List;

public class PedidoRequest {

    @NotNull
    private Long clienteId;

    @NotNull
    private OffsetDateTime dataEntrega;

    @Valid
    private List<ItemPedidoRequest> itens;

    public PedidoRequest() {
    }

    public Long getClienteId() {
        return clienteId;
    }

    public OffsetDateTime getDataEntrega() {
        return dataEntrega;
    }

    public List<ItemPedidoRequest> getItens() {
        return itens;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public void setDataEntrega(OffsetDateTime dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public void setItens(List<ItemPedidoRequest> itens) {
        this.itens = itens;
    }
}
