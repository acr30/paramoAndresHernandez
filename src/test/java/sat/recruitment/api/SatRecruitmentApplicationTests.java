package sat.recruitment.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SatRecruitmentApplicationTests {

    @Test
    void contextLoads() {
        SatRecruitmentApplication application = new SatRecruitmentApplication();
        assertThat(application).isNotNull();
    }

}