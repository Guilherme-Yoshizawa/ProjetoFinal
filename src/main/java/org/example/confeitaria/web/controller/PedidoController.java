package org.example.confeitaria.web.controller;

import org.example.confeitaria.domain.enums.StatusPedido;
import org.example.confeitaria.domain.model.Pedido;
import org.example.confeitaria.domain.repository.PedidoRepository;
import org.example.confeitaria.domain.service.PedidoService;
import org.example.confeitaria.web.dto.pedido.PedidoRequest;
import org.example.confeitaria.web.dto.pedido.PedidoResponse;
import org.example.confeitaria.web.exception.NotFoundException;
import org.example.confeitaria.web.mapper.PedidoMapper;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoService pedidoService,
                            PedidoRepository pedidoRepository) {
        this.pedidoService = pedidoService;
        this.pedidoRepository = pedidoRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse criar(@Valid @RequestBody PedidoRequest req) {
        Pedido p = pedidoService.criar(req);
        return PedidoMapper.toResponse(p);
    }

    @GetMapping
    public List<PedidoResponse> listar(@RequestParam(required = false) StatusPedido status,
                                       @RequestParam(required = false)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime inicio,
                                       @RequestParam(required = false)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fim) {
        List<Pedido> lista;
        if (status != null) {
            lista = pedidoRepository.findByStatus(status);
        } else if (inicio != null && fim != null) {
            lista = pedidoRepository.findByDataEntregaBetween(inicio, fim);
        } else {
            lista = pedidoService.listar();
        }
        return lista.stream().map(PedidoMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PedidoResponse buscar(@PathVariable Long id) {
        Pedido p = pedidoService.buscarPorId(id);
        return PedidoMapper.toResponse(p);
    }

    @PutMapping("/{id}")
    public PedidoResponse atualizar(@PathVariable Long id, @Valid @RequestBody PedidoRequest req) {
        Pedido p = pedidoService.atualizar(id, req);
        return PedidoMapper.toResponse(p);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        pedidoService.deletar(id);
    }

    @PostMapping("/{id}/confirmar")
    public PedidoResponse confirmar(@PathVariable Long id) {
        Pedido p = pedidoService.confirmar(id);
        return PedidoMapper.toResponse(p);
    }

    @PostMapping("/{id}/cancelar")
    public PedidoResponse cancelar(@PathVariable Long id) {
        Pedido p = pedidoService.cancelar(id);
        return PedidoMapper.toResponse(p);
    }
}