package ex6;

import java.util.LinkedList;
import java.util.List;

public class PlayersGroup {
    public String name;
    private List<Player> players;

    public PlayersGroup(String name) {
        this.name = name;
        players = new LinkedList<>();
    }

    public void add(Player player) {
        players.add(player);
    }

    public int getNumberOfPlayers() {
        return players.size();
    }

    public Iterable<? extends Player> getMembers() {
        return players;
    }
}
