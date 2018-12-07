// Namebook: CS305, SwEng Final Exam 2015.
// Author: James Larus, james.larus@epfl.ch

package ch.epfl.sweng.tests;

import ch.epfl.sweng.GraphNode;
import ch.epfl.sweng.Main;
import ch.epfl.sweng.NameBook;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public final class NameBookTests {
    private List<String[]> friends;

    @Before
    public void initialize() throws IOException{
        friends = Main.readFriends(new FileInputStream("friends.txt"));
    }

    @Test
    public void main() throws  IOException {
        NameBook nb = new NameBook(friends);

        assertEquals(3, nb.findFriends("alice", 1).size());
        assertEquals(3, nb.findFriends("alice", 2).size());

        assertEquals(1, nb.findFriends("bob", 1).size());
        assertEquals(2, nb.findFriends("bob", 2).size());
    }

    @Test
    public void printFriendships() {
        NameBook nb = new NameBook(friends);

        String result = "alice" + System.lineSeparator() +
                "- bob" + System.lineSeparator() +
                "- carol" + System.lineSeparator() +
                "- ted" + System.lineSeparator() +
                "bob" + System.lineSeparator() +
                "- ted" + System.lineSeparator() +
                "carol" + System.lineSeparator() +
                "ted" + System.lineSeparator() +
                "- carol";

        assertEquals(result, nb.printFriendships());
    }

    @Test
    public void findFriendEmptyTest() throws IOException {
        NameBook nb = new NameBook(friends);
        assertEquals(Collections.emptyList(), nb.findFriends("alice", 0));
        assertEquals(Collections.emptyList(), nb.findFriends("", 0));
    }

    @Test
    public void findFriendTest() throws IOException {
        NameBook nb = new NameBook(friends);
        List<GraphNode<String>> foundFriends = nb.findFriends("alice", 1);

        assertEquals("bob", foundFriends.get(0).getData());
        assertEquals("ted", foundFriends.get(1).getData());
        assertEquals("carol", foundFriends.get(2).getData());
    }

}