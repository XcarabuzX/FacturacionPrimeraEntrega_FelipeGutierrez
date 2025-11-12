package com.coldflame.farmacia.controller;

import com.coldflame.farmacia.dto.VentaDTO;
import com.coldflame.farmacia.responses.ErrorResponse;
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

    // POST /ventas → crear una nueva venta (usando DTO)
    @PostMapping
    public ResponseEntity<?> crearVenta(@RequestBody VentaDTO ventaDTO) {
        try {
            VentaDTO nuevaVenta = ventaService.crearVenta(ventaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error de validación", e.getMessage()));
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