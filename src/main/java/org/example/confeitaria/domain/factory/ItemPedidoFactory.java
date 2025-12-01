package org.example.confeitaria.domain.factory;

import org.example.confeitaria.domain.enums.TipoItem;
import org.example.confeitaria.domain.model.ItemPedido;
import org.example.confeitaria.domain.service.PrecoService;

import java.math.BigDecimal;

public class ItemPedidoFactory {

    private final PrecoService precoService;

    public ItemPedidoFactory(PrecoService precoService) {
        this.precoService = precoService;
    }

    public ItemPedido criarBolo(String sabor, int pesoKg) {
        if (pesoKg <= 0) {
            throw new IllegalArgumentException("Peso do bolo deve ser inteiro positivo (kg).");
        }
        BigDecimal precoPorKg = precoService.precoBoloPorKg(sabor);
        BigDecimal subtotal = precoPorKg.multiply(BigDecimal.valueOf(pesoKg));

        ItemPedido item = new ItemPedido();
        item.setTipo(TipoItem.BOLO);
        item.setSabor(sabor);
        item.setPesoKg(pesoKg);
        item.setQuantidade(null);
        item.setPrecoUnitario(precoPorKg);
        item.setSubtotal(subtotal);
        return item;
    }

    // quantidade em unidades; deve ser múltiplo de 25
    public ItemPedido criarDocinho(String sabor, int quantidadeUnidades) {
        if (quantidadeUnidades <= 0 || quantidadeUnidades % 25 != 0) {
            throw new IllegalArgumentException("Docinhos devem ser pedidos em múltiplos de 25 unidades.");
        }
        BigDecimal precoPor25 = precoService.precoDocinhoPor25(sabor);
        int pacotesDe25 = quantidadeUnidades / 25;
        BigDecimal subtotal = precoPor25.multiply(BigDecimal.valueOf(pacotesDe25));

        ItemPedido item = new ItemPedido();
        item.setTipo(TipoItem.DOCINHO);
        item.setSabor(sabor);
        item.setPesoKg(null);
        item.setQuantidade(quantidadeUnidades);
        item.setPrecoUnitario(precoPor25);
        item.setSubtotal(subtotal);
        return item;
    }
}