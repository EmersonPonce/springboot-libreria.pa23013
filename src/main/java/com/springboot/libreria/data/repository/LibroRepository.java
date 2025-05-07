package com.springboot.libreria.data.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.libreria.data.entities.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    
    Optional<Libro> findByIsbn(String isbn);
    
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    
    List<Libro> findByGenero(String genero);
    
    List<Libro> findByEditorialIgnoreCase(String editorial);
    
    List<Libro> findByAnioPublicacion(Integer anioPublicacion);
    
    List<Libro> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax);
    
    List<Libro> findByStockGreaterThan(Integer stock);
    
    @Query("SELECT l FROM Libro l JOIN l.autores a WHERE a.id = :autorId")
    List<Libro> findByAutorId(@Param("autorId") Long autorId);
    
    @Query("SELECT l FROM Libro l JOIN l.autores a WHERE CONCAT(a.nombre, ' ', a.apellido) LIKE %:nombreAutor%")
    List<Libro> findByNombreAutor(@Param("nombreAutor") String nombreAutor);
}