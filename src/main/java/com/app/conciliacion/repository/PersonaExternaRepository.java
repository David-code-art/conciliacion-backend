package com.app.conciliacion.repository;

import com.app.conciliacion.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaExternaRepository extends JpaRepository<PersonaExterna, Long> {
    // Método para buscar si ya existe la persona (para no duplicar registros)
    PersonaExterna findByNumeroDocumento(String numeroDocumento);
}