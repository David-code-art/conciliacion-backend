package com.app.conciliacion.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "TBL_PERSONA_EXTERNA")
public class PersonaExterna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersona;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_DOCUMENTO", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(name = "NUMERO_DOCUMENTO", unique = true, nullable = false)
    private String numeroDocumento;

    @Column(name = "NOMBRE_RAZON_SOCIAL", nullable = false)
    private String nombreRazonSocial;

    @Column(name = "DOMICILIO", nullable = false)
    private String domicilio;

    @Column(name = "CORREO")
    private String correo;

    @Column(name = "TELEFONO")
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_PERSONA", nullable = false)
    private TipoPersona tipoPersona;

    public enum TipoDocumento { DNI, RUC }
    public enum TipoPersona { NATURAL, JURIDICA }
}