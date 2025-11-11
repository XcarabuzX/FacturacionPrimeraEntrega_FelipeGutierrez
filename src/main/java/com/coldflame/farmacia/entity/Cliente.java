package com.coldflame.farmacia.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @OneToMany(mappedBy = "cliente")
    private List<Venta> ventas;

}