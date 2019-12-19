// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN change the bodies of existing methods/constructors
// You CAN add new private methods/constructors
// You CANNOT add interface implementations unless explicitly instructed to do so
// You CANNOT add new public, package-private or protected methods/constructors
// You CANNOT edit the names, parameters, checked exceptions or return types of existing methods/constructors
// You CANNOT delete existing methods/constructors
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class AppView {
    /**
     * View description, used to explain the view to users.
     */
    final String description;
    /**
     * View commands, from which the user must pick.
     */
    final List<String> commands;

    /**
     * Creates a new View.
     *
     * @throws IllegalArgumentException if and only if at least one of the following issues arises:
     *                                  the description is null, or the commands are null, or the commands are empty.
     */
    AppView(String description, List<String> commands) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null.");
        }
        if (commands == null) {
            throw new IllegalArgumentException("Commands cannot be null.");
        }
        if (commands.isEmpty()) {
            throw new IllegalArgumentException("Commands cannot be empty.");
        }

        this.description = description;
        this.commands = Collections.unmodifiableList(new ArrayList<>(commands));
    }

    /**
     * Returns a textual representation of this view.
     * --
     * The textual representation contains the description, then a line separator, then the text "Available commands:", then a line separator.
     * Then, for each command, one line containing a dash '-', a space, the command, and a line separator.
     */
    @Override
    public String toString() {
        // TODO
        throw new UnsupportedOperationException();
    }
}
