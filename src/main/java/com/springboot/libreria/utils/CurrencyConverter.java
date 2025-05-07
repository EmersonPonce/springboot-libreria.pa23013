package com.springboot.libreria.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * Utility class for converting currency values between different currencies
 */
@Component
public class CurrencyConverter {
    
    // Exchange rates map with USD as the base currency
    private final Map<String, BigDecimal> exchangeRates;
    
    /**
     * Constructor to initialize the exchange rates
     */
    public CurrencyConverter() {
        // Initialize exchange rates (these could be loaded from an external API in a real app)
        exchangeRates = new HashMap<>();
        exchangeRates.put("USD", BigDecimal.ONE); // Base currency
        exchangeRates.put("EUR", new BigDecimal("0.92")); // 1 USD = 0.92 EUR
        exchangeRates.put("GBP", new BigDecimal("0.79")); // 1 USD = 0.79 GBP
        exchangeRates.put("CAD", new BigDecimal("1.35")); // 1 USD = 1.35 CAD
        exchangeRates.put("MXN", new BigDecimal("16.78")); // 1 USD = 16.78 MXN
    }
    
    /**
     * Converts an amount from one currency to another
     * 
     * @param amount The amount to convert
     * @param fromCurrency The source currency code
     * @param toCurrency The target currency code
     * @return The converted amount in the target currency
     * @throws IllegalArgumentException if any of the currency codes are invalid
     */
    public BigDecimal convert(BigDecimal amount, String fromCurrency, String toCurrency) {
        // Validate currency codes
        validateCurrency(fromCurrency);
        validateCurrency(toCurrency);
        
        if (fromCurrency.equals(toCurrency)) {
            return amount;
        }
        
        // Convert to USD first (if not already USD)
        BigDecimal amountInUSD;
        if (fromCurrency.equals("USD")) {
            amountInUSD = amount;
        } else {
            amountInUSD = amount.divide(exchangeRates.get(fromCurrency), 6, RoundingMode.HALF_UP);
        }
        
        // Convert from USD to target currency
        return amountInUSD.multiply(exchangeRates.get(toCurrency))
                .setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Validates if a currency code is supported
     * 
     * @param currencyCode The currency code to validate
     * @throws IllegalArgumentException if the currency code is not supported
     */
    private void validateCurrency(String currencyCode) {
        if (!exchangeRates.containsKey(currencyCode)) {
            throw new IllegalArgumentException("Currency code not supported: " + currencyCode);
        }
    }
    
    /**
     * Returns all supported currency codes
     * 
     * @return An array of supported currency codes
     */
    public String[] getSupportedCurrencies() {
        return exchangeRates.keySet().toArray(new String[0]);
    }
}