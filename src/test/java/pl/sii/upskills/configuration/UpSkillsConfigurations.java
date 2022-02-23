package pl.sii.upskills.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import pl.sii.upskills.common.TimeService;

import java.time.LocalDateTime;

@TestConfiguration
class UpSkillsConfigurations {

    @Bean
    TimeService getTimeService() {
        return () -> LocalDateTime.of(2025, 2, 2, 12, 34, 56);
    }
}
