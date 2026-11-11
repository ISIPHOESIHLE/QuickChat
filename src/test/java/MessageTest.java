/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.quickchat.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
/**
 *
 * @author LENOVO
 */
public class MessageTest {
    
   @BeforeEach
    public void setUp() {
        Message.sentMessagesList.clear();
        Message.storedMessagesList.clear();
        Message.disregardedMessagesList.clear();
        Message.messageIDList.clear();
        Message.messageHashList.clear();

        new Message("+27834557896", "Did you get the cake?", "Sent", 1);
        new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored", 2);
        new Message("+27834484567", "Yohoooo, I am at your gate.", "Disregard", 3);
        new Message("0838884567", "It is dinner time!", "Sent", 4);
        new Message("+27838884567", "Ok, I am leaving without you.", "Stored", 5);
    }

    @Test
    public void testSentMessagesArrayPopulated() {
        assertEquals(2, Message.sentMessagesList.size());
        assertEquals("Did you get the cake?", Message.sentMessagesList.get(0).getMessageContent());
        assertEquals("It is dinner time!", Message.sentMessagesList.get(1).getMessageContent());
    }

    @Test
    public void testDisplayLongestMessage() {
        String longest = Message.getLongestMessage(Message.storedMessagesList);
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    @Test
    public void testSearchByMessageID() {
        Message m4 = Message.sentMessagesList.get(1);
        Message found = Message.searchByMessageID(m4.getMessageID());
        assertNotNull(found);
        assertEquals("It is dinner time!", found.getMessageContent());
    }

    @Test
    public void testSearchByRecipient() {
        List<String> results = Message.searchByRecipient("+27838884567");
        assertEquals(2, results.size());
        assertTrue(results.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(results.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteMessageByHash() {
        String hashToDelete = Message.storedMessagesList.get(0).getMessageHash();
        boolean deleted = Message.deleteMessageByHash(hashToDelete);
        assertTrue(deleted);
        assertEquals(1, Message.storedMessagesList.size());
    }
}