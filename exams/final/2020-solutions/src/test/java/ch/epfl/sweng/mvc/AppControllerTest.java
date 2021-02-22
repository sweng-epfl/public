package ch.epfl.sweng.mvc;

import org.junit.jupiter.api.BeforeEach;

import java.util.concurrent.atomic.AtomicBoolean;

import ch.epfl.sweng.Student;
import ch.epfl.sweng.database.DatabaseException;
import ch.epfl.sweng.mocks.StudentDatabaseMock;
import grading.GradedCategory;
import grading.GradedTest;

import static ch.epfl.sweng.utils.AssertUtils.assertHandlesNullArgument;
import static ch.epfl.sweng.utils.AssertUtils.assertStringContains;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.fail;

@GradedCategory("Part 2.2: Implementing the AppController")
public final class AppControllerTest {

    private StudentDatabaseMock database;
    private AppController controller;

    @BeforeEach
    public void init() {
        database = new StudentDatabaseMock();
        controller = new AppController(new AppModel(database), new AppView());
    }

    @GradedTest("`constructor` should throw an IllegalArgumentException or a NullPointerException if the first argument is null")
    public void constructorThrowOnNullFirstArgument() {
        assertHandlesNullArgument(() -> new AppController(null, new AppView()));
    }

    @GradedTest("`constructor` should throw an IllegalArgumentException or a NullPointerException if the second argument is null")
    public void constructorThrowOnNullSecondArgument() {
        assertHandlesNullArgument(() -> new AppController(new AppModel(database), null));
    }

    @GradedTest("`handleRequest` should throw an IllegalArgumentException of a NullPointerException if the argument is null")
    public void handleRequestThrowOnNullArgument() {
        assertHandlesNullArgument(() -> controller.handleRequest(null));
    }

    @GradedTest("`handleRequest` should return the correct error message if the command is invalid")
    public void handleRequestReturnCorrectErrorOnInvalidCommand() {
        String response;
        response = controller.handleRequest("");
        assertStringContains(response, "Error", "Invalid", "command");

        response = controller.handleRequest("remove123456");
        assertStringContains(response, "Error", "Invalid", "command");

        response = controller.handleRequest("jump to light speed");
        assertStringContains(response, "Error", "Invalid", "command");

        response = controller.handleRequest("123456 get");
        assertStringContains(response, "Error", "Invalid", "command");
    }

    @GradedTest("`handleRequest` should return the correct error message if the put command does not have the correct number of arguments")
    public void handleRequestReturnCorrectErrorOnInvalidPutCommand() {
        String response;
        response = controller.handleRequest("put");
        assertStringContains(response, "Error", "Invalid", "number", "argument", "put", "command");

        response = controller.handleRequest("put John");
        assertStringContains(response, "Error", "Invalid", "number", "argument", "put", "command");

        response = controller.handleRequest("put John 123456");
        assertStringContains(response, "Error", "Invalid", "number", "argument", "put", "command");

        response = controller.handleRequest("put John 123456 IC IN");
        assertStringContains(response, "Error", "Invalid", "number", "argument", "put", "command");
    }

    @GradedTest("`handleRequest` should return the correct error message if the put operation fails")
    public void handleRequestReturnCorrectErrorOnPutFailure() {
        database.onPut(student -> {
            throw new DatabaseException("simulated database exception");
        });

        String response = controller.handleRequest("put John 123456 IC");
        assertStringContains(response, "Error", "Database", "failed", "insert", "student");
    }

    @GradedTest("`handleRequest` should return the correct success message if the put operation succeeds")
    public void handleRequestReturnSuccessOnValidPutCommand() {
        String sciper = "523476";
        String response;
        response = controller.handleRequest("put John " + sciper + " IC");
        assertStringContains(response, "Success", "Successfully", "added", sciper);
    }

