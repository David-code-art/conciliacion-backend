package com.app.conciliacion.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "TBL_USUARIO_INTERNO")
public class UsuarioInterno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @ManyToOne
    @JoinColumn(name = "ID_ROL", nullable = false)
    private Rol rol;

    @Column(name = "DNI", unique = true, nullable = false)
    private String dni;
    
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    
    // Simplificado. Agrega más campos si es necesario.
}