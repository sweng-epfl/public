package ch.epfl.sweng.tests.graded;

import ch.epfl.sweng.Course;
import ch.epfl.sweng.CourseQuizFormatter;
import ch.epfl.sweng.User;
import ch.epfl.sweng.grading.GradedCategory;
import ch.epfl.sweng.grading.GradedTest;
import ch.epfl.sweng.grading.JUnitGradeSheetTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;

@RunWith(JUnitGradeSheetTestRunner.class)
@GradedCategory(name = "Part 1.3: `CourseQuizFormatter`", description = "These tests check whether your quiz formatters provide the expected functionality.")
public final class CourseQuizFormatterTests {
    @Test(expected = IllegalArgumentException.class)
    @GradedTest(name = "Passing a null user should throw an exception", points = 1)
    public void nullUser() {
        Course course = new Course("Software Engineering", Collections.emptyList(), Collections.emptyList());

        course.getQuizFormatter(null);
    }

    @Test
    @GradedTest(name = "A guest should see the proper greeting", points = 2)
    public void guestGreeting() {
        User user = new User("Alice");
        Course course = new Course("Software Engineering", Collections.emptyList(), Collections.emptyList());

        CourseQuizFormatter formatter = course.getQuizFormatter(user);

        assertThat(formatter.getGreeting(), is("Hello!"));
    }

    @Test
    @GradedTest(name = "A guest should not see questions", points = 2)
    public void guestQuestion() {
        User user = new User("Alice");
        Course course = new Course("Software Engineering", Collections.emptyList(), Collections.emptyList());

        CourseQuizFormatter formatter = course.getQuizFormatter(user);

        assertThat(formatter.getQuestionText("1 + 1 ?", "2"), is("You cannot see this question."));
    }

    @Test
    @GradedTest(name = "A student should see the proper greeting", points = 2)
    public void studentGreeting() {
        User user = new User("Bob");
        Course course = new Course("Software Engineering", Collections.emptyList(), Collections.singletonList(user));

        CourseQuizFormatter formatter = course.getQuizFormatter(user);

        assertThat(formatter.getGreeting(), is("Hello, " + user.name + "!"));
    }

    @Test
    @GradedTest(name = "A student should only see question statements", points = 2)
    public void studentQuestion() {
        User user = new User("Bob");
        Course course = new Course("Software Engineering", Collections.emptyList(), Collections.singletonList(user));

        CourseQuizFormatter formatter = course.getQuizFormatter(user);

        assertThat(formatter.getQuestionText("1 + 1 ?", "2"), is("1 + 1 ?"));
    }

    @Test
    @GradedTest(name = "A lecturer should see the proper greeting", points = 3)
    public void lecturerGreeting() {
        User user = new User("Carol");
        Course course = new Course("Software Engineering", Collections.singletonList(user), Collections.emptyList());

        CourseQuizFormatter formatter = course.getQuizFormatter(user);

        assertThat(formatter.getGreeting(), is("Hello, Prof. " + user.name + "!"));
    }

    @Test
    @GradedTest(name = "A lecturer should see question statements and answers", points = 3)
    public void lecturerQuestion() {
        User user = new User("Carol");
        Course course = new Course("Software Engineering", Collections.singletonList(user), Collections.emptyList());

        CourseQuizFormatter formatter = course.getQuizFormatter(user);

        assertThat(formatter.getQuestionText("1 + 1 ?", "2"), is("1 + 1 ? 2"));
    }
}