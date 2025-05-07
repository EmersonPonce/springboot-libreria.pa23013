package com.springboot.libreria.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class VentaDTO {
    
    private Long id;
    private String numeroFactura;
    private Long clienteId;
    private String nombreCliente; // For display purposes
    private LocalDateTime fecha; // Use 'fecha' instead of 'fechaVenta'
    private BigDecimal total;
    private String estado;
    private String metodoPago;
    private String notas;
    private List<DetalleVentaDTO> detalles = new ArrayList<>();
    
    // Constructor básico
    public VentaDTO(Long id, String numeroFactura, Long clienteId, LocalDateTime fecha, 
                   BigDecimal total, String estado, String metodoPago) {
        this.id = id;
        this.numeroFactura = numeroFactura;
        this.clienteId = clienteId;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.metodoPago = metodoPago;
    }
    
    // Constructor completo
    public VentaDTO(Long id, String numeroFactura, Long clienteId, String nombreCliente,
                   LocalDateTime fecha, BigDecimal total, String estado, 
                   String metodoPago, String notas) {
        this.id = id;
        this.numeroFactura = numeroFactura;
        this.clienteId = clienteId;
        this.nombreCliente = nombreCliente;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.notas = notas;
    }
    
    // Método para agregar un detalle a la venta
    public void addDetalle(DetalleVentaDTO detalle) {
        this.detalles.add(detalle);
    }
    
    // Método para calcular el total en base a los detalles
    public void calcularTotal() {
        this.total = detalles.stream()
            .map(DetalleVentaDTO::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}