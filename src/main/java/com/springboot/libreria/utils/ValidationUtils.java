package com.springboot.libreria.utils;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class ValidationUtils {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    private static final String ISBN_REGEX = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";
    private static final Pattern ISBN_PATTERN = Pattern.compile(ISBN_REGEX);
    
    private static final String TELEFONO_REGEX = "^\\+?[0-9]{8,15}$";
    private static final Pattern TELEFONO_PATTERN = Pattern.compile(TELEFONO_REGEX);
    
    /**
     * Valida si un correo electrónico tiene formato válido
     * @param email El correo electrónico a validar
     * @throws IllegalArgumentException si el correo no es válido
     */
    public void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico no puede estar vacío");
        }
        
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("El formato del correo electrónico no es válido: " + email);
        }
    }
    
    /**
     * Valida si un ISBN tiene formato válido
     * @param isbn El ISBN a validar
     * @throws IllegalArgumentException si el ISBN no es válido
     */
    public void validateISBN(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío");
        }
        
        if (!ISBN_PATTERN.matcher(isbn).matches()) {
            throw new IllegalArgumentException("El formato del ISBN no es válido: " + isbn);
        }
    }
    
    /**
     * Valida si un número de teléfono tiene formato válido
     * @param telefono El número de teléfono a validar
     * @throws IllegalArgumentException si el teléfono no es válido
     */
    public void validateTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de teléfono no puede estar vacío");
        }
        
        if (!TELEFONO_PATTERN.matcher(telefono).matches()) {
            throw new IllegalArgumentException("El formato del número de teléfono no es válido: " + telefono);
        }
    }
    
    /**
     * Valida que un texto no esté vacío
     * @param texto El texto a validar
     * @param nombreCampo El nombre del campo para el mensaje de error
     * @throws IllegalArgumentException si el texto está vacío
     */
    public void validateRequiredText(String texto, String nombreCampo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " no puede estar vacío");
        }
    }
    
    /**
     * Valida que un número sea positivo
     * @param numero El número a validar
     * @param nombreCampo El nombre del campo para el mensaje de error
     * @throws IllegalArgumentException si el número no es positivo
     */
    public void validatePositiveNumber(Number numero, String nombreCampo) {
        if (numero == null) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " no puede ser nulo");
        }
        
        if (numero.doubleValue() <= 0) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " debe ser un número positivo");
        }
    }
    
    /**
     * Valida que un objeto no sea nulo
     * @param objeto El objeto a validar
     * @param nombreCampo El nombre del campo para el mensaje de error
     * @throws IllegalArgumentException si el objeto es nulo
     */
    public void validateNotNull(Object objeto, String nombreCampo) {
        if (objeto == null) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " no puede ser nulo");
        }
    }
}