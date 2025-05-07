package com.springboot.libreria.data.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroDTO {
    
    private Long id;
    private String isbn;
    private String titulo;
    private String sinopsis;
    private BigDecimal precio;
    private Integer stock;
    private String editorial;
    private Integer anioPublicacion;
    private String genero;
    private Integer paginas;
    private Set<AutorDTO> autores = new HashSet<>();
}