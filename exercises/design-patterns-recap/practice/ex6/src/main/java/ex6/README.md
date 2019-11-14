# Exercise 6

We simulate a game where players can collect coins.
Specifically, the players split the coins among themselves when they manage to kill a monster.
Have a look at our current game in the `App` class, where we have 5 players in total, 3 of them make up a
group (called "Developers") and the other two make up a
list of individual players.

```Java
        Player andy = new Player("Andy");
        Player jane = new Player("Jane");
        Player eve = new Player("Eve");
        Player ann = new Player("Ann");
        Player edith = new Player("Edith");

        PlayersGroup developers = new PlayersGroup("Developers");
        developers.add(andy);
        developers.add(jane);
        developers.add(eve);

        List<Player> individuals = new LinkedList<>();
        individuals.add(ann);
        individuals.add(edith);
```

Since the players managed to kill the monster, they won 1023 coins that we would like
to split between the 2 individual and the players' group.
To do this, we go through to each player and give them their share.
```Java
        for (Player ind: individuals) {
            ind.setCollectedCoins(ind.getCollectedCoins() + amountForEach + leftOver);
            leftOver = 0;
            ind.getStatistics();
            System.out.println();
        }
```


We do the same to each group as well,
where we also divide the group’s share among the player within that group.
```Java
        for (PlayersGroup gr: groups) {
            int amountForEachGroupMember = amountForEach / gr.getNumberOfPlayers();
            leftOver = amountForEachGroupMember % gr.getNumberOfPlayers();
            for (Player ind: gr.getMembers())
            {
                ind.setCollectedCoins(ind.getCollectedCoins() + amountForEachGroupMember + leftOver);
                leftOver = 0;
                ind.getStatistics();
                System.out.println();
            }
        }
```

Execute the `main` method to see how the 1023 coins are divided up.

The code works but it is quite messy. Keep in mind that our tree hierarchy is very simple:
we can have individuals and groups. Think of a more complicated scenario:
within the "Developers" group we can have subgroups, such as .NET developers or Java developers
who are further subdivided into web and desktop developers and even
individuals that do not fit into any group. In the current code (`App` class) we iterate through the
individuals and the groups manually. We also iterate the players in the group.
Imagine what would happen if we had to iterate through the subgroups of the subgroups of the group in
 case of a deeper hierarchy.
The `for` loop would keep growing and the
splitting logic would become very challenging to maintain.
Use the **composite design pattern** so that we can treat the individual part (i.e., player) and the
whole (i.e., a group of players) in a uniform way.
Do whatever is needed for the `GameTest` class to compile and pass the tests.


