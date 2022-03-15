package pl.sii.upskills.configuration;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sii.upskills.conference.service.model.Money;
import pl.sii.upskills.conference.service.model.TimeSlot;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder ->
                builder.mixIn(TimeSlot.class, TimeSlotMixIn.class)
                        .mixIn(Money.class, MoneyMixIn.class);
    }
}
