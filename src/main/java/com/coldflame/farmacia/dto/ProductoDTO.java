package com.coldflame.farmacia.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private int stock;
    private double precio;
}