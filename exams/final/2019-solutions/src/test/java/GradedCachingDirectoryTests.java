import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import grading.GradedCategory;
import grading.GradedTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@GradedCategory("Practice Question 2")
class GradedCachingDirectoryTests {
    @GradedTest("CachingDirectory's constructor should throw an IllegalArgumentException when given a null Directory")
    void t00_nullArgToConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new CachingDirectory(null));
    }

    @GradedTest("CachingDirectory should return results from the underlying directory")
    void t01_search() {
        Person person = new Person("Edelgard", "Heir", 17, "Enbarr");
        CachingDirectory caching = new CachingDirectory(
                name -> GradedTestsUtils.list(person)
        );

        List<Person> result = caching.search("anything");

        assertThat(result, contains(person));
    }

    @GradedTest("CachingDirectory should return a cached result when searching the same name for the second time")
    void t02_cachedSearch() {
        AtomicInteger counter = new AtomicInteger(0);
        CachingDirectory caching = new CachingDirectory(
                name -> {
                    if (counter.getAndIncrement() == 0) {
                        return GradedTestsUtils.list(new Person("Dimitri", "Crown Prince", 17, "Fhirdiad"));
                    }
                    return GradedTestsUtils.list();
                }
        );

        List<Person> firstResult = caching.search("anything");
        List<Person> secondResult = caching.search("anything");

        assertThat(secondResult, is(firstResult));
    }

    @GradedTest("CachingDirectory should ignore leading and trailing whitespace for cache lookups")
    void t03_whitespace() {
        AtomicInteger counter = new AtomicInteger(0);
        CachingDirectory caching = new CachingDirectory(
                name -> {
                    if (counter.getAndIncrement() == 0) {
                        return GradedTestsUtils.list(new Person("Claude", "Heir", 17, "Deirdru"));
                    }
                    return GradedTestsUtils.list();
                }
        );

        List<Person> firstResult = caching.search("anything");
        List<Person> secondResult = caching.search("\tanything ");

        assertThat(secondResult, is(firstResult));
    }

    @GradedTest("CachingDirectory should perform case-insensitive cache lookups")
    void t04_caseInsensitivity() {
        AtomicInteger counter = new AtomicInteger(0);
        CachingDirectory caching = new CachingDirectory(
                name -> {
                    if (counter.getAndIncrement() == 0) {
                        return GradedTestsUtils.list(new Person("Jeralt", "Blade Breaker", null, "Garreg Mach"));
                    }
                    return GradedTestsUtils.list();
                }
        );

        List<Person> firstResult = caching.search("AnYtHiNg");
        List<Person> secondResult = caching.search("aNyThInG");

        assertThat(secondResult, is(firstResult));
    }

    @GradedTest("CachingDirectory should not modify the name before passing it to the wrapped Directory")
    void t05_unmodifiedName() {
        StringBuilder output = new StringBuilder();
        CachingDirectory caching = new CachingDirectory(
                name -> {
                    output.append(name);
                    return GradedTestsUtils.list();
                }
        );

        caching.search("Anything ");

        assertThat(output.toString(), is("Anything "));
    }
}
