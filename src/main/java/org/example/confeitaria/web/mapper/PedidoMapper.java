package org.example.confeitaria.web.mapper;

import org.example.confeitaria.domain.model.ItemPedido;
import org.example.confeitaria.domain.model.Pedido;
import org.example.confeitaria.web.dto.pedido.ItemPedidoResponse;
import org.example.confeitaria.web.dto.pedido.PedidoResponse;

import java.util.ArrayList;
import java.util.List;

public class PedidoMapper {

    public static PedidoResponse toResponse(Pedido pedido) {
        PedidoResponse resp = new PedidoResponse();
        resp.setId(pedido.getId());
        resp.setClienteId(pedido.getCliente().getId());
        resp.setClienteNome(pedido.getCliente().getNome());
        resp.setDataEntrega(pedido.getDataEntrega());
        resp.setCriadoEm(pedido.getCriadoEm());
        resp.setStatus(pedido.getStatus());
        resp.setValorTotal(pedido.getValorTotal());
        List<ItemPedidoResponse> itens = new ArrayList<>();
        for (ItemPedido it : pedido.getItens()) {
            ItemPedidoResponse ir = new ItemPedidoResponse();
            ir.setId(it.getId());
            ir.setTipo(it.getTipo());
            ir.setSabor(it.getSabor());
            ir.setPesoKg(it.getPesoKg());
            ir.setQuantidade(it.getQuantidade());
            ir.setPrecoUnitario(it.getPrecoUnitario());
            ir.setSubtotal(it.getSubtotal());
            itens.add(ir);
        }
        resp.setItens(itens);
        return resp;
    }
}