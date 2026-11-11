/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;
import java.util.Random;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author LENOVO
 */
public class Message {
 private String messageID;
    private String recipientNumber;
    private String messageContent;
    private String messageHash;
    private static int totalMessagesSent = 0;
    private String flag;

    public static List<Message> sentMessagesList = new ArrayList<>();
    public static List<Message> disregardedMessagesList = new ArrayList<>();
    public static List<Message> storedMessagesList = new ArrayList<>();
    public static List<String> messageIDList = new ArrayList<>();
    public static List<String> messageHashList = new ArrayList<>();

    public Message(String recipientNumber, String messageContent, String flag) {
        this.messageID = createMessageID();
        this.recipientNumber = recipientNumber;
        this.messageContent = messageContent;
        this.messageHash = createMessageHash(this.messageID, storedMessagesList.size() + 1, messageContent);
        this.flag = flag;

        if (flag.equalsIgnoreCase("Sent")) {
            sentMessagesList.add(this);
        } else if (flag.equalsIgnoreCase("Disregard") || flag.equalsIgnoreCase("Disregarded")) {
            disregardedMessagesList.add(this);
        } else if (flag.equalsIgnoreCase("Stored")) {
            storedMessagesList.add(this);
        }
        messageIDList.add(this.messageID);
        messageHashList.add(this.messageHash);
    }

    public String getMessageID() { return messageID; }
    public String getRecipientNumber() { return recipientNumber; }
    public String getMessageContent() { return messageContent; }
    public String getMessageHash() { return messageHash; }
    public String getFlag() { return flag; }

    public static boolean checkMessageID(String messageID) {
        return messageID != null && messageID.length() <= 10;
    }

    public static boolean checkRecipientCell(String cellNumber) {
        return cellNumber != null && cellNumber.matches("(\\+27|0)[0-9]{9}") && cellNumber.length() <= 12;
    }

    public static String createMessageID() {
        Random rand = new Random();
        long id = 1000000000L + rand.nextLong(9000000000L);
        return String.valueOf(id);
    }

    public static String createMessageHash(String messageID, int messageCounter, String message) {
        String prefix = messageID.length() >= 2 ? messageID.substring(0, 2) : "12";
        String[] words = message.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        
        String combined = (firstWord + lastWord).replaceAll("[^a-zA-Z]", "").toUpperCase();
        if (combined.length() > 6) {
            combined = combined.substring(0, 6);
        }
        
        return prefix + ":" + messageCounter + ":" + combined;
    }

    public static String getLongestMessage(List<Message> list) {
        if (list == null || list.isEmpty()) return "";
        Message longest = list.get(0);
        for (Message m : list) {
            if (m.getMessageContent().length() > longest.getMessageContent().length()) {
                longest = m;
            }
        }
        return longest.getMessageContent();
    }

    public static Message searchByMessageID(String id) {
        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(sentMessagesList);
        allMessages.addAll(storedMessagesList);
        allMessages.addAll(disregardedMessagesList);

        for (Message m : allMessages) {
            if (m.getMessageID().equals(id)) {
                return m;
            }
        }
        return null;
    }

    public static List<String> searchByRecipient(String recipient) {
        List<String> results = new ArrayList<>();
        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(sentMessagesList);
        allMessages.addAll(storedMessagesList);
        allMessages.addAll(disregardedMessagesList);

        for (Message m : allMessages) {
            if (m.getRecipientNumber().equals(recipient)) {
                results.add(m.getMessageContent());
            }
        }
        return results;
    }

    public static boolean deleteMessageByHash(String hashOrContent) {
        List<Message> allLists[] = new List[]{sentMessagesList, storedMessagesList, disregardedMessagesList};
        for (List<Message> list : allLists) {
            for (int i = 0; i < list.size(); i++) {
                Message m = list.get(i);
                if (m.getMessageHash().equalsIgnoreCase(hashOrContent) || m.getMessageContent().equalsIgnoreCase(hashOrContent)) {
                    list.remove(i);
                    return true;
                }
            }
        }
        return false;
    }
}
