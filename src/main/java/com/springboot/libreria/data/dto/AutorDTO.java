package com.springboot.libreria.data.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorDTO {
    
    private Long id;
    private String nombre;
    private String apellido;
    private String biografia;
    private LocalDateTime fechaNacimiento;
    private String nacionalidad;
    private String email;
}