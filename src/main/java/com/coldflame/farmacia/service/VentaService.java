package com.coldflame.farmacia.service;

import com.coldflame.farmacia.client.WorldClockApiClient;
import com.coldflame.farmacia.dto.*;
import com.coldflame.farmacia.entity.Cliente;
import com.coldflame.farmacia.entity.LineaVenta;
import com.coldflame.farmacia.entity.Producto;
import com.coldflame.farmacia.entity.Venta;
import com.coldflame.farmacia.exception.ValidationException;
import com.coldflame.farmacia.repository.ClienteRepository;
import com.coldflame.farmacia.repository.LineaVentaRepository;
import com.coldflame.farmacia.repository.ProductoRepository;
import com.coldflame.farmacia.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private LineaVentaRepository lineaVentaRepository;

    @Autowired
    private WorldClockApiClient worldClockApiClient;

    // NUEVO: Crear venta con validaciones completas
    @Transactional
    public VentaResponseDTO crearVenta(VentaRequestDTO request) {
        List<String> errores = new ArrayList<>();

        // 1. VALIDAR CLIENTE
        Long clienteId = request.getCliente().getClienteid();
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);

        if (cliente == null) {
            errores.add("Cliente con ID " + clienteId + " no existe");
        }

        // 2. VALIDAR LÍNEAS (al menos una)
        if (request.getLineas() == null || request.getLineas().isEmpty()) {
            errores.add("Debe incluir al menos una línea de venta");
        }

        // 3. VALIDAR PRODUCTOS Y STOCK
        List<LineaVenta> lineasVenta = new ArrayList<>();
        Double totalVenta = 0.0;
        Integer cantidadTotal = 0;

        if (request.getLineas() != null) {
            for (LineaVentaRequestDTO lineaDTO : request.getLineas()) {
                Long productoId = lineaDTO.getProducto().getProductoid();
                Integer cantidad = lineaDTO.getCantidad();

                // Validar que cantidad sea positiva
                if (cantidad == null || cantidad <= 0) {
                    errores.add("La cantidad debe ser mayor a 0 para producto ID " + productoId);
                    continue;
                }

                // Validar que el producto exista
                Producto producto = productoRepository.findById(productoId).orElse(null);
                if (producto == null) {
                    errores.add("Producto con ID " + productoId + " no existe");
                    continue;
                }

                // Validar stock suficiente
                if (producto.getStock() < cantidad) {
                    errores.add("Stock insuficiente para producto '" + producto.getNombre() +
                            "'. Disponible: " + producto.getStock() + ", solicitado: " + cantidad);
                    continue;
                }

                // Crear línea de venta
                LineaVenta linea = new LineaVenta();
                linea.setProducto(producto);
                linea.setCantidad(cantidad);
                linea.setPrecioUnitario(producto.getPrecio());
                linea.setSubtotal(cantidad * producto.getPrecio());

                lineasVenta.add(linea);
                totalVenta += linea.getSubtotal();
                cantidadTotal += cantidad;
            }
        }

        // 4. SI HAY ERRORES, LANZAR EXCEPCIÓN
        if (!errores.isEmpty()) {
            throw new ValidationException(errores);
        }

        // 5. CREAR VENTA
        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFecha(worldClockApiClient.obtenerFechaActual());
        venta.setTotal(totalVenta);
        venta.setCantidadProductos(cantidadTotal);

        // 6. ASOCIAR LÍNEAS A LA VENTA
        for (LineaVenta linea : lineasVenta) {
            linea.setVenta(venta);
        }
        venta.setLineas(lineasVenta);

        // 7. ACTUALIZAR STOCK DE PRODUCTOS
        for (LineaVenta linea : lineasVenta) {
            Producto producto = linea.getProducto();
            producto.setStock(producto.getStock() - linea.getCantidad());
            productoRepository.save(producto);
        }

        // 8. GUARDAR VENTA (cascade guardará las líneas automáticamente)
        Venta ventaGuardada = ventaRepository.save(venta);

        // 9. MAPEAR A DTO DE RESPUESTA
        return mapToResponseDTO(ventaGuardada);
    }

    // Obtener todas las ventas
    public List<VentaDTO> obtenerVentas() {
        List<Venta> ventas = ventaRepository.findAll();
        return ventas.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Obtener venta por ID
    public Optional<VentaResponseDTO> obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id)
                .map(this::mapToResponseDTO);
    }

    // Eliminar venta
    public void eliminarVenta(Long id) {
        ventaRepository.deleteById(id);
    }

    // MAPEO: Entidad → DTO Response completo
    private VentaResponseDTO mapToResponseDTO(Venta venta) {
        VentaResponseDTO dto = new VentaResponseDTO();
        dto.setId(venta.getId());
        dto.setFecha(venta.getFecha());
        dto.setTotal(venta.getTotal());
        dto.setCantidadProductos(venta.getCantidadProductos());

        // Cliente
        ClienteResponseDTO clienteDTO = new ClienteResponseDTO();
        clienteDTO.setId(venta.getCliente().getId());
        clienteDTO.setNombre(venta.getCliente().getNombre());
        dto.setCliente(clienteDTO);

        // Líneas
        List<LineaVentaResponseDTO> lineasDTO = venta.getLineas().stream()
                .map(this::mapLineaToDTO)
                .collect(Collectors.toList());
        dto.setLineas(lineasDTO);

        return dto;
    }

    // MAPEO: LineaVenta → LineaVentaResponseDTO
    private LineaVentaResponseDTO mapLineaToDTO(LineaVenta linea) {
        LineaVentaResponseDTO dto = new LineaVentaResponseDTO();
        dto.setProductoId(linea.getProducto().getId());
        dto.setProductoNombre(linea.getProducto().getNombre());
        dto.setCantidad(linea.getCantidad());
        dto.setPrecioUnitario(linea.getPrecioUnitario());
        dto.setSubtotal(linea.getSubtotal());
        return dto;
    }

    // MAPEO: Entidad → DTO (para listados simples)
    private VentaDTO mapToDTO(Venta venta) {
        VentaDTO dto = new VentaDTO();
        dto.setId(venta.getId());
        dto.setFecha(venta.getFecha());
        dto.setTotal(venta.getTotal());
        dto.setCantidadProductos(venta.getCantidadProductos());
        dto.setClienteId(venta.getCliente().getId());
        dto.setClienteNombre(venta.getCliente().getNombre());

        List<LineaVentaResponseDTO> lineasDTO = venta.getLineas().stream()
                .map(this::mapLineaToDTO)
                .collect(Collectors.toList());
        dto.setLineas(lineasDTO);

        return dto;
    }
}