package com.springboot.libreria.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.libreria.data.dto.AutorDTO;
import com.springboot.libreria.data.entities.Autor;
import com.springboot.libreria.data.repository.AutorRepository;
import com.springboot.libreria.utils.ValidationUtils;

@Service
public class AutorService {
    
    @Autowired
    private AutorRepository autorRepository;
    
    @Autowired
    private ValidationUtils validationUtils;
    
    @Transactional(readOnly = true)
    public List<AutorDTO> getAllAutores() {
        return autorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Optional<AutorDTO> getAutorById(Long id) {
        return autorRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    @Transactional(readOnly = true)
    public Optional<AutorDTO> getAutorByEmail(String email) {
        return autorRepository.findByEmail(email)
                .map(this::convertToDTO);
    }
    
    @Transactional
    public AutorDTO createAutor(AutorDTO autorDTO) {
        validationUtils.validateEmail(autorDTO.getEmail());
        
        Autor autor = convertToEntity(autorDTO);
        autor.setFechaCreacion(LocalDateTime.now());
        return convertToDTO(autorRepository.save(autor));
    }
    
    @Transactional
    public Optional<AutorDTO> updateAutor(Long id, AutorDTO autorDTO) {
        if (!autorRepository.existsById(id)) {
            return Optional.empty();
        }
        
        validationUtils.validateEmail(autorDTO.getEmail());
        
        Autor autor = convertToEntity(autorDTO);
        autor.setId(id);
        autor.setFechaActualizacion(LocalDateTime.now());
        return Optional.of(convertToDTO(autorRepository.save(autor)));
    }
    
    @Transactional
    public boolean deleteAutor(Long id) {
        if (!autorRepository.existsById(id)) {
            return false;
        }
        autorRepository.deleteById(id);
        return true;
    }
    
    @Transactional(readOnly = true)
    public List<AutorDTO> findAutoresByNombre(String nombre) {
        return autorRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AutorDTO> findAutoresByApellido(String apellido) {
        return autorRepository.findByApellidoContainingIgnoreCase(apellido).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AutorDTO> findAutoresByNacionalidad(String nacionalidad) {
        return autorRepository.findByNacionalidad(nacionalidad).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Métodos de conversión
    private AutorDTO convertToDTO(Autor autor) {
        AutorDTO dto = new AutorDTO();
        dto.setId(autor.getId());
        dto.setNombre(autor.getNombre());
        dto.setApellido(autor.getApellido());
        dto.setBiografia(autor.getBiografia());
        dto.setFechaNacimiento(autor.getFechaNacimiento());
        dto.setNacionalidad(autor.getNacionalidad());
        dto.setEmail(autor.getEmail());
        return dto;
    }
    
    private Autor convertToEntity(AutorDTO dto) {
        Autor autor = new Autor();
        autor.setNombre(dto.getNombre());
        autor.setApellido(dto.getApellido());
        autor.setBiografia(dto.getBiografia());
        autor.setFechaNacimiento(dto.getFechaNacimiento());
        autor.setNacionalidad(dto.getNacionalidad());
        autor.setEmail(dto.getEmail());
        return autor;
    }
}