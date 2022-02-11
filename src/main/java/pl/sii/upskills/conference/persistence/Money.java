package pl.sii.upskills.conference.persistence;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Currency;


@Embeddable
public class Money {
    private BigDecimal amount;
    @Size(max = 3)
    private Currency currency;
}
