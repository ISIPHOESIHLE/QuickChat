/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.mycompany.quickchat.Message;
/**
 *
 * @author LENOVO
 */
public class MessageTest {
    
    @Test
    public void testCheckRecipientCell_Valid() {
        assertTrue(Message.checkRecipientCell("+27831234567"));
        assertTrue(Message.checkRecipientCell("0831234567"));
    }

    @Test
    public void testCheckRecipientCell_Invalid() {
        assertFalse(Message.checkRecipientCell("12345"));
        assertFalse(Message.checkRecipientCell("abc"));
    }

    @Test
    public void testCreateMessageID() {
        String id = Message.createMessageID();
        assertNotNull(id);
        assertTrue(Message.checkMessageID(id));
    }

    @Test
    public void testCreateMessageHash() {
        String id = "1234567890";
        String hash = Message.createMessageHash(id, 1, "Hello World");
        assertNotNull(hash);
        assertTrue(hash.startsWith("12:"));
    }
}