package ch.epfl.sweng.tests.graded;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import ch.epfl.sweng.Forum;
import ch.epfl.sweng.Leaderboard;
import ch.epfl.sweng.Question;
import ch.epfl.sweng.User;
import ch.epfl.sweng.tests.grading.GradedCategory;
import ch.epfl.sweng.tests.grading.GradedTest;
import ch.epfl.sweng.tests.grading.JUnitGradeSheetTestRunner;

@RunWith(JUnitGradeSheetTestRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // we use "t00" format to fix the order
@GradedCategory("Question 2")
public final class Q2Tests {
    private final User alice = Users.unlimited("Alice");
    private final User bob = Users.unlimited("Bob");
    private final User carol = Users.unlimited("Carol");
    private Forum forum;
    private Leaderboard board;

    @Before
    public void setup() {
        forum = new Forum();
        board = new Leaderboard(forum);
    }

    @Test(expected = IllegalArgumentException.class)
    @GradedTest("Cannot create a leaderboard with a null forum")
    public void t00_cannotCreateLeaderboardWithNullForum() {
        new Leaderboard(null);
    }

    @Test
    @GradedTest("An empty leaderboard correctly returns the empty string")
    public void t01_emptyBoardIsCorrect() {
        assertThat(board.toString().trim(), is(""));
    }

    @Test
    @GradedTest("A leaderboard with a single user is correctly displayed")
    public void t02_singleUserBoardIsCorrect() {
        forum.postQuestion(alice, "Question text");

        assertThat(board.toString().trim(), is("#1 Alice 5"));
    }

    @Test
    @GradedTest("A leaderboard with two users, who have different points, is correctly displayed")
    public void t03_twoUserBoardWithDifferentPointsIsCorrect() {
        forum.postQuestion(alice, "Question text");

        Question question = forum.getQuestions().get(0);
        forum.postAnswer(bob, question, "Answer text");

        assertThat(board.toString().trim(), is(
                "#1 Alice 5" +
                        System.lineSeparator() +
                        "#2 Bob 1"
        ));
    }

    @Test
    @GradedTest("A leaderboard with two users, who have the same number of points, is correctly displayed")
    public void t04_twoUserBoardWithSamePointsIsCorrect() {
        // Voluntarily reverse order here :)
        forum.postQuestion(bob, "Question text 1");
        forum.postQuestion(alice, "Question text 2");

        assertThat(board.toString().trim(), is(
                "#1 Alice 5" +
                        System.lineSeparator() +
                        "#1 Bob 5"
        ));
    }

    @Test
    @GradedTest("A leaderboard with three users, the first two of which have the same rank, is correctly displayed")
    public void t05_rankIsCorrectAfterExAequoUsers() {
        // Voluntarily reverse order here :)
        forum.postQuestion(bob, "Question text 1");
        forum.postQuestion(alice, "Question text 2");

        Question question = forum.getQuestions().get(0);
        forum.postAnswer(carol, question, "Answer text");

        assertThat(board.toString().trim(), is(
                "#1 Alice 5" +
                        System.lineSeparator() +
                        "#1 Bob 5" +
                        System.lineSeparator() +
                        "#3 Carol 1"
        ));
    }

    @Test
    @GradedTest("There should not be a line separator at the end of a leaderboard")
    public void t06_twoUserBoardWithDifferentPointsIsCorrect() {
        forum.postQuestion(alice, "Question text");

        assertThat(board.toString(), is(
                "#1 Alice 5"
        ));
    }
}
