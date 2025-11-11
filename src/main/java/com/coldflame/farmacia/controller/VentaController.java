package com.coldflame.farmacia.controller;

import com.coldflame.farmacia.entity.Venta;
import com.coldflame.farmacia.repository.VentaRepository;
import com.coldflame.farmacia.responses.ErrorResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final VentaRepository ventaRepository;

    public VentaController(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @GetMapping
    public ResponseEntity<?> listarVentas() {
        try {
            List<Venta> ventas = ventaRepository.findAll();
            return ResponseEntity.ok(ventas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al listar ventas", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> crearVenta(@RequestBody Venta venta) {
        try {
            Venta nueva = ventaRepository.save(venta);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al crear venta", e.getMessage()));
        }
    }
}