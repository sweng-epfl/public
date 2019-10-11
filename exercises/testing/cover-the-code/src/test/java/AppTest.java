import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

final class AppTest {
    @Test
    void noSpeedChangeUnderStandardConditions() {
        assertThat(App.getEngineSpeed(120, 20, false), is(120));
    }
}
