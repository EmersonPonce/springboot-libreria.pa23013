package com.springboot.libreria.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.libreria.data.entities.DetalleVenta;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    
    List<DetalleVenta> findByVentaId(Long ventaId);
    
    List<DetalleVenta> findByLibroId(Long libroId);
    
    @Query("SELECT SUM(d.cantidad) FROM DetalleVenta d WHERE d.libro.id = :libroId")
    Integer sumCantidadByLibroId(@Param("libroId") Long libroId);
    
    @Query("SELECT d FROM DetalleVenta d JOIN d.venta v JOIN v.cliente c WHERE c.id = :clienteId")
    List<DetalleVenta> findByClienteId(@Param("clienteId") Long clienteId);
    
    @Query("SELECT d FROM DetalleVenta d JOIN d.libro l WHERE l.titulo LIKE %:tituloLibro%")
    List<DetalleVenta> findByTituloLibro(@Param("tituloLibro") String tituloLibro);
}