package com.coldflame.farmacia.service;

import com.coldflame.farmacia.dto.ProductoDTO;
import com.coldflame.farmacia.entity.Producto;
import com.coldflame.farmacia.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Crear producto
    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        Producto producto = dtoToEntity(productoDTO);
        Producto productoGuardado = productoRepository.save(producto);
        return entityToDTO(productoGuardado);
    }

    // Obtener todos los productos
    public List<ProductoDTO> obtenerTodosProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    // Obtener producto por ID
    public ProductoDTO obtenerProductoPorId(Long id) {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        return productoOptional.map(this::entityToDTO).orElse(null);
    }

    // Eliminar producto por ID
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    //Conversión Entity → DTO
    private ProductoDTO entityToDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setStock(producto.getStock());
        dto.setPrecio(producto.getPrecio());
        return dto;
    }

    //Conversión DTO → Entity
    private Producto dtoToEntity(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setStock(dto.getStock());
        producto.setPrecio(dto.getPrecio());
        return producto;
    }
}