import java.util.HashMap;
import java.util.List;
import java.util.Map;

import grading.GradedCategory;
import grading.GradedTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@GradedCategory("Practice Question 3")
class GradedOverridableDirectoryTests {
    @GradedTest("OverridableDirectory's constructor should throw an IllegalArgumentException when given a null Directory")
    void t00_nullDirectory() {
        assertThrows(IllegalArgumentException.class, () -> new OverridableDirectory(null, new HashMap<>()));
    }

    @GradedTest("OverridableDirectory's constructor should throw an IllegalArgumentException when given a null overrides map")
    void t01_nullOverrides() {
        Directory directory = name -> GradedTestsUtils.list();
        assertThrows(IllegalArgumentException.class, () -> new OverridableDirectory(directory, null));
    }

    @GradedTest("OverridableDirectory's constructor should throw an IllegalArgumentException if the overrides map contains null values")
    void t02_nullOverrides() {
        Directory directory = name -> GradedTestsUtils.list();
        Map<String, Person> overrides = GradedTestsUtils.map(
                "me", null
        );

        assertThrows(IllegalArgumentException.class, () -> new OverridableDirectory(directory, overrides));
    }

    @GradedTest("OverridableDirectory's constructor should throw an IllegalArgumentException if the overrides map contains keys considered equal")
    void t03_equalOverrides() {
        Directory directory = name -> GradedTestsUtils.list();
        Map<String, Person> overrides = GradedTestsUtils.map(
                " Me", new Person("Byleth", null, null, null),
                "me ", new Person("Byleth", null, null, null)
        );

        assertThrows(IllegalArgumentException.class, () -> new OverridableDirectory(directory, overrides));
    }

    @GradedTest("OverridableDirectory should return results from the underlying directory")
    void t04_search() {
        Person person = new Person("Petra", "Crown Princess", 15, "Brigid");
        Person other = new Person("Caspar", "Student", 16, "Bergliez"); // should not be returned!
        OverridableDirectory overridable = new OverridableDirectory(
                name -> GradedTestsUtils.list(person),
                GradedTestsUtils.map("example", other)
        );

        List<Person> result = overridable.search("anything");

        assertThat(result, contains(person));
    }

    @GradedTest("OverridableDirectory should use the provided overrides")
    void t05_override() {
        Person override = new Person("Linhardt", "Student", 16, "Hevring");
        OverridableDirectory overridable = new OverridableDirectory(
                name -> GradedTestsUtils.list(),
                GradedTestsUtils.map("example", override)
        );

        List<Person> result = overridable.search("example");

        assertThat(result, contains(override));
    }

    @GradedTest("OverridableDirectory should put the provided overrides at the beginning")
    void t06_overrideAtBeginning() {
        Person person = new Person("Bernadetta", "Student", 17, "Varley");
        Person override = new Person("Ferdinand", "Student", 17, "Aegir");
        OverridableDirectory overridable = new OverridableDirectory(
                name -> GradedTestsUtils.list(person),
                GradedTestsUtils.map("example", override)
        );

        List<Person> result = overridable.search("example");

        assertThat(result, contains(override, person));
    }

    @GradedTest("OverridableDirectory should ignore leading and trailing whitespace for override lookups")
    void t07_whitespace() {
        Person person = new Person("Dorothea", "Songstress", 18, "Enbarr");
        OverridableDirectory overridable = new OverridableDirectory(
                name -> GradedTestsUtils.list(),
                GradedTestsUtils.map("anything  ", person)
        );

        List<Person> result = overridable.search("\tanything");

        assertThat(result, contains(person));
    }

    @GradedTest("OverridableDirectory should perform case-insensitive override lookups")
    void t08_caseInsensitivity() {
        Person person = new Person("Hubert", "Student", 20, "Enbarr");
        OverridableDirectory overridable = new OverridableDirectory(
                name -> GradedTestsUtils.list(),
                GradedTestsUtils.map("AnYtHiNg", person)
        );

        List<Person> result = overridable.search("aNyThInG");

        assertThat(result, contains(person));
    }

    @GradedTest("OverridableDirectory should not modify the name before passing it to the wrapped Directory")
    void t09_unmodifiedName() {
        StringBuilder result = new StringBuilder();
        OverridableDirectory overridable = new OverridableDirectory(
                name -> {
                    result.append(name);
                    return GradedTestsUtils.list();
                },
                new HashMap<>()
        );

        overridable.search("Anything ");

        assertThat(result.toString(), is("Anything "));
    }
}
