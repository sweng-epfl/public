package ch.epfl.sweng.tests.graded;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import ch.epfl.sweng.Answer;
import ch.epfl.sweng.Forum;
import ch.epfl.sweng.IllegalOperationException;
import ch.epfl.sweng.NoSuchPostException;
import ch.epfl.sweng.Question;
import ch.epfl.sweng.StandardUser;
import ch.epfl.sweng.User;
import ch.epfl.sweng.tests.grading.GradedCategory;
import ch.epfl.sweng.tests.grading.GradedTest;
import ch.epfl.sweng.tests.grading.JUnitGradeSheetTestRunner;

@RunWith(JUnitGradeSheetTestRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // we use "t00" format to fix the order
@GradedCategory("Question 1")
public final class Q1Tests {
    private final User alice = Users.unlimited("Alice");
    private final User bob = Users.unlimited("Bob");
    private final User sirius = Users.limited("Sirius");
    private Forum forum;
    private Forum otherForum;

    @Before
    public void setup() {
        forum = new Forum();
        otherForum = new Forum();
    }

    @Test
    @GradedTest("An unrestricted user should be able to post a question")
    public void t00_canPostQuestion() {
        forum.postQuestion(alice, "Question text");
        Question question = forum.getQuestions().get(0);

        assertThat(question.getAuthor(), is(alice));
        assertThat(question.getText(), is("Question text"));
    }

    @Test(expected = IllegalArgumentException.class)
    @GradedTest("Cannot post a question with a null user")
    public void t01_cannotPostQuestionWithNullUser() {
        forum.postQuestion(null, "Question text");
    }

    @Test(expected = IllegalArgumentException.class)
    @GradedTest("Cannot post a question with a null text")
    public void t02_cannotPostQuestionWithNullText() {
        forum.postQuestion(alice, null);
    }

    @Test(expected = IllegalOperationException.class)
    @GradedTest("Cannot post a question the user is not allowed to post")
    public void t03_cannotPostIllegalQuestion() {
        forum.postQuestion(sirius, "Question text");
    }

    @Test
    @GradedTest("An unrestricted user should be able to post an answer")
    public void t04_canPostAnswer() {
        forum.postQuestion(alice, "Question text");
        Question question = forum.getQuestions().get(0);

        forum.postAnswer(bob, question, "Answer text");
        Answer answer = question.getAnswers().get(0);

        assertThat(answer.getAuthor(), is(bob));
        assertThat(answer.getText(), is("Answer text"));
    }

    @Test
    @GradedTest("Cannot post an answer with a null user")
    public void t05_cannotPostAnswerWithNullUser() {
        forum.postQuestion(alice, "Question text");
        Question question = forum.getQuestions().get(0);

        try {
            forum.postAnswer(null, question, "Answer text");
        } catch (IllegalArgumentException e) {
            return;
        }

        fail("Should have thrown an IllegalArgumentException.");
    }

    @Test(expected = IllegalArgumentException.class)
    @GradedTest("Cannot post an answer to a null question")
    public void t06_cannotPostAnswerWithNullQuestion() {
        forum.postAnswer(alice, null, "Answer text");
    }

    @Test
    @GradedTest("Cannot post an answer with a null text")
    public void t07_cannotPostAnswerWithNullText() {
        forum.postQuestion(alice, "Question text");
        Question question = forum.getQuestions().get(0);

        try {
            forum.postAnswer(bob, question, null);
        } catch (IllegalArgumentException e) {
            return;
        }

        fail("Should have thrown an IllegalArgumentException.");
    }

    @Test
    @GradedTest("Cannot post an answer to a question not in the forum")
    public void t08_cannotPostAnswerToQuestionOutsideForum() {
        otherForum.postQuestion(alice, "Question text");
        Question question = otherForum.getQuestions().get(0);

        try {
            forum.postAnswer(bob, question, "Answer text");
        } catch (NoSuchPostException e) {
            return;
        }

        fail("Should have thrown a NoSuchPostException.");
    }

    @Test
    @GradedTest("Cannot post an answer the user is not allowed to post")
    public void t09_cannotPostIllegalAnswer() {
        forum.postQuestion(alice, "Question text");
        Question question = forum.getQuestions().get(0);

        try {
            forum.postAnswer(sirius, question, "Answer text");
        } catch (IllegalOperationException e) {
            return;
        }

        fail("Should have thrown an IllegalOperationException.");
    }

    @Test
    @GradedTest("An unrestricted user should be able to edit a question")
    public void t10_canEditQuestion() {
        forum.postQuestion(alice, "Question text");
        Question question = forum.getQuestions().get(0);

        forum.editPost(alice, question, "New question text");

        assertThat(question.getText(), is("New question text"));
    }

    @Test
    @GradedTest("An unrestricted user should be able to edit an answer")
    public void t11_canEditAnswer() {
        forum.postQuestion(alice, "Question text");
        Question question = forum.getQuestions().get(0);

        forum.postAnswer(bob, question, "Answer text");
        Answer answer = question.getAnswers().get(0);

        forum.editPost(bob, answer, "New answer text");

        assertThat(answer.getText(), is("New answer text"));
    }

    @Test
    @GradedTest("Cannot edit a post with a null user")
    public void t12_cannotEditWithNullUser() {
        forum.postQuestion(alice, "Question text");
        Question question = forum.getQuestions().get(0);

        try {
            forum.editPost(null, question, "New question text");
        } catch (IllegalArgumentException e) {
            return;
        }

        fail("Should have thrown an IllegalArgumentException.");
    }

    @Test(expected = IllegalArgumentException.class)
    @GradedTest("Cannot edit a null post")
    public void t13_cannotEditWithNullPost() {
        forum.editPost(alice, null, "New post text");
    }

    @Test
    @GradedTest("Cannot edit a post with a null text")
    public void t14_cannotEditWithNullText() {
        forum.postQuestion(alice, "Question text");
        Question question = forum.getQuestions().get(0);

        try {
            forum.editPost(alice, question, null);
        } catch (IllegalArgumentException e) {
            return;
        }

        fail("Should have thrown an IllegalArgumentException.");
    }

    @Test
    @GradedTest("Cannot edit a question outside of the forum")
    public void t15_cannotEditQuestionOutsideForum() {
        otherForum.postQuestion(alice, "Question text");
        Question question = otherForum.getQuestions().get(0);

        try {
            forum.editPost(alice, question, "New question text");
        } catch (NoSuchPostException e) {
            return;
        }

        fail("Should have thrown a NoSuchPostException.");
    }

    @Test
    @GradedTest("Cannot edit an answer outside of the forum")
    public void t16_cannotEditAnswerOutsideForum() {
        otherForum.postQuestion(alice, "Question text");
        Question question = otherForum.getQuestions().get(0);
        otherForum.postAnswer(alice, question, "Answer text");
        Answer answer = question.getAnswers().get(0);

        try {
            forum.editPost(alice, answer, "New answer text");
        } catch (NoSuchPostException e) {
            return;
        }

        fail("Should have thrown a NoSuchPostException.");
    }

    @Test
    @GradedTest("Cannot make an edit that the user is not allowed to make")
    public void t17_cannotMakeIllegalEdit() {
        forum.postQuestion(alice, "Question text");
        Question question = forum.getQuestions().get(0);

        try {
            forum.editPost(sirius, question, "New question text");
        } catch (IllegalOperationException e) {
            return;
        }

        fail("Should have thrown an IllegalOperationException.");
    }

    @Test(expected = IllegalArgumentException.class)
    @GradedTest("Cannot create a standard user with a null name")
    public void t18_cannotCreateStandardUserWithNullName() {
        new StandardUser(null);
    }

    @Test
    @GradedTest("Standard users should be able to ask questions with 10 characters")
    public void t19_standardUserCanAskLongQuestion() {
        User standard = new StandardUser("Standard");

        assertThat(standard.canAsk("0123456789"), is(true));
    }

    @Test
    @GradedTest("Standard users should not be able to ask questions with <10 characters")
    public void t20_standardUserCannotAskShortQuestion() {
        User standard = new StandardUser("Standard");

        assertThat(standard.canAsk("01234"), is(false));
    }

    @Test
    @GradedTest("Standard users should be able to answer others' questions")
    public void t21_standardUserCanAnswerQuestionFromOthers() {
        User standard = new StandardUser("Standard");

        forum.postQuestion(alice, "Question text");
        Question question = forum.getQuestions().get(0);

        assertThat(standard.canAnswer(question, "Question text"), is(true));
    }

    @Test
    @GradedTest("Standard users should not be able to answer their own questions")
    public void t22_standardUserCannotAnswerTheirOwnQuestion() {
        User standard = new StandardUser("Standard");

        forum.postQuestion(standard, "Question text");
        Question question = forum.getQuestions().get(0);

        assertThat(standard.canAnswer(question, "Question text"), is(false));
    }

    @Test
    // Ungraded, only shows how to get code coverage
    public void t23_standardUserCannotEditPostsFromOthers() {
        User standard = new StandardUser("Standard");

        forum.postQuestion(alice, "Question text");
        Question question = forum.getQuestions().get(0);

        assertThat(standard.canEdit(question, "New question text"), is(false));
    }

    @Test
    // Ungraded, only shows how to get code coverage
    public void t24_standardUserCanEditTheirQuestionsWithNoAnswers() {
        User standard = new StandardUser("Standard");

        forum.postQuestion(standard, "Question text");
        Question question = forum.getQuestions().get(0);

        assertThat(standard.canEdit(question, "New question text"), is(true));
    }

    @Test
    // Ungraded, only shows how to get code coverage
    public void t25_standardUserCannotEditTheirQuestionsWithAnswers() {
        User alice = new StandardUser("Alice");
        User bob = Users.unlimited("Bob");

        forum.postQuestion(alice, "Question text");
        Question question = forum.getQuestions().get(0);

        forum.postAnswer(bob, question, "Answer text");

        assertThat(alice.canEdit(question, "New question text"), is(false));
    }

    @Test
    // Ungraded, only shows how to get code coverage
    public void t26_standardUserCanEditTheirAnswersIfAnotherUserHasAnsweredThreeTimes() {
        User alice = Users.unlimited("Alice");
        User bob = new StandardUser("Bob");
        User carol = Users.unlimited("Carol");

        forum.postQuestion(alice, "Question text");
        Question question = forum.getQuestions().get(0);

        forum.postAnswer(bob, question, "Answer text");
        Answer answer = question.getAnswers().get(0);

        forum.postAnswer(carol, question, "Answer text 1");
        forum.postAnswer(carol, question, "Answer text 2");
        forum.postAnswer(carol, question, "Answer text 3");

        assertThat(bob.canEdit(answer, "New answer text"), is(true));
    }

    @Test
    // Ungraded, only shows how to get code coverage
    public void t27_standardUserCanEditTheirAnswersIfThreeUsersAnsweredOnAverageMoreThanOnce() {
        User alice = Users.unlimited("Alice");
        User bob = new StandardUser("Bob");
        User carol = Users.unlimited("Carol");
        User daniel = Users.unlimited("Daniel");

        forum.postQuestion(alice, "Question text");
        Question question = forum.getQuestions().get(0);

        forum.postAnswer(bob, question, "Answer text");
        Answer answer = question.getAnswers().get(0);

        forum.postAnswer(carol, question, "Answer text 1");
        forum.postAnswer(carol, question, "Answer text 2");
        forum.postAnswer(daniel, question, "Answer text 3");
        forum.postAnswer(daniel, question, "Answer text 4");

        assertThat(bob.canEdit(answer, "New answer text"), is(true));
    }

    @Test
    // Ungraded, only shows how to get code coverage
    public void t28_standardUserCannotEditTheirAnswersOtherwise() {
        User alice = Users.unlimited("Alice");
        User bob = new StandardUser("Bob");

        forum.postQuestion(alice, "Question text");
        Question question = forum.getQuestions().get(0);

        forum.postAnswer(bob, question, "Answer text");
        Answer answer = question.getAnswers().get(0);

        assertThat(bob.canEdit(answer, "New answer text"), is(false));
    }
}