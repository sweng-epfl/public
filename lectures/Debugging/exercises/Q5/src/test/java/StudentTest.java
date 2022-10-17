import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class StudentTest {

  @Test
  public void testAddition() {
    assertThat(1 + 1, Matchers.is(2));
  }
}
