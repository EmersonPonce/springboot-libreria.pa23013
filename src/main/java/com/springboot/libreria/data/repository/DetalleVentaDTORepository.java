package com.springboot.libreria.data.repository;

import com.springboot.libreria.data.dto.DetalleVentaDTO;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DetalleVentaDTORepository {

    private final JdbcTemplate jdbcTemplate;
    
    public DetalleVentaDTORepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private RowMapper<DetalleVentaDTO> detalleVentaRowMapper = (ResultSet rs, int rowNum) -> {
        DetalleVentaDTO detalleDTO = new DetalleVentaDTO();
        detalleDTO.setId(rs.getLong("id"));
        detalleDTO.setLibroId(rs.getLong("libro_id"));
        detalleDTO.setTituloLibro(rs.getString("titulo_libro"));
        detalleDTO.setIsbn(rs.getString("isbn"));
        detalleDTO.setCantidad(rs.getInt("cantidad"));
        detalleDTO.setPrecioUnitario(rs.getBigDecimal("precio_unitario"));
        detalleDTO.setDescuento(rs.getBigDecimal("descuento"));
        detalleDTO.setSubtotal(rs.getBigDecimal("subtotal"));
        return detalleDTO;
    };
    
    public List<DetalleVentaDTO> findByVentaId(Long ventaId) {
        String sql = "SELECT dv.id, dv.libro_id, l.titulo as titulo_libro, l.isbn, " +
                     "dv.cantidad, dv.precio_unitario, dv.descuento, dv.subtotal " +
                     "FROM detalles_venta dv " +
                     "JOIN libros l ON dv.libro_id = l.id " +
                     "WHERE dv.venta_id = ?";
        return jdbcTemplate.query(sql, detalleVentaRowMapper, ventaId);
    }
    
    public DetalleVentaDTO findById(Long id) {
        String sql = "SELECT dv.id, dv.libro_id, l.titulo as titulo_libro, l.isbn, " +
                     "dv.cantidad, dv.precio_unitario, dv.descuento, dv.subtotal " +
                     "FROM detalles_venta dv " +
                     "JOIN libros l ON dv.libro_id = l.id " +
                     "WHERE dv.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, detalleVentaRowMapper, id);
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<DetalleVentaDTO> findByLibroId(Long libroId) {
        String sql = "SELECT dv.id, dv.libro_id, l.titulo as titulo_libro, l.isbn, " +
                     "dv.cantidad, dv.precio_unitario, dv.descuento, dv.subtotal " +
                     "FROM detalles_venta dv " +
                     "JOIN libros l ON dv.libro_id = l.id " +
                     "WHERE dv.libro_id = ?";
        return jdbcTemplate.query(sql, detalleVentaRowMapper, libroId);
    }
}