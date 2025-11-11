package com.coldflame.farmacia.controller;

import com.coldflame.farmacia.entity.Venta;
import com.coldflame.farmacia.repository.VentaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final VentaRepository ventaRepository;

    public VentaController(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    // GET /ventas → listar todas las ventas
    @GetMapping
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    // POST /ventas → crear una nueva venta
    @PostMapping
    public Venta crearVenta(@RequestBody Venta venta) {
        return ventaRepository.save(venta);
    }
}