    @GradedTest("`handleRequest` should put the correct student in the database")
    public void handleRequestPutStudentCorrectly() {
        Student student = new Student("John", "493756", "IC");

        final AtomicBoolean execFlag = new AtomicBoolean(false);
        database.onPut(s -> {
            assertThat(s, is(student));
            execFlag.set(true);
        });

        controller.handleRequest("put " + student.name + " " + student.sciper + " " + student.faculty);
        assertThat("Database.put was not executed", execFlag.get());
    }

    @GradedTest("`handleRequest` should return the correct error message if the remove command does not have the correct number of arguments")
    public void handleRequestReturnCorrectErrorOnInvalidRemoveCommand() {
        String response;
        response = controller.handleRequest("remove");
        assertStringContains(response, "Error", "Invalid", "number", "argument", "remove", "command");

        response = controller.handleRequest("remove 123456 234567");
        assertStringContains(response, "Error", "Invalid", "number", "argument", "remove", "command");
    }

    @GradedTest("`handleRequest` should return the correct error message if the remove operation fails")
    public void handleRequestReturnCorrectErrorOnRemoveFailure() {
        String response = controller.handleRequest("remove 123456");
        assertStringContains(response, "Error", "Database", "failed", "remove", "student");
    }

    @GradedTest("`handleRequest` should return the correct success message if the remove operation succeeds")
    public void handleRequestReturnSuccessOnValidRemoveCommand() {
        String sciper = "523476";
        database.onRemove(s -> {});

        String response;
        response = controller.handleRequest("remove " + sciper);
        assertStringContains(response, "Success", "Successfully", "removed", sciper);
    }

    @GradedTest("`handleRequest` should remove the correct student from the database")
    public void handleRequestRemoveStudentCorrectly() {
        String sciper = "532152";

        final AtomicBoolean execFlag = new AtomicBoolean(false);
        database.onRemove(s -> {
            assertThat(s, is(sciper));
            execFlag.set(true);
        });

        controller.handleRequest("remove " + sciper);
        assertThat("Database.remove was not executed", execFlag.get());
    }

    @GradedTest("`handleRequest` should return the correct error message if the get command does not have the correct number of arguments")
    public void handleRequestReturnCorrectErrorOnInvalidGetCommand() {
        String response;
        response = controller.handleRequest("get");
        assertStringContains(response, "Error", "Invalid", "number", "argument", "get", "command");

        response = controller.handleRequest("get 123456 234567");
        assertStringContains(response, "Error", "Invalid", "number", "argument", "get", "command");
    }

    @GradedTest("`handleRequest` should return the correct error message if a database exception occurs in the get command")
    public void handleRequestReturnCorrectErrorOnDatabaseException() {
        database.onGet(sciper -> {
            throw new DatabaseException("simulated database exception");
        });

        String response;
        response = controller.handleRequest("get 123456");
        assertStringContains(response, "Error", "Database", "failed", "retrieve");
    }

    @GradedTest("`handleRequest` should return the correct message when displaying a get result")
    public void handleRequestReturnCorrectMessageOnGetResult() {
        String name = "John";
        String sciper = "582976";
        String faculty = "GC";

        database.onGet(s -> new Student(name, sciper, faculty));

        String result;
        result = controller.handleRequest("get " + sciper);
        assertStringContains(result, "Success", "Found", name, sciper, faculty, "ms");
    }

    @GradedTest("`handleRequest` should ignore leading and trailing spaces in the input")
    public void handleRequestIgnoreWhitespacesInCommand() {
        String name = "John";
        String sciper = "523476";
        String faculty = "GC";

        database.onGet(s -> new Student(name, sciper, faculty));
        database.onRemove(s -> {});

        String response;

        response = controller.handleRequest("\t   put John " + sciper + " IC   \t");
        assertStringContains(response, "Success", "Successfully", "added", sciper);

        response = controller.handleRequest("\t   get " + sciper + "   ");
        assertStringContains(response, "Success", "Found", name, sciper, faculty, "ms");

        response = controller.handleRequest("\tremove " + sciper);
        assertStringContains(response, "Success", "Successfully", "removed", sciper);
    }

}
