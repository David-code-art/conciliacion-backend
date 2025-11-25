package com.app.conciliacion.repository;

import com.app.conciliacion.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpedienteRepository extends JpaRepository<Expediente, Long> {
    // Métodos personalizados aquí, si fueran necesarios
}