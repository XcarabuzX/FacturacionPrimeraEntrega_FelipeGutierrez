package com.coldflame.farmacia.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "precio", nullable = false)
    private Double precio;
    
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @ManyToMany(mappedBy = "productos")
    private List<Venta> ventas;
}