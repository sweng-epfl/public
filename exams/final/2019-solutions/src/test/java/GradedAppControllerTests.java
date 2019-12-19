import grading.GradedCategory;
import grading.GradedTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@GradedCategory("Practice Question 4")
final class GradedAppControllerTests {
    // Overall, we accept extraneous leading/trailing spaces to be nice, and to treat CRLF (Windows) the same as LF (Unix),
    // except in tests specifically designed to check spacing

    private static final AppController EMPTY_CONTROLLER = new AppController(name -> GradedTestsUtils.list());

    @GradedTest("AppController::handle should throw an IllegalArgumentException when given a null input")
    void t00_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> EMPTY_CONTROLLER.handle(null));
    }

    @GradedTest("AppController::handle should return null given a known command without text")
    void t01_commandWithoutText() {
        assertThat(EMPTY_CONTROLLER.handle("show"), is(nullValue()));
    }

    @GradedTest("AppController::handle should return null given an unknown command")
    void t02_exitWithArgThrows() {
        assertThat(EMPTY_CONTROLLER.handle("explore"), is(nullValue()));
    }

    @GradedTest("AppController::handle should return the correct description for the main view")
    void t03_mainViewDescription() {
        assertThat(EMPTY_CONTROLLER.handle("").description.trim(), is("Welcome!"));
    }

    @GradedTest("AppController::handle should return the correct commands for the main view")
    void t04_mainViewCommands() {
        assertThat(EMPTY_CONTROLLER.handle("").commands, containsInAnyOrder("search", "show"));
    }

    @GradedTest("AppController::handle should ignore leading and trailing spaces for the 'main' view")
    void t05_commandWithSpacesWithoutText() {
        assertThat(EMPTY_CONTROLLER.handle("   ").description.trim(), is("Welcome!"));
    }

    @GradedTest("AppController::handle should pass the text through unmodified for 'search'")
    void t06_searchPassesArgumentThrough() {
        StringBuilder result = new StringBuilder();
        AppController controller = new AppController(name -> {
            result.append(name);
            return GradedTestsUtils.list(new Person("Flayn", null, null, null));
        });

        controller.handle("search Flayn");

        assertThat(result.toString(), is("Flayn"));
    }

    @GradedTest("AppController::handle should return the correct description when a search returns no results")
    void t07_searchWithoutResultsHasCorrectText() {
        assertThat(EMPTY_CONTROLLER.handle("search x").description.trim(), is("The search returned no results."));
    }

    @GradedTest("AppController::handle should return the correct commands when a search returns no results")
    void t08_searchWithoutResultsHasCorrectCommands() {
        assertThat(EMPTY_CONTROLLER.handle("search x").commands, containsInAnyOrder("search", "show"));
    }

    @GradedTest("AppController::handle should return the correct description when a search returns results")
    void t09_searchWithResultsHasCorrectText() {
        AppController controller = new AppController(name -> GradedTestsUtils.list(
                new Person("Ingrid", "Student", 17, "Galatea"),
                new Person("Sylvain", "Student", 19, "Gautier")
        ));
        // be nice - accept leading/trailing whitespace
        // originally we only asked that it should have the name, not that it shouldn't have anything else, so we accept the presence of other stuff
        assertThat(controller.handle("search x").description, matchesPattern("\\s*.*Ingrid.*\\s*\\n\\s*.*Sylvain.*\\s*"));
    }

    @GradedTest("AppController::handle should return the correct commands when a search returns results")
    void t10_searchWithoutResultsHasCorrectCommands() {
        AppController controller = new AppController(name -> GradedTestsUtils.list(
                new Person("Indech", null, null, null),
                new Person("Macuil", null, null, null)
        ));
        assertThat(controller.handle("search x").commands, containsInAnyOrder("search", "show"));
    }

    @GradedTest("AppController::handle should pass the text through unmodified for the 'show' view")
    void t11_showPassesArgumentThrough() {
        StringBuilder result = new StringBuilder();
        AppController controller = new AppController(name -> {
            result.append(name);
            return GradedTestsUtils.list(new Person("Rhea", null, null, null));
        });

        controller.handle("show Rhea");

        assertThat(result.toString(), is("Rhea"));
    }

    @GradedTest("AppController::handle should return the correct description for the 'show' view when the name is unambiguous and some fields are null")
    void t12_showWithUnambiguousNameHasCorrectTextWhenSomeFieldsAreNull() {
        AppController controller = new AppController(name -> GradedTestsUtils.list(new Person("Gilbert", null, 56, null)));

        assertThat(controller.handle("show x").description, matchesPattern("\\s*Gilbert\\s*\\n\\s*56\\s*"));
    }

    @GradedTest("AppController::handle should return the correct description for the 'show' view when the name is unambiguous and no fields are null")
    void t13_showWithUnambiguousNameHasCorrectTextWhenFieldsAreNonNull() {
        AppController controller = new AppController(name -> GradedTestsUtils.list(new Person("Gustave", "Baron", 61, "Dominic")));

        assertThat(controller.handle("show x").description, matchesPattern("\\s*Gustave\\s*\\n\\s*Baron\\s*\\n\\s*61\\s*\\n\\s*Dominic\\s*"));
    }

    @GradedTest("AppController::handle should return the correct commands for the 'show' view when the name is unambiguous")
    void t14_showWithUnambiguousNameHasCorrectCommands() {
        AppController controller = new AppController(name -> GradedTestsUtils.list(new Person("Rodrigue", null, null, null)));

        assertThat(controller.handle("show x").commands, containsInAnyOrder("show", "search"));
    }

    @GradedTest("AppController::handle should return the correct description for the 'show' view when the directory returns no results")
    void t15_showWithNoResultsHasCorrectText() {
        AppController controller = new AppController(name -> GradedTestsUtils.list());

        assertThat(controller.handle("show x").description.trim(), is("The search did not return exactly one person."));
    }

    @GradedTest("AppController::handle should return the correct commands for the 'show' view when the directory returns no results")
    void t16_showWithNoResultsHasCorrectText() {
        AppController controller = new AppController(name -> GradedTestsUtils.list());

        assertThat(controller.handle("show x").commands, containsInAnyOrder("show", "search"));
    }

    @GradedTest("AppController::handle should return the correct description for the 'show' view when the directory returns more than one result")
    void t17_showWithManyResultsHasCorrectText() {
        AppController controller = new AppController(name -> GradedTestsUtils.list(
                new Person("Cichol", null, null, null),
                new Person("Cethleann", null, null, null)
        ));

        assertThat(controller.handle("show x").description.trim(), is("The search did not return exactly one person."));
    }

    @GradedTest("AppController::handle should return the correct commands for the 'show' view when the directory returns more than one result")
    void t18_showWithManyResultsHasCorrectID() {
        AppController controller = new AppController(name -> GradedTestsUtils.list(
                new Person("The Immovable", null, null, null),
                new Person("The Wind Caller", null, null, null)
        ));

        assertThat(controller.handle("show x").commands, containsInAnyOrder("show", "search"));
    }

    @GradedTest("AppController::handle should ignore leading and trailing spaces in the text")
    void t19_ignoreTextTrailingSpaces() {
        StringBuilder result = new StringBuilder();
        AppController controller = new AppController(name -> {
            result.append(name);
            return GradedTestsUtils.list();
        });

        controller.handle("search  xyz  ");

        assertThat(result.toString(), is("xyz"));
    }
}
