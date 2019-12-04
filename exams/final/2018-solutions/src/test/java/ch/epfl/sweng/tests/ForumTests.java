package ch.epfl.sweng.tests;

// !!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN edit this file.
// You CAN delete this file.
// !!!!!!!!!!!!!!!!!!!!!!!!!

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.Forum;
import ch.epfl.sweng.NoSuchPostException;
import ch.epfl.sweng.Question;
import ch.epfl.sweng.StandardUser;
import ch.epfl.sweng.User;

public final class ForumTests {
    private final User alice = new StandardUser("Alice");
    private final User bob = new StandardUser("Bob");
    private Forum forum;
    private Forum otherForum;

    @Before
    public void setup() {
        forum = new Forum();
        otherForum = new Forum();
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotPostQuestionWithNullUser() {
        forum.postQuestion(null, "Question text");
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotPostQuestionWithNullText() {
        forum.postQuestion(alice, null);
    }

    @Test
    public void cannotPostAnswerWithNullUser() {
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
    public void cannotPostAnswerWithNullQuestion() {
        forum.postAnswer(alice, null, "Answer text");
    }

    @Test
    public void cannotPostAnswerWithNullText() {
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
    public void cannotPostAnswerToQuestionOutsideForum() {
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
    public void canEditQuestion() {
        forum.postQuestion(alice, "Question text");
        Question question = forum.getQuestions().get(0);

        forum.editPost(alice, question, "New question text");

        assertThat(question.getText(), is("New question text"));
    }

    @Test
    public void cannotEditWithNullUser() {
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
    public void cannotEditWithNullPost() {
        forum.editPost(alice, null, "New post text");
    }

    @Test
    public void cannotEditQuestionOutsideForum() {
        otherForum.postQuestion(alice, "Question text");
        Question question = otherForum.getQuestions().get(0);

        try {
            forum.editPost(alice, question, "New question text");
        } catch (NoSuchPostException e) {
            return;
        }

        fail("Should have thrown a NoSuchPostException.");
    }
}