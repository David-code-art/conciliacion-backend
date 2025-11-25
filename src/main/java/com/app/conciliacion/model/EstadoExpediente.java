package com.app.conciliacion.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "TBL_ESTADO_EXPEDIENTE")
public class EstadoExpediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstado;

    @Column(name = "NOMBRE_ESTADO", nullable = false)
    private String nombreEstado;
}