package pl.sii.upskills.conference.service.model;


import java.math.BigDecimal;
import java.util.Currency;

/**
 * Representation of monetary value.
 */
public interface Money {

    Currency getCurrency();

    BigDecimal getAmount();
}
