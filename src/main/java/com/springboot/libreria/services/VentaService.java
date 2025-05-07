package com.springboot.libreria.services;

import com.springboot.libreria.data.dto.DetalleVentaDTO;
import com.springboot.libreria.data.dto.VentaDTO;
import com.springboot.libreria.data.entities.Cliente;
import com.springboot.libreria.data.entities.DetalleVenta;
import com.springboot.libreria.data.entities.Libro;
import com.springboot.libreria.data.entities.Venta;
import com.springboot.libreria.data.repository.ClienteRepository;
import com.springboot.libreria.data.repository.DetalleVentaRepository;
import com.springboot.libreria.data.repository.LibroRepository;
import com.springboot.libreria.data.repository.VentaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;
    private final LibroRepository libroRepository;
    private final DetalleVentaRepository detalleVentaRepository;

    @Autowired
    public VentaService(VentaRepository ventaRepository, 
                         ClienteRepository clienteRepository,
                         LibroRepository libroRepository,
                         DetalleVentaRepository detalleVentaRepository) {
        this.ventaRepository = ventaRepository;
        this.clienteRepository = clienteRepository;
        this.libroRepository = libroRepository;
        this.detalleVentaRepository = detalleVentaRepository;
    }

    @Transactional
    public VentaDTO crearVenta(VentaDTO ventaDTO) {
        Cliente cliente = clienteRepository.findById(ventaDTO.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now()); // Using 'fecha' instead of 'fechaVenta'
        venta.setCliente(cliente);
        venta.setNumeroFactura(ventaDTO.getNumeroFactura());
        venta.setEstado(ventaDTO.getEstado());
        venta.setMetodoPago(ventaDTO.getMetodoPago());
        venta.setNotas(ventaDTO.getNotas());
        venta.setTotal(BigDecimal.ZERO);
        venta.setFechaCreacion(LocalDateTime.now());

        Venta savedVenta = ventaRepository.save(venta);

        BigDecimal total = BigDecimal.ZERO;
        List<DetalleVenta> detalles = new ArrayList<>();

        for (DetalleVentaDTO detalleDTO : ventaDTO.getDetalles()) {
            Libro libro = libroRepository.findById(detalleDTO.getLibroId())
                    .orElseThrow(() -> new EntityNotFoundException("Libro no encontrado"));

            DetalleVenta detalle = new DetalleVenta();
            detalle.setLibro(libro);
            detalle.setVenta(savedVenta);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());
            detalle.setDescuento(detalleDTO.getDescuento());
            detalle.setSubtotal(detalleDTO.getSubtotal());

            detalles.add(detalle);
            total = total.add(detalle.getSubtotal());

            // Update stock
            libro.setStock(libro.getStock() - detalle.getCantidad());
            libroRepository.save(libro);
        }

        savedVenta.setTotal(total);
        ventaRepository.save(savedVenta);
        detalleVentaRepository.saveAll(detalles);

        return convertToDTO(savedVenta);
    }

    public List<VentaDTO> buscarVentasPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        // Using 'fecha' instead of 'fechaVenta'
        List<Venta> ventas = ventaRepository.findByFechaBetween(fechaInicio, fechaFin);
        return ventas.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Map<String, Object> obtenerEstadisticasVentas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        // Using 'fecha' instead of 'fechaVenta'
        List<Venta> ventas = ventaRepository.findByFechaBetween(fechaInicio, fechaFin);
        
        BigDecimal totalVentas = ventas.stream()
                .map(Venta::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        long cantidadVentas = ventas.size();
        
        Map<String, Object> estadisticas = new HashMap<>();
        estadisticas.put("totalVentas", totalVentas);
        estadisticas.put("cantidadVentas", cantidadVentas);
        
        // You can add more statistics calculations here
        
        return estadisticas;
    }
    
    public VentaDTO buscarVentaPorId(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venta no encontrada"));
        return convertToDTO(venta);
    }
    
    private VentaDTO convertToDTO(Venta venta) {
        VentaDTO dto = new VentaDTO();
        dto.setId(venta.getId());
        dto.setClienteId(venta.getCliente().getId());
        dto.setFecha(venta.getFecha()); // Using 'fecha' instead of 'fechaVenta'
        dto.setNumeroFactura(venta.getNumeroFactura());
        dto.setEstado(venta.getEstado());
        dto.setMetodoPago(venta.getMetodoPago());
        dto.setTotal(venta.getTotal());
        dto.setNotas(venta.getNotas());
        
        // Add client name for display purposes
        dto.setNombreCliente(venta.getCliente().getNombre() + " " + venta.getCliente().getApellido());
        
        // Add details
        List<DetalleVentaDTO> detallesDTO = venta.getDetalles().stream()
                .map(detalle -> {
                    DetalleVentaDTO detalleDTO = new DetalleVentaDTO();
                    detalleDTO.setId(detalle.getId());
                    detalleDTO.setLibroId(detalle.getLibro().getId());
                    // Using ventaId instead of setVentaId
                    detalleDTO.setVentaId(detalle.getVenta().getId());
                    detalleDTO.setCantidad(detalle.getCantidad());
                    detalleDTO.setPrecioUnitario(detalle.getPrecioUnitario());
                    detalleDTO.setDescuento(detalle.getDescuento());
                    detalleDTO.setSubtotal(detalle.getSubtotal());
                    detalleDTO.setTituloLibro(detalle.getLibro().getTitulo());
                    return detalleDTO;
                })
                .collect(Collectors.toList());
        
        dto.setDetalles(detallesDTO);
        
        return dto;
    }
}