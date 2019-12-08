package ch.epfl.sweng.tests.graded;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import ch.epfl.sweng.Forum;
import ch.epfl.sweng.LimitedUser;
import ch.epfl.sweng.Moderator;
import ch.epfl.sweng.Question;
import ch.epfl.sweng.User;
import ch.epfl.sweng.tests.grading.GradedCategory;
import ch.epfl.sweng.tests.grading.GradedTest;
import ch.epfl.sweng.tests.grading.JUnitGradeSheetTestRunner;

@RunWith(JUnitGradeSheetTestRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // we use "t00" format to fix the order
@GradedCategory("Question 3")
public final class Q3Tests {
    private static final String BANNED_WORD = "Travis";

    private Forum forum;

    @Before
    public void setup() {
        forum = new Forum();
    }

    @Test(expected = IllegalArgumentException.class)
    @GradedTest("Cannot create a moderator from a null user")
    public void t00_cannotCreateModeratorFromNullUser() {
        new Moderator(null);
    }

    @Test
    @GradedTest("Moderators should have their decorated user's name")
    public void t01_moderatorHasWrappedName() {
        Moderator moderator = new Moderator(Users.unlimited("Alice"));

        assertThat(moderator.getName(), is("Alice"));
    }

    @Test
    @GradedTest("Moderators should not have more rights to post questions than their decorated user")
    public void t02_moderatorQuestionsAreLimited() {
        Moderator moderator = new Moderator(Users.limited("Alice"));

        assertThat(moderator.canAsk("Question text"), is(false));
    }

    @Test
    @GradedTest("Moderators should always be allowed to post answers")
    public void t03_moderatorAnswersAreUnlimited() {
        User user = Users.unlimited("Alice");
        Moderator moderator = new Moderator(Users.limited("Bob"));

        forum.postQuestion(user, "Question text");
        Question question = forum.getQuestions().get(0);

        assertThat(moderator.canAnswer(question, "Question text"), is(true));
    }

    @Test
    @GradedTest("Moderators should always be allowed to edit posts")
    public void t04_moderatorEditsAreUnlimited() {
        User user = Users.unlimited("Alice");
        Moderator moderator = new Moderator(Users.limited("Bob"));

        forum.postQuestion(user, "Question text");
        Question question = forum.getQuestions().get(0);

        assertThat(moderator.canEdit(question, "New question text"), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    @GradedTest("Cannot create a limited user from a null user")
    public void t05_cannotCreateLimitedUserFromNullUser() {
        new LimitedUser(null);
    }

    @Test
    @GradedTest("Limited users should have their decorated user's name")
    public void t06_limitedUserHasWrappedName() {
        LimitedUser limited = new LimitedUser(Users.unlimited("Alice"));

        assertThat(limited.getName(), is("Alice"));
    }

    @Test
    @GradedTest("Limited users should be able to post questions without the banned word")
    public void t07_limitedUserCanAskNormalQuestion() {
        LimitedUser limited = new LimitedUser(Users.unlimited("Alice"));

        assertThat(limited.canAsk("Question text"), is(true));
    }

    @Test
    @GradedTest("Limited users should not be able to post questions with the banned word")
    public void t08_limitedUserCannotUseBannedWordInQuestionText() {
        LimitedUser limited = new LimitedUser(Users.unlimited("Alice"));

        assertThat(limited.canAsk(BANNED_WORD + " ..."), is(false));
    }

    @Test
    @GradedTest("Limited users should not be able to post questions with the banned word in uppercase")
    public void t09_limitedUserCannotUseBannedWordInQuestionTitleUppercase() {
        LimitedUser limited = new LimitedUser(Users.unlimited("Alice"));

        assertThat(limited.canAsk(BANNED_WORD.toUpperCase() + " ..."), is(false));
    }

    @Test
    @GradedTest("Limited users should not be able to post questions with the banned word in lowercase")
    public void t10_limitedUserCannotUseBannedWordInQuestionTitleLowercase() {
        LimitedUser limited = new LimitedUser(Users.unlimited("Alice"));

        assertThat(limited.canAsk(BANNED_WORD.toLowerCase() + " ..."), is(false));
    }

    @Test
    @GradedTest("Limited users should be able to post answers without the banned word")
    public void t11_limitedUserCanPostNormalAnswer() {
        User user = Users.unlimited("Alice");
        LimitedUser limited = new LimitedUser(Users.unlimited("Bob"));

        forum.postQuestion(user, "Question text");
        Question question = forum.getQuestions().get(0);

        assertThat(limited.canAnswer(question, "Question text"), is(true));
    }

    @Test
    @GradedTest("Limited users should not be able to post answers with the banned word")
    public void t12_limitedUserCannotUseBannedWordInAnswerText() {
        User user = Users.unlimited("Alice");
        LimitedUser limited = new LimitedUser(Users.unlimited("Bob"));

        forum.postQuestion(user, "Question text");
        Question question = forum.getQuestions().get(0);

        assertThat(limited.canAnswer(question, BANNED_WORD + " in answer"), is(false));
    }

    @Test
    @GradedTest("Limited users should not be able to post answers with the banned word in uppercase")
    public void t13_limitedUserCannotUseBannedWordInAnswerTextUppercase() {
        User user = Users.unlimited("Alice");
        LimitedUser limited = new LimitedUser(Users.unlimited("Bob"));

        forum.postQuestion(user, "Question text");
        Question question = forum.getQuestions().get(0);

        assertThat(limited.canAnswer(question, BANNED_WORD.toUpperCase() + " in answer"), is(false));
    }

    @Test
    @GradedTest("Limited users should not be able to post answers with the banned word in lowercase")
    public void t14_limitedUserCannotUseBannedWordInAnswerTextLowercase() {
        User user = Users.unlimited("Alice");
        LimitedUser limited = new LimitedUser(Users.unlimited("Bob"));

        forum.postQuestion(user, "Question text");
        Question question = forum.getQuestions().get(0);

        assertThat(limited.canAnswer(question, BANNED_WORD.toLowerCase() + " in answer"), is(false));
    }

    @Test
    @GradedTest("Limited users should be able to edit posts without the banned word")
    public void t15_limitedUserCanDoNormalEdit() {
        User user = Users.unlimited("Alice");
        LimitedUser limited = new LimitedUser(Users.unlimited("Bob"));

        forum.postQuestion(user, "Question text");
        Question question = forum.getQuestions().get(0);

        assertThat(limited.canEdit(question, "New question text"), is(true));
    }

    @Test
    @GradedTest("Limited users should not be able to edit posts to add the banned word")
    public void t16_limitedUserCannotUseBannedWordInEdit() {
        User user = Users.unlimited("Alice");
        LimitedUser limited = new LimitedUser(Users.unlimited("Bob"));

        forum.postQuestion(user, "Question text");
        Question question = forum.getQuestions().get(0);

        assertThat(limited.canEdit(question, "New text " + BANNED_WORD), is(false));
    }

    @Test
    @GradedTest("Limited users should not be able to edit posts to add the banned word in uppercase")
    public void t17_limitedUserCannotUseBannedWordInEditUppercase() {
        User user = Users.unlimited("Alice");
        LimitedUser limited = new LimitedUser(Users.unlimited("Bob"));

        forum.postQuestion(user, "Question text");
        Question question = forum.getQuestions().get(0);

        assertThat(limited.canEdit(question, "New text " + BANNED_WORD.toUpperCase()), is(false));
    }

    @Test
    @GradedTest("Limited users should not be able to edit posts to add the banned word in lowercase")
    public void t18_limitedUserCannotUseBannedWordInEditLowercase() {
        User user = Users.unlimited("Alice");
        LimitedUser limited = new LimitedUser(Users.unlimited("Bob"));

        forum.postQuestion(user, "Question text");
        Question question = forum.getQuestions().get(0);

        assertThat(limited.canEdit(question, "New text " + BANNED_WORD.toLowerCase()), is(false));
    }
}