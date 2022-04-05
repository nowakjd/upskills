package pl.sii.upskills.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import pl.sii.upskills.broker.ConferenceBroker;
import pl.sii.upskills.common.TimeService;

import java.time.LocalDateTime;

@TestConfiguration
class UpSkillsConfigurations {

    @Bean
    TimeService getTimeService() {
        return () -> LocalDateTime.of(2022, 1, 27, 12, 34, 56);
    }

    @Bean
    @Primary
    ConferenceBroker getConferenceBroker() {
        return (message) -> message;
    }
}
