package com.springboot.libreria.services;

import com.springboot.libreria.data.entities.DetalleVenta;
import com.springboot.libreria.data.entities.Libro;
import com.springboot.libreria.data.entities.Venta;
import com.springboot.libreria.data.repository.DetalleVentaRepository;
import com.springboot.libreria.data.repository.LibroRepository;
import com.springboot.libreria.data.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    
    @Autowired
    private VentaRepository ventaRepository;
    
    @Autowired
    private LibroRepository libroRepository;
    
    @Transactional(readOnly = true)
    public List<DetalleVenta> findAll() {
        return detalleVentaRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<DetalleVenta> findById(Long id) {
        return detalleVentaRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<DetalleVenta> findByVentaId(Long ventaId) {
        return detalleVentaRepository.findByVentaId(ventaId);
    }
    
    @Transactional
    public DetalleVenta save(DetalleVenta detalleVenta) {
        // Calculate subtotal
        BigDecimal precioFinal = detalleVenta.getPrecioUnitario();
        if (detalleVenta.getDescuento() != null && detalleVenta.getDescuento().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal descuentoAmount = detalleVenta.getPrecioUnitario()
                    .multiply(detalleVenta.getDescuento())
                    .divide(new BigDecimal("100"));
            precioFinal = detalleVenta.getPrecioUnitario().subtract(descuentoAmount);
        }
        
        BigDecimal subtotal = precioFinal.multiply(new BigDecimal(detalleVenta.getCantidad()));
        detalleVenta.setSubtotal(subtotal);
        
        // Update stock
        Libro libro = libroRepository.findById(detalleVenta.getLibro().getId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        if (libro.getStock() < detalleVenta.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para el libro: " + libro.getTitulo());
        }
        libro.setStock(libro.getStock() - detalleVenta.getCantidad());
        libroRepository.save(libro);
        
        // Update sale total
        Venta venta = ventaRepository.findById(detalleVenta.getVenta().getId())
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        venta.setTotal(venta.getTotal().add(subtotal));
        ventaRepository.save(venta);
        
        return detalleVentaRepository.save(detalleVenta);
    }
    
    @Transactional
    public void deleteById(Long id) {
        DetalleVenta detalle = detalleVentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de venta no encontrado"));
        
        // Restore stock
        Libro libro = detalle.getLibro();
        libro.setStock(libro.getStock() + detalle.getCantidad());
        libroRepository.save(libro);
        
        // Update sale total
        Venta venta = detalle.getVenta();
        venta.setTotal(venta.getTotal().subtract(detalle.getSubtotal()));
        ventaRepository.save(venta);
        
        detalleVentaRepository.deleteById(id);
    }
}