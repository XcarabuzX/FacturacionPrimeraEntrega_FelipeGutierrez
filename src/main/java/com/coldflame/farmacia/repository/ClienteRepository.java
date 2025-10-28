package com.coldflame.farmacia.repository;

import com.coldflame.farmacia.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}