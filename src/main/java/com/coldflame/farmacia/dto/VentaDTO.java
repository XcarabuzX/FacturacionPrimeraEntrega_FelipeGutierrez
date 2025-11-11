package com.coldflame.farmacia.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class VentaDTO {
    private Long id;
    private LocalDate fecha;
    private Double total;
    private Long clienteId;
    private List<Long> productoIds; 
}