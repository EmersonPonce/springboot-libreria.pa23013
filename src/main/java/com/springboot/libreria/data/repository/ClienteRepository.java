package com.springboot.libreria.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.libreria.data.entities.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByEmail(String email);
    
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
    
    List<Cliente> findByApellidoContainingIgnoreCase(String apellido);
    
    List<Cliente> findByCiudad(String ciudad);
    
    List<Cliente> findByPais(String pais);
    
    @Query("SELECT c FROM Cliente c WHERE CONCAT(c.nombre, ' ', c.apellido) LIKE %?1%")
    List<Cliente> findByNombreCompletoContaining(String nombreCompleto);
}