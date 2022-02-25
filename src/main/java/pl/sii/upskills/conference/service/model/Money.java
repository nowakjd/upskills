package pl.sii.upskills.conference.service.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.sii.upskills.conference.persistence.MoneyVO;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Representation of monetary value.
 */
@JsonDeserialize(as = MoneyVO.class)
public interface Money {

    Currency getCurrency();

    BigDecimal getAmount();
}
