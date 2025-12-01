package org.example.confeitaria.domain.service;

import org.example.confeitaria.domain.enums.TipoItem;
import org.example.confeitaria.domain.model.ItemPedido;
import org.example.confeitaria.domain.model.Pedido;
import org.example.confeitaria.web.exception.NegocioException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class ValidacaoService {

    public void validarAntecedencia(Pedido pedido) {
        // Pedido só aceito com 2 dias de antecedência
        OffsetDateTime agora = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime minimo = agora.plusDays(2);
        if (pedido.getDataEntrega() == null || !pedido.getDataEntrega().isAfter(minimo.minusSeconds(1))) {
            throw new NegocioException("Pedidos só são aceitos com 2 dias de antecedência.");
        }
    }

    public void validarItens(Pedido pedido) {
        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            throw new NegocioException("O pedido deve conter ao menos 1 item.");
        }

        // Regra docinhos: por sabor, múltiplos de 25; no total do pedido, múltiplos de 100
        int totalDocinhos = 0;
        for (ItemPedido item : pedido.getItens()) {
            if (item.getTipo() == TipoItem.BOLO) {
                if (item.getPesoKg() == null || item.getPesoKg() <= 0) {
                    throw new NegocioException("Bolos devem ser pedidos em quilos inteiros positivos.");
                }
            } else if (item.getTipo() == TipoItem.DOCINHO) {
                if (item.getQuantidade() == null || item.getQuantidade() <= 0 || item.getQuantidade() % 25 != 0) {
                    throw new NegocioException("Docinhos devem ser pedidos em múltiplos de 25 unidades por sabor.");
                }
                totalDocinhos += item.getQuantidade();
            }
        }
        if (totalDocinhos > 0 && totalDocinhos % 100 != 0) {
            throw new NegocioException("O total de docinhos no pedido deve fechar múltiplos de 100 unidades.");
        }
    }
}