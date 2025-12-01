package org.example.confeitaria.domain.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class PrecoService {

    // Tabelas de preços simples; ajuste conforme necessidade
    // Bolo: preço por kg
    private final Map<String, BigDecimal> precoBoloPorKg = new HashMap<>();

    // Docinho: preço por "pacote" de 25 unidades
    private final Map<String, BigDecimal> precoDocinhoPor25 = new HashMap<>();

    public PrecoService() {
        // Bolos
        precoBoloPorKg.put("chocolate", new BigDecimal("65.00"));
        precoBoloPorKg.put("ninho", new BigDecimal("70.00"));
        precoBoloPorKg.put("cenoura", new BigDecimal("60.00"));
        precoBoloPorKg.put("prestigio", new BigDecimal("72.00"));

        // Docinhos (por 25 unidades)
        precoDocinhoPor25.put("beijinho", new BigDecimal("35.00"));
        precoDocinhoPor25.put("brigadeiro", new BigDecimal("38.00"));
        precoDocinhoPor25.put("cajuzinho", new BigDecimal("34.00"));
        precoDocinhoPor25.put("bicho-de-pe", new BigDecimal("36.00"));
    }

    public BigDecimal precoBoloPorKg(String sabor) {
        String key = normalizar(sabor);
        BigDecimal preco = precoBoloPorKg.get(key);
        if (preco == null) {
            // preço padrão
            return new BigDecimal("65.00");
        }
        return preco;
    }

    public BigDecimal precoDocinhoPor25(String sabor) {
        String key = normalizar(sabor);
        BigDecimal preco = precoDocinhoPor25.get(key);
        if (preco == null) {
            // preço padrão
            return new BigDecimal("35.00");
        }
        return preco;
    }

    private String normalizar(String s) {
        if (s == null) return "";
        return s.trim().toLowerCase().replace(' ', '-');
    }
}