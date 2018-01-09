package ch.epfl.sweng.tests.graded;

import ch.epfl.sweng.*;
import ch.epfl.sweng.grading.GradedCategory;
import ch.epfl.sweng.grading.GradedTest;
import ch.epfl.sweng.grading.JUnitGradeSheetTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(JUnitGradeSheetTestRunner.class)
@GradedCategory(name = "Part 1.2: `PapademiaAdapter`", description = "These tests check whether your adapter provides the expected functionality.")
public final class PapademiaAdapterTests {
    // Not graded, but required for coverage
    @Test(expected = IllegalArgumentException.class)
    public void nullUsersInConstructor() {
        new PapademiaAdapter(null, Collections.emptyList());
    }

    // Not graded, but required for coverage
    @Test(expected = IllegalArgumentException.class)
    public void nullCoursesInConstructor() {
        new PapademiaAdapter(Collections.emptyList(), null);
    }

    @Test
    @GradedTest(name = "A user with no courses should be adapted properly", points = 2)
    public void singleUser() {
        final PapademiaUser user = new PapademiaUser("Alice", Collections.emptyList(), Collections.emptyList());
        final PapademiaAdapter adapter = new PapademiaAdapter(Collections.singletonList(user), Collections.emptyList());

        final List<User> users = adapter.getUsers();
        final List<Course> courses = adapter.getCourses();

        assertThat(users, contains(new User(user.name)));
        assertThat(courses, is(empty()));
    }

    @Test
    @GradedTest(name = "A course with no users should be adapted properly", points = 2)
    public void singleCourse() {
        final PapademiaCourse course = new PapademiaCourse("Software Engineering");
        final PapademiaAdapter adapter = new PapademiaAdapter(Collections.emptyList(), Collections.singletonList(course));

        final List<User> users = adapter.getUsers();
        final List<Course> courses = adapter.getCourses();

        assertThat(users, is(empty()));
        assertThat(courses, contains(new Course(course.name, Collections.emptyList(), Collections.emptyList())));
    }

    @Test
    @GradedTest(name = "A course and an user with no relation should be adapted properly", points = 2)
    public void unrelatedCourseAndUser() {
        final PapademiaUser user = new PapademiaUser("Bob", Collections.emptyList(), Collections.emptyList());
        final PapademiaCourse course = new PapademiaCourse("Software Engineering");
        final PapademiaAdapter adapter = new PapademiaAdapter(Collections.singletonList(user), Collections.singletonList(course));

        final List<User> users = adapter.getUsers();
        final List<Course> courses = adapter.getCourses();

        assertThat(users, contains(new User(user.name)));
        assertThat(courses, contains(new Course(course.name, Collections.emptyList(), Collections.emptyList())));
    }

    @Test
    @GradedTest(name = "A course taught by an user should be adapted properly", points = 2)
    public void courseTaughtByUser() {
        final PapademiaCourse course = new PapademiaCourse("Intro to Jenkins");
        final PapademiaUser user = new PapademiaUser("Carol", Collections.singletonList(course), Collections.emptyList());
        final PapademiaAdapter adapter = new PapademiaAdapter(Collections.singletonList(user), Collections.singletonList(course));

        final List<User> users = adapter.getUsers();
        final List<Course> courses = adapter.getCourses();

        final User poodleUser = new User(user.name);
        final Course poodleCourse = new Course(course.name, Collections.singletonList(poodleUser), Collections.emptyList());

        assertThat(users, contains(poodleUser));
        assertThat(courses, contains(poodleCourse));
    }

    @Test
    @GradedTest(name = "A course attended by an user should be adapted properly", points = 2)
    public void courseAttendedByUser() {
        final PapademiaCourse course = new PapademiaCourse("Christmas Cookies Baking");
        final PapademiaUser user = new PapademiaUser("Daniel", Collections.emptyList(), Collections.singletonList(course));
        final PapademiaAdapter adapter = new PapademiaAdapter(Collections.singletonList(user), Collections.singletonList(course));

        final List<User> users = adapter.getUsers();
        final List<Course> courses = adapter.getCourses();

        final User poodleUser = new User(user.name);
        final Course poodleCourse = new Course(course.name, Collections.emptyList(), Collections.singletonList(poodleUser));

        assertThat(users, contains(poodleUser));
        assertThat(courses, contains(poodleCourse));
    }

    @Test
    @GradedTest(name = "Null users should be ignored", points = 1)
    public void nullUsersShouldBeIgnored() {
        final PapademiaAdapter adapter = new PapademiaAdapter(Collections.singletonList(null), Collections.emptyList());

        final List<User> users = adapter.getUsers();
        final List<Course> courses = adapter.getCourses();

        assertThat(users, is(empty()));
        assertThat(courses, is(empty()));
    }

    @Test
    @GradedTest(name = "Null courses should be ignored", points = 1)
    public void nullCoursesShouldBeIgnored() {
        final PapademiaAdapter adapter = new PapademiaAdapter(Collections.emptyList(), Collections.singletonList(null));

        final List<User> users = adapter.getUsers();
        final List<Course> courses = adapter.getCourses();

        assertThat(users, is(empty()));
        assertThat(courses, is(empty()));
    }

    @Test
    @GradedTest(name = "Four users (two lecturers, two student) in two classes should be adapted properly", points = 3)
    public void complexGraph() {
        final PapademiaCourse course1 = new PapademiaCourse("Swordfighting");
        final PapademiaCourse course2 = new PapademiaCourse("Archery");
        final PapademiaUser user1 = new PapademiaUser("Ike", Collections.singletonList(course1), Collections.singletonList(course2));
        final PapademiaUser user2 = new PapademiaUser("Mia", Collections.emptyList(), Arrays.asList(course1, course2));
        final PapademiaUser user3 = new PapademiaUser("Oscar", Collections.emptyList(), Collections.singletonList(course2));
        final PapademiaUser user4 = new PapademiaUser("Titania", Arrays.asList(course1, course2), Collections.emptyList());
        final PapademiaAdapter adapter = new PapademiaAdapter(Arrays.asList(user1, user2, user3, user4), Arrays.asList(course1, course2));

        final List<User> users = adapter.getUsers();
        final List<Course> courses = adapter.getCourses();

        final User poodleUser1 = new User(user1.name);
        final User poodleUser2 = new User(user2.name);
        final User poodleUser3 = new User(user3.name);
        final User poodleUser4 = new User(user4.name);
        final Course poodleCourse1 = new Course(course1.name, Arrays.asList(poodleUser1, poodleUser4), Collections.singletonList(poodleUser2));
        final Course poodleCourse2 = new Course(course2.name, Collections.singletonList(poodleUser4), Arrays.asList(poodleUser1, poodleUser2, poodleUser3));

        assertThat(users, containsInAnyOrder(poodleUser1, poodleUser2, poodleUser3, poodleUser4));
        assertThat(courses, containsInAnyOrder(poodleCourse1, poodleCourse2));
    }
}