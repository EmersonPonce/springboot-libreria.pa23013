package com.springboot.libreria.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.libreria.data.entities.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    
    Optional<Autor> findByEmail(String email);
    
    List<Autor> findByNombreContainingIgnoreCase(String nombre);
    
    List<Autor> findByApellidoContainingIgnoreCase(String apellido);
    
    List<Autor> findByNacionalidad(String nacionalidad);
}