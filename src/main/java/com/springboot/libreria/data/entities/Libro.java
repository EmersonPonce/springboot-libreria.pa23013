package com.springboot.libreria.data.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "libros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "isbn", unique = true, nullable = false, length = 20)
    private String isbn;
    
    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;
    
    @Column(name = "sinopsis", columnDefinition = "TEXT")
    private String sinopsis;
    
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    
    @Column(name = "stock", nullable = false)
    private Integer stock;
    
    @Column(name = "editorial", length = 100)
    private String editorial;
    
    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;
    
    @Column(name = "genero", length = 50)
    private String genero;
    
    @Column(name = "paginas")
    private Integer paginas;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "libro_autor",
        joinColumns = @JoinColumn(name = "libro_id"),
        inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autores = new HashSet<>();
    
    @OneToMany(mappedBy = "libro")
    private Set<DetalleVenta> detallesVenta = new HashSet<>();
    
    // Métodos para manejar relaciones
    public void addAutor(Autor autor) {
        this.autores.add(autor);
        autor.getLibros().add(this);
    }
    
    public void removeAutor(Autor autor) {
        this.autores.remove(autor);
        autor.getLibros().remove(this);
    }
}