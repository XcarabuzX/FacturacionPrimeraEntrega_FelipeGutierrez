package com.coldflame.farmacia.service;

import com.coldflame.farmacia.dto.VentaDTO;
import com.coldflame.farmacia.entity.Cliente;
import com.coldflame.farmacia.entity.Producto;
import com.coldflame.farmacia.entity.Venta;
import com.coldflame.farmacia.repository.ClienteRepository;
import com.coldflame.farmacia.repository.ProductoRepository;
import com.coldflame.farmacia.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // Crear venta
    public VentaDTO crearVenta(VentaDTO ventaDTO) {
        Cliente cliente = clienteRepository.findById(ventaDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        List<Producto> productos = productoRepository.findAllById(ventaDTO.getProductoIds());

        Venta venta = new Venta();
        venta.setFecha(ventaDTO.getFecha());
        venta.setTotal(ventaDTO.getTotal());
        venta.setCliente(cliente);
        venta.setProductos(productos);

        Venta guardada = ventaRepository.save(venta);

        return mapToDTO(guardada);
    }

    // Obtener todas las ventas
    public List<VentaDTO> obtenerVentas() {
        List<Venta> ventas = ventaRepository.findAll();
        return ventas.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Obtener venta por ID
    public Optional<VentaDTO> obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id)
                .map(this::mapToDTO);
    }

    // Eliminar venta
    public void eliminarVenta(Long id) {
        ventaRepository.deleteById(id);
    }

    // Mapear Entidad → DTO
    private VentaDTO mapToDTO(Venta venta) {
        VentaDTO dto = new VentaDTO();
        dto.setId(venta.getId());
        dto.setFecha(venta.getFecha());
        dto.setTotal(venta.getTotal());
        dto.setClienteId(venta.getCliente().getId());
        dto.setProductoIds(
                venta.getProductos().stream()
                        .map(Producto::getId)
                        .collect(Collectors.toList())
        );
        return dto;
    }
}