package com.app.conciliacion.controller;

import com.app.conciliacion.dto.ExpedienteDTO;
import com.app.conciliacion.model.Expediente;
import com.app.conciliacion.service.ExpedienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expedientes")
public class ExpedienteController {

    @Autowired
    private ExpedienteService expedienteService;

    // --- HU01: REGISTRO DE SOLICITUD (Formato A) ---
    /**
     * Permite al Solicitante registrar una nueva solicitud de conciliación.
     * Mapea al flujo de Mesa de Partes Virtual.
     */
    @PostMapping("/registrar")
    public ResponseEntity<Expediente> registrarSolicitud(@RequestBody ExpedienteDTO dto) {
        try {
            Expediente nuevoExpediente = expedienteService.registrarSolicitud(dto);
            // Retorna 201 Created si el registro fue exitoso
            return new ResponseEntity<>(nuevoExpediente, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Maneja errores de lógica de negocio o de configuración de estados
            System.err.println("Error al registrar solicitud: " + e.getMessage());
            // CORRECCIÓN: Usamos .status().build() para evitar el error de tipo genérico, 
            // manteniendo el código de error 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // --- HU03: REVISIÓN DEL DIRECTOR ---
    
    /**
     * Permite al Director visualizar una solicitud pendiente por su ID.
     */
    @GetMapping("/{idExpediente}")
    public ResponseEntity<Expediente> verExpediente(@PathVariable Long idExpediente) {
        try {
            Expediente expediente = expedienteService.verExpediente(idExpediente);
            return ResponseEntity.ok(expediente);
        } catch (Exception e) {
            // Si el expediente no se encuentra, retorna 404
            return ResponseEntity.notFound().build(); 
        }
    }

    /**
     * Permite al Director aprobar una solicitud (cambia el estado a "Aprobado").
     */
    @PutMapping("/aprobar/{idExpediente}")
    public ResponseEntity<Expediente> aprobarSolicitud(@PathVariable Long idExpediente, @RequestParam Long idDirector) {
        try {
            Expediente expedienteAprobado = expedienteService.aprobarSolicitud(idExpediente, idDirector);
            return ResponseEntity.ok(expedienteAprobado);
        } catch (Exception e) {
            // Si el Expediente o Director no se encuentran, retorna 404
            System.err.println("Error al aprobar: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Permite al Director rechazar una solicitud y registrar observaciones 
     * (cambia el estado a "Pendiente de Subsanación").
     */
    @PutMapping("/rechazar/{idExpediente}")
    public ResponseEntity<Expediente> rechazarSolicitud(
            @PathVariable Long idExpediente, 
            @RequestParam Long idDirector, 
            @RequestParam String motivoObservacion) {
        try {
            Expediente expedienteRechazado = expedienteService.rechazarSolicitud(idExpediente, idDirector, motivoObservacion);
            return ResponseEntity.ok(expedienteRechazado);
        } catch (Exception e) {
            // Si el Expediente o Director no se encuentran, retorna 404
            System.err.println("Error al rechazar: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}