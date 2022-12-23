package model.data;

import model.util.Utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * This class represents the {@link Database} of the {@link GradedBook}.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 * DO NOT TOUCH THIS FILE <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 */
public class MangaDatabase implements Database {
    // Source: https://myanimelist.net/topmanga.php
    private final Set<GradedBook> bookSet = Set.of(
            new GradedBook("JoJo no Kimyou na Bouken Part 7: Steel Ball Run", 928),
            new GradedBook("Vagabond", 920),
            new GradedBook("Berserk", 946),
            new GradedBook("Slam Dunk", 907),
            new GradedBook("One Piece", 921),
            new GradedBook("Oyasumi Punpun", 903),
            new GradedBook("Monster", 914),
            new GradedBook("Grand Blue", 902),
            new GradedBook("Fullmetal Alchemist", 904),
            new GradedBook("Vinland Saga", 900)
    );

    private final Map<String, Set<Reaction>> reactions = new HashMap<>();

    @Override
    public CompletableFuture<Set<GradedBook>> getAllBooks() {
        return CompletableFuture.supplyAsync(() -> bookSet);
    }

    @Override
    public CompletableFuture<GradedBook> getByTitle(String title) {
        return CompletableFuture.supplyAsync(() -> {
            Utils.sleep();

            for (GradedBook m : bookSet) {
                if (m.getTitle().equals(title)) {
                    return m;
                }
            }

            throw new IllegalArgumentException("GradedBook not found: " + title);
        });
    }

    @Override
    public CompletableFuture<Void> updateGrade(String title, int update) {
        return CompletableFuture.supplyAsync(() -> {
            Utils.sleep();

            for (GradedBook m : bookSet) {
                if (m.getTitle().equals(title)) {
                    m.setGrade(m.getGrade() + update);
                    return null;
                }
            }

            throw new IllegalArgumentException("GradedBook not found: " + title);
        });
    }

    @Override
    public CompletableFuture<Void> updateReaction(String username, String title, boolean liked) {
        return CompletableFuture.supplyAsync(() -> {
            Utils.sleep();
            reactions.putIfAbsent(username, new HashSet<>());
            reactions.get(username).add(new Reaction(title, liked));
            return null;
        });
    }

    @Override
    public CompletableFuture<Set<Reaction>> getReactionsByUser(String username) {
        return CompletableFuture.supplyAsync(() -> {
            Utils.sleep();

            if (reactions.containsKey(username)) {
                return new HashSet<>(reactions.get(username));
            }

            throw new IllegalArgumentException("User not found: " + username);
        });
    }

}
