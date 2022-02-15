package pl.sii.upskills.conference.persistence;

import pl.sii.upskills.conference.service.model.Money;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Currency;


@Embeddable
public class MoneyVO implements Money {
    private BigDecimal amount;
    @Size(max = 3)
    private Currency currency;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
