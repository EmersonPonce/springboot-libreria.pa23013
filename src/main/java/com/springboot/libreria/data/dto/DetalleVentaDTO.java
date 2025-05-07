package com.springboot.libreria.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class DetalleVentaDTO {
    private Long id;
    private Long libroId;
    private Long ventaId; // Nuevo campo para almacenar el ID de la venta
    private String tituloLibro; // Para mostrar información del libro
    private String isbn; // Para referencia del libro
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal descuento;
    private BigDecimal subtotal;

    // Constructor con parámetros útiles para creación rápida
    public DetalleVentaDTO(Long libroId, Integer cantidad, BigDecimal precioUnitario, BigDecimal descuento) {
        this.libroId = libroId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento;
    }

    // Constructor completo para mapeo de entidad a DTO
    public DetalleVentaDTO(Long id, Long libroId, Long ventaId, String tituloLibro, String isbn, Integer cantidad, BigDecimal precioUnitario, BigDecimal descuento, BigDecimal subtotal) {
        this.id = id;
        this.libroId = libroId;
        this.ventaId = ventaId;
        this.tituloLibro = tituloLibro;
        this.isbn = isbn;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento;
        this.subtotal = subtotal;
    }
}