package com.app.conciliacion.repository;

import com.app.conciliacion.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioInternoRepository extends JpaRepository<UsuarioInterno, Long> {
    // Para buscar al director por su ID
}