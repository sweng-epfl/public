package data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import util.AsyncUtil;

/**
 * Remote implementation of a database.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class RemoteDatabase implements Database {

    private Map<String, Set<Integer>> data;

    public RemoteDatabase() {
        data = new HashMap<>();
        data.put("SwengFan", new HashSet<>());
    }

    @Override
    public CompletableFuture<Set<Integer>> retrieveList(String username) {
        return CompletableFuture.supplyAsync(() -> {
            AsyncUtil.simulateAsync();
            return new HashSet<>(data.get(username));
        });
    }

    @Override
    public CompletableFuture<Void> add(String username, int videoId) {
        return CompletableFuture.supplyAsync(() -> {
            AsyncUtil.simulateAsync();
            if (!data.containsKey(username)) {
                throw new IllegalArgumentException("The user is not registered on the platform");
            }
            Set<Integer> userData = data.get(username);
            userData.add(videoId);
            data.put(username, userData);
            return null;
        });
    }

    @Override
    public CompletableFuture<Void> remove(String username, int videoId) {
        return CompletableFuture.supplyAsync(() -> {
            AsyncUtil.simulateAsync();
            if (!data.containsKey(username)) {
                throw new IllegalArgumentException("The user is not registered on the platform");
            }
            Set<Integer> userData = data.get(username);
            if (!userData.remove(videoId)) {
                throw new IllegalArgumentException("The video was not present in that user's list");
            }
            data.put(username, userData);
            return null;
        });
    }

}
