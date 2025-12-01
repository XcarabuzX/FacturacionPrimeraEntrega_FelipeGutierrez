package com.coldflame.farmacia.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaRequestDTO {
    private ClienteIdDTO cliente;
    private List<LineaVentaRequestDTO> lineas;
}
