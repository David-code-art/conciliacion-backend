package com.app.conciliacion.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "TBL_EXPEDIENTE")
public class Expediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExpediente;

    @Column(name = "NUMERO_EXPEDIENTE", unique = true)
    private String numeroExpediente;

    @OneToOne(cascade = CascadeType.ALL) // Si la persona se crea junto al expediente
    @JoinColumn(name = "ID_SOLICITANTE", nullable = false)
    private PersonaExterna solicitante;

    @OneToOne(cascade = CascadeType.ALL) // Si la persona se crea junto al expediente
    @JoinColumn(name = "ID_INVITADO", nullable = false)
    private PersonaExterna invitado;

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", nullable = false)
    private EstadoExpediente estado; // Estado actual del caso

    @Column(name = "MATERIA_CONCILIAR", nullable = false)
    private String materiaConciliar;

    @Lob 
    @Column(name = "HECHOS_CONFLICTO", nullable = false)
    private String hechosConflicto;

    @Lob
    @Column(name = "PRETENSION", nullable = false)
    private String pretension;
    
    @Column(name = "FECHA_APERTURA")
    private LocalDateTime fechaApertura;

    // Campos de la HU03 (Revisión del Director)
    @ManyToOne
    @JoinColumn(name = "ID_DIRECTOR_APROBACION")
    private UsuarioInterno directorAprobacion; 

    @Column(name = "FECHA_APROBACION")
    private LocalDateTime fechaAprobacion;
}