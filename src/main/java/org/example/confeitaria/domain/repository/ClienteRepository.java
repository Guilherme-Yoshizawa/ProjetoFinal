package org.example.confeitaria.domain.repository;

import org.example.confeitaria.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}