package org.example.confeitaria.web.dto.pedido;

import org.example.confeitaria.domain.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class PedidoResponse {
    private Long id;
    private Long clienteId;
    private String clienteNome;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime criadoEm;
    private StatusPedido status;
    private BigDecimal valorTotal;
    private List<ItemPedidoResponse> itens;

    public PedidoResponse() {
    }

    public Long getId() {
        return id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public OffsetDateTime getDataEntrega() {
        return dataEntrega;
    }

    public OffsetDateTime getCriadoEm() {
        return criadoEm;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public List<ItemPedidoResponse> getItens() {
        return itens;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public void setDataEntrega(OffsetDateTime dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public void setCriadoEm(OffsetDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setItens(List<ItemPedidoResponse> itens) {
        this.itens = itens;
    }
}