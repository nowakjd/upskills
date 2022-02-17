package pl.sii.upskills.conference.persistence;

import pl.sii.upskills.conference.service.model.Money;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoneyVO moneyVO = (MoneyVO) o;

        if (!Objects.equals(amount, moneyVO.amount)) return false;
        return Objects.equals(currency, moneyVO.currency);
    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }
}
