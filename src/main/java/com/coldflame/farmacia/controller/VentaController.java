package com.coldflame.farmacia.controller;

import com.coldflame.farmacia.dto.VentaDTO;
import com.coldflame.farmacia.dto.VentaRequestDTO;
import com.coldflame.farmacia.dto.VentaResponseDTO;
import com.coldflame.farmacia.exception.ValidationException;
import com.coldflame.farmacia.responses.ErrorResponse;
import com.coldflame.farmacia.responses.ValidationErrorResponse;
import com.coldflame.farmacia.service.VentaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    //GET /ventas → listar todas las ventas
    @GetMapping
    public ResponseEntity<?> listarVentas() {
        try {
            List<VentaDTO> ventas = ventaService.obtenerVentas();
            return ResponseEntity.ok(ventas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al listar ventas", e.getMessage()));
        }
    }

    //GET /ventas/{id} → obtener una venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerVentaPorId(@PathVariable Long id) {
        try {
            var ventaOpt = ventaService.obtenerVentaPorId(id);
            if (ventaOpt.isPresent()) {
                return ResponseEntity.ok(ventaOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("No encontrada", "La venta con ID " + id + " no existe"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener venta", e.getMessage()));
        }
    }

    // POST /ventas → crear una nueva venta con validaciones
    @PostMapping
    public ResponseEntity<?> crearVenta(@RequestBody VentaRequestDTO ventaRequest) {
        try {
            VentaResponseDTO nuevaVenta = ventaService.crearVenta(ventaRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
        } catch (ValidationException e) {
            // Errores de validación (cliente no existe, stock insuficiente, etc.)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ValidationErrorResponse("Errores de validación", e.getErrors()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al crear venta", e.getMessage()));
        }
    }

    //DELETE /ventas/{id} → eliminar venta
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarVenta(@PathVariable Long id) {
        try {
            ventaService.eliminarVenta(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al eliminar venta", e.getMessage()));
        }
    }
}