package com.app.conciliacion.dto;

import com.app.conciliacion.model.PersonaExterna;
import lombok.Data;

@Data
public class ExpedienteDTO {
    // Datos del Solicitante
    private PersonaExterna solicitante; 
    
    // Datos del Invitado
    private PersonaExterna invitado;

    // Datos del Formato A
    private String materiaConciliar;
    private String hechosConflicto;
    private String pretension;
    
    // IDs adicionales para la revisión (HU03)
    private Long idDirector;
    private Long idExpediente; // Necesario para Aprobar/Rechazar
    private String motivoObservacion; // Necesario para Rechazar
}