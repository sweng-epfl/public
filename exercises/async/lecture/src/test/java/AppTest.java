import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

class AppTest {
    // TODO: Fill in the following tests.
    //       DO NOT use Thread.sleep or such methods;
    //       your tests should succeed if and only if the async code finishes in reasonable time.

    @Test
    public void exactAnswerTest() {
        // 8. getExactAnswer() should return 42 (do not change getExactAnswer)
    }

    @Test
    public void clickButtonTest() {
        // 9. clickButton should set buttonWasClicked to true (do not change clickButton)
    }

    @Test
    public void printAnswersTest() {
        // 10. printAnswers should print the exact and approximate answers, 42 and 40, in any order
        //     You'll need to change printAnswers so it's testable;
        //     but keep the logic of turning the answers to strings in printAnswers.
    }
}
