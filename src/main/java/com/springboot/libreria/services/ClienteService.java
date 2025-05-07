package com.springboot.libreria.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.libreria.data.dto.ClienteDTO;
import com.springboot.libreria.data.entities.Cliente;
import com.springboot.libreria.data.repository.ClienteRepository;
import com.springboot.libreria.utils.ValidationUtils;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ValidationUtils validationUtils;
    
    @Transactional(readOnly = true)
    public List<ClienteDTO> getAllClientes() {
        return clienteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Optional<ClienteDTO> getClienteById(Long id) {
        return clienteRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    @Transactional(readOnly = true)
    public Optional<ClienteDTO> getClienteByEmail(String email) {
        return clienteRepository.findByEmail(email)
                .map(this::convertToDTO);
    }
    
    @Transactional
    public ClienteDTO createCliente(ClienteDTO clienteDTO) {
        validationUtils.validateEmail(clienteDTO.getEmail());
        
        Cliente cliente = convertToEntity(clienteDTO);
        cliente.setFechaRegistro(LocalDateTime.now());
        cliente.setFechaActualizacion(LocalDateTime.now());
        return convertToDTO(clienteRepository.save(cliente));
    }
    
    @Transactional
    public Optional<ClienteDTO> updateCliente(Long id, ClienteDTO clienteDTO) {
        if (!clienteRepository.existsById(id)) {
            return Optional.empty();
        }
        
        validationUtils.validateEmail(clienteDTO.getEmail());
        
        Cliente cliente = convertToEntity(clienteDTO);
        cliente.setId(id);
        cliente.setFechaActualizacion(LocalDateTime.now());
        return Optional.of(convertToDTO(clienteRepository.save(cliente)));
    }
    
    @Transactional
    public boolean deleteCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            return false;
        }
        clienteRepository.deleteById(id);
        return true;
    }
    
    @Transactional(readOnly = true)
    public List<ClienteDTO> findClientesByNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ClienteDTO> findClientesByApellido(String apellido) {
        return clienteRepository.findByApellidoContainingIgnoreCase(apellido).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ClienteDTO> findClientesByCiudad(String ciudad) {
        return clienteRepository.findByCiudad(ciudad).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ClienteDTO> findClientesByPais(String pais) {
        return clienteRepository.findByPais(pais).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ClienteDTO> findClientesByNombreCompleto(String nombreCompleto) {
        return clienteRepository.findByNombreCompletoContaining(nombreCompleto).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Métodos de conversión
    private ClienteDTO convertToDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        dto.setDireccion(cliente.getDireccion());
        dto.setCiudad(cliente.getCiudad());
        dto.setCodigoPostal(cliente.getCodigoPostal());
        dto.setPais(cliente.getPais());
        dto.setFechaNacimiento(cliente.getFechaNacimiento());
        return dto;
    }
    
    private Cliente convertToEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());
        cliente.setCiudad(dto.getCiudad());
        cliente.setCodigoPostal(dto.getCodigoPostal());
        cliente.setPais(dto.getPais());
        cliente.setFechaNacimiento(dto.getFechaNacimiento());
        return cliente;
    }
}