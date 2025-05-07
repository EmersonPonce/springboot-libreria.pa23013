package com.springboot.libreria.data.repository;

import com.springboot.libreria.data.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    
    // Find a sale by its invoice number
    Optional<Venta> findByNumeroFactura(String numeroFactura);
    
    // Find sales by customer ID
    List<Venta> findByClienteId(Long clienteId);
    
    // Find sales by status
    List<Venta> findByEstado(String estado);
    
    // Find sales in a date range
    List<Venta> findByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    // Find sales by payment method
    List<Venta> findByMetodoPago(String metodoPago);
    
    // Optional: Find sales with total amount greater than a value
    List<Venta> findByTotalGreaterThan(Double total);
}