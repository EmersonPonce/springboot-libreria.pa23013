package com.springboot.libreria.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import org.springframework.stereotype.Component;

@Component
public class DateTimeUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter PRETTY_DATE_FORMATTER = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
    
    /**
     * Formatea una fecha en formato dd/MM/yyyy
     * @param date La fecha a formatear
     * @return La fecha formateada como String
     */
    public String formatDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(DATE_FORMATTER);
    }
    
    /**
     * Formatea una fecha y hora en formato dd/MM/yyyy HH:mm:ss
     * @param dateTime La fecha y hora a formatear
     * @return La fecha y hora formateada como String
     */
    public String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DATE_TIME_FORMATTER);
    }
    
    /**
     * Formatea una fecha en formato bonito (ejemplo: 15 de enero de 2023)
     * @param date La fecha a formatear
     * @return La fecha formateada como String
     */
    public String formatPrettyDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(PRETTY_DATE_FORMATTER);
    }
    
    /**
     * Convierte un String en formato dd/MM/yyyy a LocalDate
     * @param dateStr El String de fecha
     * @return La fecha convertida a LocalDate
     * @throws DateTimeParseException si el formato no es válido
     */
    public LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }
    
    /**
     * Convierte un String en formato dd/MM/yyyy HH:mm:ss a LocalDateTime
     * @param dateTimeStr El String de fecha y hora
     * @return La fecha y hora convertida a LocalDateTime
     * @throws DateTimeParseException si el formato no es válido
     */
    public LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
    }
    
    /**
     * Calcula la edad en años a partir de una fecha de nacimiento
     * @param fechaNacimiento La fecha de nacimiento
     * @return La edad en años
     */
    public int calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula");
        }
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
    
    /**
     * Obtiene el primer día del mes actual
     * @return El primer día del mes actual
     */
    public LocalDate getPrimerDiaMesActual() {
        return LocalDate.now().withDayOfMonth(1);
    }
    
    /**
     * Obtiene el último día del mes actual
     * @return El último día del mes actual
     */
    public LocalDate getUltimoDiaMesActual() {
        LocalDate hoy = LocalDate.now();
        return hoy.withDayOfMonth(hoy.getMonth().length(hoy.isLeapYear()));
    }
    
    /**
     * Obtiene el inicio del día para una fecha dada
     * @param fecha La fecha
     * @return La fecha con hora 00:00:00
     */
    public LocalDateTime getInicioDia(LocalDate fecha) {
        if (fecha == null) {
            return null;
        }
        return fecha.atStartOfDay();
    }
    
    /**
     * Obtiene el fin del día para una fecha dada
     * @param fecha La fecha
     * @return La fecha con hora 23:59:59
     */
    public LocalDateTime getFinDia(LocalDate fecha) {
        if (fecha == null) {
            return null;
        }
        return fecha.atTime(23, 59, 59);
    }
}