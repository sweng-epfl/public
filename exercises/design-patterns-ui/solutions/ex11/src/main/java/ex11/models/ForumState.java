package ex11.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Describes the state of the application
 */
public class ForumState {
    // state is a list of threads
    private final List<ForumThread> threadsList;

    public ForumState() {
        threadsList = new ArrayList<>();
    }

    /**
     * Adds a forum thread to the state
     * @param thread new forum thread
     */
    public void addThread(ForumThread thread) {
        threadsList.add(thread);
    }

    /**
     * Returns an immutable view of the state
     * @return the list of all existing threads
     */
    public List<ForumThread> getAllThreads() {
        return Collections.unmodifiableList(new ArrayList<>(threadsList));
    }

    /**
     * Returns thread by id
     * @return requested thread
     */
    public Optional<ForumThread> getThread(String threadId) {
        return threadsList.stream()
            .filter(thread -> thread.uid.equals(threadId))
            .findFirst();
    }
}