package ch.epfl.sweng.tests.graded;

import ch.epfl.sweng.Course;
import ch.epfl.sweng.Homepage;
import ch.epfl.sweng.User;
import ch.epfl.sweng.grading.GradedCategory;
import ch.epfl.sweng.grading.GradedTest;
import ch.epfl.sweng.grading.JUnitGradeSheetTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitGradeSheetTestRunner.class)
@GradedCategory(name = "Part 1.1: `Homepage`", description = "These tests check whether you fixed all bugs.")
public final class HomepageTests {
    // Since we asked for 100% coverage, this test is necessary
    @Test
    public void constructor() {
        new Homepage();
    }

    // Not graded, but required for coverage
    @Test(expected = IllegalArgumentException.class)
    public void nullCourses() {
        final User user = new User("Alice");

        Homepage.print(user, null);
    }

    @Test
    @GradedTest(name = "Long names should be supported", points = 3)
    public void longNames() {
        final User user = new User("Pablo Diego José Francisco de Paula Juan Nepomuceno María de los Remedios Cipriano de la Santísima Trinidad Ruiz y Picasso");
        final Course taughtCourse = new Course("Software Engineering", Collections.singletonList(user), Collections.emptyList());
        final Course attendedCourse = new Course("Intro to Jenkins", Collections.emptyList(), Collections.singletonList(user));

        final String homepage = Homepage.print(user, Arrays.asList(taughtCourse, attendedCourse));

        assertThat(homepage, containsString(user.name));
    }

    @Test(expected = IllegalArgumentException.class)
    @GradedTest(name = "Passing a null user should throw an exception", points = 2)
    public void nullUser() {
        final User user = new User("Alice");
        final Course taughtCourse = new Course("Software Engineering", Collections.singletonList(user), Collections.emptyList());
        final Course attendedCourse = new Course("Intro to Jenkins", Collections.emptyList(), Collections.singletonList(user));

        Homepage.print(null, Arrays.asList(taughtCourse, attendedCourse));
    }

    @Test
    @GradedTest(name = "Lack of taught courses should result in the proper message", points = 2)
    public void noTaughtCourses() {
        final User user = new User("Bob");
        final Course attendedCourse = new Course("Intro to Jenkins", Collections.emptyList(), Collections.singletonList(user));

        final String homepage = Homepage.print(user, Arrays.asList(attendedCourse));

        assertThat(homepage, is("Hello, " + user.name + "!"
                + System.lineSeparator()
                + "You are not teaching any courses."
                + System.lineSeparator()
                + "Courses you attend"
                + System.lineSeparator()
                + "- " + attendedCourse.name
                + System.lineSeparator()));
    }

    @Test
    @GradedTest(name = "Lack of attended courses should result in the proper message", points = 2)
    public void noAttendedCourses() {
        final User user = new User("Carol");
        final Course taughtCourse = new Course("Software Engineering", Collections.singletonList(user), Collections.emptyList());

        final String homepage = Homepage.print(user, Arrays.asList(taughtCourse));

        assertThat(homepage, is("Hello, " + user.name + "!"
                + System.lineSeparator()
                + "Courses you teach"
                + System.lineSeparator()
                + "- " + taughtCourse.name
                + System.lineSeparator()
                + "You are not attending any courses."
                + System.lineSeparator()));
    }

    @Test
    @GradedTest(name = "Taught courses should be sorted alphabetically", points = 3)
    public void taughtCoursesAreSorted() {
        final User user = new User("Daniel");
        final Course taughtCourseZ = new Course("Zesty Zapus", Collections.singletonList(user), Collections.emptyList());
        final Course taughtCourseA = new Course("Artful Aardvark", Collections.singletonList(user), Collections.emptyList());
        final Course taughtCourseP = new Course("Precise Pangolin", Collections.singletonList(user), Collections.emptyList());
        final Course attendedCourse = new Course("Intro to Jenkins", Collections.emptyList(), Collections.singletonList(user));

        final String homepage = Homepage.print(user, Arrays.asList(taughtCourseZ, taughtCourseA, taughtCourseP, attendedCourse));

        assertThat(homepage, is("Hello, " + user.name + "!"
                + System.lineSeparator()
                + "Courses you teach"
                + System.lineSeparator()
                + "- " + taughtCourseA.name
                + System.lineSeparator()
                + "- " + taughtCourseP.name
                + System.lineSeparator()
                + "- " + taughtCourseZ.name
                + System.lineSeparator()
                + "Courses you attend"
                + System.lineSeparator()
                + "- " + attendedCourse.name
                + System.lineSeparator()));
    }

    @Test
    @GradedTest(name = "Attended courses should be sorted alphabetically", points = 3)
    public void attendedCoursesAreSorted() {
        final User user = new User("Eve");
        final Course taughtCourse = new Course("Software Engineering", Collections.singletonList(user), Collections.emptyList());
        final Course attendedCourseZ = new Course("Zesty Zapus", Collections.emptyList(), Collections.singletonList(user));
        final Course attendedCourseA = new Course("Artful Aardvark", Collections.emptyList(), Collections.singletonList(user));
        final Course attendedCourseP = new Course("Precise Pangolin", Collections.emptyList(), Collections.singletonList(user));

        final String homepage = Homepage.print(user, Arrays.asList(taughtCourse, attendedCourseZ, attendedCourseA, attendedCourseP));

        assertThat(homepage, is("Hello, " + user.name + "!"
                + System.lineSeparator()
                + "Courses you teach"
                + System.lineSeparator()
                + "- " + taughtCourse.name
                + System.lineSeparator()
                + "Courses you attend"
                + System.lineSeparator()
                + "- " + attendedCourseA.name
                + System.lineSeparator()
                + "- " + attendedCourseP.name
                + System.lineSeparator()
                + "- " + attendedCourseZ.name
                + System.lineSeparator()));
    }
}