package ex6;

import java.util.LinkedList;
import java.util.List;

public class PlayersGroup implements Participant {
    public String name;
    private List<Participant> participants;

    public PlayersGroup(String name) {
        this.name = name;
        participants = new LinkedList<>();
    }

    public void add(Participant participant) {
        participants.add(participant);
    }

    @Override
    public int getNumberOfPlayers() {
        int sum = 0;
        for (Participant p: participants) {
            sum += p.getNumberOfPlayers();
        }
        return sum;
    }

    @Override
    public int getCollectedCoins() {
        int totalCoins = 0;
        for (Participant p: participants) {
            totalCoins += p.getCollectedCoins();
        }
        return totalCoins;
    }

    @Override
    public void setCollectedCoins(int collectedCoins) {
        int eachSplit = collectedCoins / participants.size();
        int leftOver = collectedCoins % participants.size();
        for (Participant p: participants) {
            p.setCollectedCoins(p.getCollectedCoins() + eachSplit + leftOver);
            leftOver = 0;
        }
    }

    @Override
    public void getStatistics() {
        for (Participant p: participants) {
            p.getStatistics();
        }
    }
}
