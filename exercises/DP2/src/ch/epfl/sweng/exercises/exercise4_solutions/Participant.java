package ch.epfl.sweng.exercises.exercise4_solutions;

public interface Participant {

    public void add(Participant participant);

    public int getCollectedCoins();

    public int getNumberOfPlayers();

    public void setCollectedCoins(int collectedCoins);

    public void getStatistics();
}
