package org.example.confeitaria.web.controller;

import org.example.confeitaria.domain.model.Cliente;
import org.example.confeitaria.domain.repository.ClienteRepository;
import org.example.confeitaria.web.dto.cliente.ClienteRequest;
import org.example.confeitaria.web.dto.cliente.ClienteResponse;
import org.example.confeitaria.web.exception.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse criar(@Valid @RequestBody ClienteRequest req) {
        Cliente c = new Cliente();
        c.setNome(req.getNome());
        c.setTelefone(req.getTelefone());
        c.setEmail(req.getEmail());
        Cliente salvo = clienteRepository.save(c);
        return toResponse(salvo);
    }

    @GetMapping
    public List<ClienteResponse> listar() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClienteResponse buscar(@PathVariable Long id) {
        Cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
        return toResponse(c);
    }

    @PutMapping("/{id}")
    public ClienteResponse atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequest req) {
        Cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
        c.setNome(req.getNome());
        c.setTelefone(req.getTelefone());
        c.setEmail(req.getEmail());
        Cliente salvo = clienteRepository.save(c);
        return toResponse(salvo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        Cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
        clienteRepository.delete(c);
    }

    private ClienteResponse toResponse(Cliente c) {
        return new ClienteResponse(c.getId(), c.getNome(), c.getTelefone(), c.getEmail());
    }
}