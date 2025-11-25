package com.app.conciliacion.repository;

import com.app.conciliacion.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoExpedienteRepository extends JpaRepository<EstadoExpediente, Long> {
    EstadoExpediente findByNombreEstado(String nombreEstado);
}