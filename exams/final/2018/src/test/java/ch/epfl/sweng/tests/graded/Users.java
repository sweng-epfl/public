package ch.epfl.sweng.tests.graded;

import ch.epfl.sweng.Observer;
import ch.epfl.sweng.Post;
import ch.epfl.sweng.Question;
import ch.epfl.sweng.User;

public final class Users {
    public static User unlimited(String name) {
        return new User() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public boolean canAsk(String text) {
                return true;
            }

            @Override
            public boolean canAnswer(Question question, String text) {
                return true;
            }

            @Override
            public boolean canEdit(Post post, String text) {
                return true;
            }

            // In case User implements Observable
            public void notifyObservers(Object arg) {
                // Nothing
            }

            public void addObserver(Observer o) {
                // Nothing
            }
        };
    }

    public static User limited(String name) {
        return new User() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public boolean canAsk(String text) {
                return false;
            }

            @Override
            public boolean canAnswer(Question question, String text) {
                return false;
            }

            @Override
            public boolean canEdit(Post post, String text) {
                return false;
            }

            // In case User implements Observable
            public void notifyObservers(Object arg) {
                // Nothing
            }

            public void addObserver(Observer o) {
                // Nothing
            }
        };
    }
}