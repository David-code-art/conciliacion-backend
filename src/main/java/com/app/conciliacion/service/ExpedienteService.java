package com.app.conciliacion.service;

import com.app.conciliacion.dto.ExpedienteDTO;
import com.app.conciliacion.model.*;
import com.app.conciliacion.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExpedienteService {

    @Autowired
    private ExpedienteRepository expedienteRepository;

    @Autowired
    private PersonaExternaRepository personaExternaRepository;

    @Autowired
    private EstadoExpedienteRepository estadoRepository;

    @Autowired
    private UsuarioInternoRepository usuarioRepository;

    // --- HU01: REGISTRO DE SOLICITUD (Formato A) ---
    @Transactional
    public Expediente registrarSolicitud(ExpedienteDTO dto) {
        
        // 1. Obtener el estado inicial: "Pendiente de Revisión"
        EstadoExpediente estadoInicial = estadoRepository.findByNombreEstado("Pendiente de Revisión");
        if (estadoInicial == null) {
            // Este es un error de configuración de la BD (debes insertarlo manualmente)
            throw new RuntimeException("Error de configuración: El estado 'Pendiente de Revisión' no existe.");
        }
        
        // 2. Gestionar Solicitante e Invitado: Evitar duplicidad (si ya existe, usar el registro actual)
        PersonaExterna solicitante = getOrCreatePersonaExterna(dto.getSolicitante());
        PersonaExterna invitado = getOrCreatePersonaExterna(dto.getInvitado());

        // 3. Crear el nuevo Expediente
        Expediente nuevoExpediente = new Expediente();
        
        // Asignar personas
        nuevoExpediente.setSolicitante(solicitante);
        nuevoExpediente.setInvitado(invitado);
        
        // Asignar datos del Formato A
        nuevoExpediente.setMateriaConciliar(dto.getMateriaConciliar());
        nuevoExpediente.setHechosConflicto(dto.getHechosConflicto());
        nuevoExpediente.setPretension(dto.getPretension());
        
        // Asignar estado y fecha
        nuevoExpediente.setEstado(estadoInicial);
        nuevoExpediente.setFechaApertura(LocalDateTime.now());
        
        // El campo NUMERO_EXPEDIENTE se generará en HU04, por ahora se guarda la referencia temporal
        
        return expedienteRepository.save(nuevoExpediente);
    }

    /**
     * Lógica auxiliar para buscar una persona por DNI/RUC.
     * Si no existe, la guarda; si existe, la retorna.
     */
    private PersonaExterna getOrCreatePersonaExterna(PersonaExterna persona) {
        PersonaExterna existente = personaExternaRepository.findByNumeroDocumento(persona.getNumeroDocumento());
        if (existente != null) {
            return existente;
        }
        // Si no existe, guarda el nuevo registro
        return personaExternaRepository.save(persona);
    }
    
    // Método para simular la búsqueda de expedientes pendientes para el Director
    public List<Expediente> buscarPendientesRevision() {
        // En un sistema real, buscarías por el ID del estado "Pendiente de Revisión"
        return expedienteRepository.findAll(); // Implementación simplificada
    }


    // --- HU03: REVISIÓN DEL DIRECTOR ---
    
    /**
     * Revisa una solicitud y la aprueba para Designación de Conciliador.
     */
    @Transactional
    public Expediente aprobarSolicitud(Long idExpediente, Long idDirector) throws Exception {
        Expediente expediente = expedienteRepository.findById(idExpediente)
                .orElseThrow(() -> new Exception("Expediente N° " + idExpediente + " no encontrado."));

        UsuarioInterno director = usuarioRepository.findById(idDirector)
                .orElseThrow(() -> new Exception("Director con ID " + idDirector + " no encontrado."));
        
        // 1. Obtener el estado final: "Aprobado"
        EstadoExpediente estadoAprobado = estadoRepository.findByNombreEstado("Aprobado");
        if (estadoAprobado == null) {
             throw new RuntimeException("Error de configuración: El estado 'Aprobado' no existe.");
        }

        // 2. Tarea de la HU03: Cambiar estado
        expediente.setEstado(estadoAprobado); 
        expediente.setDirectorAprobacion(director);
        expediente.setFechaAprobacion(LocalDateTime.now());
        
        // **Falta Tarea:** Aquí se integraría la Notificación al Solicitante (HU21) y el Registro de Auditoría
        
        return expedienteRepository.save(expediente);
    }
    
    /**
     * Revisa una solicitud y la rechaza (observa) para que el solicitante subsane.
     */
    @Transactional
    public Expediente rechazarSolicitud(Long idExpediente, Long idDirector, String motivoObservacion) throws Exception {
        Expediente expediente = expedienteRepository.findById(idExpediente)
                .orElseThrow(() -> new Exception("Expediente N° " + idExpediente + " no encontrado."));

        UsuarioInterno director = usuarioRepository.findById(idDirector)
                .orElseThrow(() -> new Exception("Director con ID " + idDirector + " no encontrado."));
        
        // 1. Obtener el estado final: "Rechazado" o "Pendiente de Subsanación"
        EstadoExpediente estadoRechazado = estadoRepository.findByNombreEstado("Pendiente de Subsanación");
        if (estadoRechazado == null) {
             throw new RuntimeException("Error de configuración: El estado 'Pendiente de Subsanación' no existe.");
        }
        
        // 2. Tarea de la HU03: Cambiar estado
        expediente.setEstado(estadoRechazado); 
        expediente.setDirectorAprobacion(director);
        expediente.setFechaAprobacion(LocalDateTime.now()); // Se registra el momento del rechazo

        // **Falta Tarea:** Aquí se integraría la creación del registro en TBL_OBSERVACION
        // **Falta Tarea:** Aquí se integraría la Notificación al Solicitante (HU21)
        
        return expedienteRepository.save(expediente);
    }
    
    // Método auxiliar para el controlador (ver detalles de un expediente)
    public Expediente verExpediente(Long idExpediente) throws Exception {
        return expedienteRepository.findById(idExpediente)
                 .orElseThrow(() -> new Exception("Expediente no encontrado."));
    }
}