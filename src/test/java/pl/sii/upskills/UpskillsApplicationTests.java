package pl.sii.upskills;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sii.upskills.common.TimeService;

@SpringBootTest
class UpskillsApplicationTests {

    @Autowired
    TimeService timeService;

    @Test
    void contextLoads() {
        Assertions.assertThat(timeService.get().getYear()).isEqualTo(2022);
    }

}
