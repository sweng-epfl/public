import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

final class AppTest {
    @Test
    void onePlusOneIsTwo() {
        assertThat(1 + 1, is(2));
    }
}
