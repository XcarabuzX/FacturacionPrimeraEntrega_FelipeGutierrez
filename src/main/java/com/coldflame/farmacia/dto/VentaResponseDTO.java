package com.coldflame.farmacia.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaResponseDTO {
    private Long id;
    private String fecha;
    private Double total;
    private Integer cantidadProductos;
    private ClienteResponseDTO cliente;
    private List<LineaVentaResponseDTO> lineas;
}
