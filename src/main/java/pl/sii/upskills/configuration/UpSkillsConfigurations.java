package pl.sii.upskills.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sii.upskills.common.TimeService;

import java.time.LocalDateTime;

@Configuration
class UpSkillsConfigurations {

    @Bean
    TimeService getTimeService() {
        return LocalDateTime::now;
        /*
        return () -> LocalDateTime.now();
        return new TimeService() {
            @Override
            public LocalDateTime get() {
                return LocalDateTime.now();
            }
        };
        W celach edukacyjnych.
        */
    }
}
