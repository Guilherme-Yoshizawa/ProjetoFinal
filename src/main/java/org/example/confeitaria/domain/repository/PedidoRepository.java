package org.example.confeitaria.domain.repository;

import org.example.confeitaria.domain.enums.StatusPedido;
import org.example.confeitaria.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByStatus(StatusPedido status);
    List<Pedido> findByDataEntregaBetween(OffsetDateTime inicio, OffsetDateTime fim);
}