import grading.GradedCategory;
import grading.GradedTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@GradedCategory("Practice Question 1")
final class GradedAppViewTests {
    // Do not allow leading/trailing whitespace here, only CRLF/LF, the entire point of the exercise is to have precise output

    @GradedTest("AppView::toString returns the description, then one command per line as per its specification")
    void t00_withCommands() {
        AppView view = new AppView("example description", GradedTestsUtils.list("first", "second"));

        String output = view.toString();

        assertThat(output, matchesPattern("example description\\r?\\nAvailable commands:\\r?\\n- first\\r?\\n- second\\r?\\n?"));
    }
}
