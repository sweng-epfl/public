package ch.epfl.sweng.tests;
// Namebook: CS305, SwEng Final Exam 2015.
// Author: James Larus, james.larus@epfl.ch

import ch.epfl.sweng.GraphNode;
import ch.epfl.sweng.Main;
import ch.epfl.sweng.NameBook;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class NameBookTests {

    private List<String[]> friends;

    @Before
    public void initialize() throws IOException{
        friends = Main.readFriends(new FileInputStream("friends.txt"));
    }

    @Test
    public void main() throws IOException {
        NameBook nb = new NameBook(friends);

        assertEquals(3, nb.findFriends("alice", 1).size());
        assertEquals(3, nb.findFriends("alice", 2).size());

        assertEquals(1, nb.findFriends("bob", 1).size());
        assertEquals(2, nb.findFriends("bob", 2).size());
    }

    @Test
    public void printFriendshipsTest() throws IOException {
        NameBook nb = new NameBook(friends);
        assertEquals("alice" + System.lineSeparator() + "- bob" + System.lineSeparator() + "- carol" + System.lineSeparator() + "- ted" + System.lineSeparator() +
                              "bob" + System.lineSeparator() + "- ted" + System.lineSeparator() +
                              "carol" + System.lineSeparator() +
                              "ted" + System.lineSeparator() + "- carol",
                              nb.printFriendships());

    }

    @Test
    public void findFriendTest() throws IOException {
        NameBook nb = new NameBook(friends);
        assertTrue(Arrays.asList("bob", "ted", "carol").equals(nb.findFriends("alice", 1).stream().map(GraphNode::getData).collect(Collectors.toList())));
    }

    @Test
    public void findFriendDistanceTest() throws IOException {
        NameBook nb = new NameBook(friends);
        assertTrue(Arrays.asList("bob", "ted", "carol").equals(nb.findFriends("alice", 2).stream().map(GraphNode::getData).collect(Collectors.toList())));
    }

    @Test
    public void findFriendEmptyTest() throws IOException {
        NameBook nb = new NameBook(friends);
        assertEquals(Collections.emptyList(), nb.findFriends("alice", 0));
        assertEquals(Collections.emptyList(), nb.findFriends("", 0));
    }
}