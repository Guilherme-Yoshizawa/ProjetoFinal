package org.example.confeitaria.domain.service;

import org.example.confeitaria.domain.enums.StatusPedido;
import org.example.confeitaria.domain.enums.TipoItem;
import org.example.confeitaria.domain.factory.ItemPedidoFactory;
import org.example.confeitaria.domain.model.Cliente;
import org.example.confeitaria.domain.model.ItemPedido;
import org.example.confeitaria.domain.model.Pedido;
import org.example.confeitaria.domain.repository.ClienteRepository;
import org.example.confeitaria.domain.repository.PedidoRepository;
import org.example.confeitaria.web.dto.pedido.ItemPedidoRequest;
import org.example.confeitaria.web.dto.pedido.PedidoRequest;
import org.example.confeitaria.web.exception.NegocioException;
import org.example.confeitaria.web.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ValidacaoService validacaoService;
    private final ItemPedidoFactory itemFactory;

    public PedidoService(PedidoRepository pedidoRepository,
                         ClienteRepository clienteRepository,
                         ValidacaoService validacaoService,
                         PrecoService precoService) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.validacaoService = validacaoService;
        this.itemFactory = new ItemPedidoFactory(precoService);
    }

    @Transactional
    public Pedido criar(PedidoRequest dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setCriadoEm(OffsetDateTime.now(ZoneOffset.UTC));
        pedido.setDataEntrega(dto.getDataEntrega());
        pedido.setStatus(StatusPedido.ABERTO);

        // Itens via Factory
        if (dto.getItens() != null) {
            for (ItemPedidoRequest i : dto.getItens()) {
                ItemPedido item = criarItemViaFactory(i);
                pedido.addItem(item);
            }
        }

        // Validações de negócio
        validacaoService.validarAntecedencia(pedido);
        validacaoService.validarItens(pedido);

        // Totalizar
        recalcularTotal(pedido);

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido atualizar(Long id, PedidoRequest dto) {
        Pedido pedido = buscarPorId(id);

        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new NegocioException("Apenas pedidos ABERTOS podem ser alterados.");
        }

        if (dto.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
            pedido.setCliente(cliente);
        }

        if (dto.getDataEntrega() != null) {
            pedido.setDataEntrega(dto.getDataEntrega());
        }

        // Substitui itens se enviados
        if (dto.getItens() != null) {
            pedido.clearItens();
            for (ItemPedidoRequest i : dto.getItens()) {
                ItemPedido item = criarItemViaFactory(i);
                pedido.addItem(item);
            }
        }

        // Validações e total
        validacaoService.validarAntecedencia(pedido);
        validacaoService.validarItens(pedido);
        recalcularTotal(pedido);

        return pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public Pedido buscarPorId(Long id) {
        Optional<Pedido> opt = pedidoRepository.findById(id);
        return opt.orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    @Transactional
    public void deletar(Long id) {
        Pedido pedido = buscarPorId(id);
        if (pedido.getStatus() == StatusPedido.CONFIRMADO) {
            throw new NegocioException("Não é permitido excluir pedido CONFIRMADO.");
        }
        pedidoRepository.delete(pedido);
    }

    @Transactional
    public Pedido confirmar(Long id) {
        Pedido pedido = buscarPorId(id);
        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new NegocioException("Só é possível confirmar pedidos ABERTOS.");
        }
        // Revalida antes de confirmar
        validacaoService.validarAntecedencia(pedido);
        validacaoService.validarItens(pedido);
        recalcularTotal(pedido);

        pedido.setStatus(StatusPedido.CONFIRMADO);
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido cancelar(Long id) {
        Pedido pedido = buscarPorId(id);
        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            return pedido;
        }
        if (pedido.getStatus() == StatusPedido.CONFIRMADO) {
            // Regra: pode cancelar confirmados? Aqui vou permitir.
            // Se não puder, lance NegocioException.
        }
        pedido.setStatus(StatusPedido.CANCELADO);
        return pedidoRepository.save(pedido);
    }

    private ItemPedido criarItemViaFactory(ItemPedidoRequest i) {
        if (i.getTipo() == null) {
            throw new NegocioException("Tipo do item é obrigatório (BOLO ou DOCINHO).");
        }
        if (i.getTipo().equals(TipoItem.BOLO)) {
            if (i.getPesoKg() == null) {
                throw new NegocioException("Bolo requer pesoKg.");
            }
            return itemFactory.criarBolo(i.getSabor(), i.getPesoKg());
        } else {
            if (i.getQuantidade() == null) {
                throw new NegocioException("Docinho requer quantidade (em unidades).");
            }
            return itemFactory.criarDocinho(i.getSabor(), i.getQuantidade());
        }
    }

    private void recalcularTotal(Pedido pedido) {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedido item : pedido.getItens()) {
            total = total.add(item.getSubtotal());
        }
        pedido.setValorTotal(total);
    }
}