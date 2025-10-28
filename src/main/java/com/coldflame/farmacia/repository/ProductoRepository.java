package com.coldflame.farmacia.repository;

import com.coldflame.farmacia.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}