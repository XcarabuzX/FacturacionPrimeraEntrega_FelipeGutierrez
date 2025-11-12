package com.coldflame.farmacia.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class VentaDTO {

    private Long id;
    private LocalDate fecha;
    private Double total;

    private Long clienteId;
    private List<Long> productoIds;
}