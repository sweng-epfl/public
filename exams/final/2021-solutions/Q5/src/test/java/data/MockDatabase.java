package data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public final class MockDatabase implements Database {

    private final Map<String, Set<Integer>> data;

    public MockDatabase() {
        data = new HashMap<>();
        data.put("SwengFan", new HashSet<>());
    }

    @Override
    public CompletableFuture<Set<Integer>> retrieveList(String username) {
        return CompletableFuture.completedFuture(new HashSet<>(data.get(username)));
    }

    @Override
    public CompletableFuture<Void> add(String username, int videoId) {
        if (!data.containsKey(username)) {
            return CompletableFuture.failedFuture(new IllegalArgumentException("The user is not registered on the platform"));
        }

        Set<Integer> userData = data.get(username);
        userData.add(videoId);
        data.put(username, userData);

        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> remove(String username, int videoId) {
        if (!data.containsKey(username)) {
            return CompletableFuture.failedFuture(new IllegalArgumentException("The user is not registered on the platform"));
        }

        Set<Integer> userData = data.get(username);
        if (!userData.remove(videoId)) {
            return CompletableFuture.failedFuture(new IllegalArgumentException("The video was not present in that user's list"));
        }
        data.put(username, userData);
        return CompletableFuture.completedFuture(null);
    }

}