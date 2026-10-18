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

    public Message(String recipientNumber, String messageContent, int messageCounter) {
        
        this.messageID = createMessageID();
        this.recipientNumber = recipientNumber;
        this.messageContent = messageContent;
        this.messageHash = createMessageHash(this.messageID, messageCounter, messageContent);
    }

    public static boolean checkMessageID(String messageID) {
        return messageID != null && messageID.length() <= 10;
    }
    public static boolean checkRecipientCell(String cellNumber) {
        return cellNumber.matches("(\\+27|0)[0-9]{9}") && cellNumber.length() <= 12;
    }

    public static String createMessageID() {
        Random rand = new Random();
        long id = 1000000000L + rand.nextLong(9000000000L);
        return String.valueOf(id);
    }

    public static String createMessageHash(String messageID, int messageCounter, String message) {
        String prefix = messageID.substring(0, 2);
   
        String[] words = message.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        
        String combined = (firstWord + lastWord).replaceAll("[^a-zA-Z]", "").toUpperCase();
        if (combined.length() > 6) {
            combined = combined.substring(0, 6);
        }
        
        return prefix + ":" + messageCounter + ":" + combined;
    }

    public static String sentMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                return "Message successfully sent.";
            case 0:
                return "Press 0 to delete the message.";
            case 2:
                return "Message successfully stored.";
            default:
                return "Invalid option selected.";
        }
    }

    public static String printMessages(String messageID, String messageHash, String recipientNumber, String messageContent) {
        return "--- MESSAGE DETAILS ---\n" +
               "Message ID: " + messageID + "\n" +
               "Message Hash: " + messageHash + "\n" +
               "Recipient: " + recipientNumber + "\n" +
               "Message: " + messageContent;
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
   public static void storeMessage(java.util.List<Message> messageList, String filename) {
        try {
            com.google.gson.Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
            String jsonOutput = gson.toJson(messageList);
            java.nio.file.Files.writeString(java.nio.file.Paths.get(filename), jsonOutput);
            System.out.println("Message successfully stored.");
        } catch (Exception e) {
            System.out.println("Error saving messages to JSON: " + e.getMessage());
        }
    }


   
}
