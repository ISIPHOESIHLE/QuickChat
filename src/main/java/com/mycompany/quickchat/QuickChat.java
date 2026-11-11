/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quickchat;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class QuickChat {
static Scanner input = new Scanner(System.in);
    static String cellPhoneNumber;
    static String username;
    static String password;

    public static boolean checkUsername(String username) {
        if (username != null && username.contains("_") && username.length() <= 5) {
            System.out.println("Username successfully captured.");
            return true;
        } else {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than 5 characters in length.");
            return false;
        }
    }

    public static boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUpper = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }
        return hasUpper && hasNumber && hasSpecial;
    }

    public static boolean checkCellPhoneNumber(String cellNumber) {
        return cellNumber != null && cellNumber.matches("(\\+27|0)[0-9]{9}");
    }

    public static String registerUser() {
        System.out.print("Enter username: ");
        String u = input.nextLine();
        if (!checkUsername(u)) {
            return "Registration failed: Invalid username.";
        }
        username = u;

        System.out.print("Enter password: ");
        String p = input.nextLine();
        if (!checkPasswordComplexity(p)) {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.");
            return "Registration failed: Invalid password.";
        }
        password = p;
        System.out.println("Password successfully captured.");

        System.out.print("Enter cell phone number: ");
        String c = input.nextLine();
        if (!checkCellPhoneNumber(c)) {
            System.out.println("Cell phone number is incorrectly formatted or does not contain a valid international code.");
            return "Registration failed: Invalid cell number.";
        }
        cellPhoneNumber = c;
        System.out.println("Cell phone number successfully captured.");

        return "User registered successfully.";
    }

    public static boolean loginUser(String enteredUser, String enteredPass) {
        return enteredUser != null && enteredUser.equals(username) && enteredPass != null && enteredPass.equals(password);
    }

    public static String returnLoginStatus(boolean loggedIn, String user) {
        if (loggedIn) {
            return "Login successful. Welcome back, " + user + "!";
        } else {
            return "Login failed. Invalid username or password.";
        }
    }

    public static void initializeTestData() {
        new Message("+27834557896", "Did you get the cake?", "Sent", 1);
        new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored", 2);
        new Message("+27834484567", "Yohoooo, I am at your gate.", "Disregard", 3);
        new Message("0838884567", "It is dinner time!", "Sent", 4);
        new Message("+27838884567", "Ok, I am leaving without you.", "Stored", 5);
    }

    public static void main(String[] args) {
        initializeTestData();

        String regStatus = registerUser();
        System.out.println(regStatus);
        if (!regStatus.equals("User registered successfully.")) {
            return;
        }

        System.out.println("\n--- LOGIN SCREEN ---");
        System.out.print("Enter username: ");
        String u = input.nextLine();
        System.out.print("Enter password: ");
        String p = input.nextLine();

        boolean loggedIn = loginUser(u, p);
        System.out.println(returnLoginStatus(loggedIn, u));
        if (!loggedIn) {
            return;
        }

        System.out.println("\nWelcome to QuickChat.");
        boolean running = true;
        int counter = Message.sentMessagesList.size() + Message.storedMessagesList.size() + Message.disregardedMessagesList.size();

        while (running) {
            System.out.println("\n--- QUICKCHAT MENU ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Stored Messages");
            System.out.println("4) Quit");
            System.out.print("Choose an option: ");
            
            int choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {
                System.out.print("How many messages? ");
                int total = input.nextInt();
                input.nextLine();

                for (int i = 1; i <= total; i++) {
                    System.out.println("\nMessage " + i);
                    
                    System.out.print("Recipient cell: ");
                    String cell = input.nextLine();
                    while (!Message.checkRecipientCell(cell)) {
                        System.out.print("Invalid format. Enter cell: ");
                        cell = input.nextLine();
                    }

                    System.out.print("Enter message (max 250 chars): ");
                    String text = input.nextLine();
                    while (text.length() > 250) {
                        System.out.println("Please enter a message of less than 250 characters.");
                        System.out.print("Enter message: ");
                        text = input.nextLine();
                    }
                    System.out.println("Message ready to send.");

                    counter++;
                    System.out.println("1) Send | 0) Discard | 2) Store");
                    int action = input.nextInt();
                    input.nextLine();

                    System.out.println(Message.sentMessage(action));
                    
                    String flag = "Sent";
                    if (action == 0) flag = "Disregard";
                    else if (action == 2) flag = "Stored";

                    Message msgObj = new Message(cell, text, flag, counter);

                    if (action == 1 || action == 2) {
                        System.out.println("\n" + Message.printMessages(msgObj.getMessageID(), msgObj.getMessageHash(), cell, text));
                    }

                    if (action == 2) {
                        Message.storeMessage(Message.storedMessagesList, "quickChatMessageStorage.json");
                    }
                }
                System.out.println("\nTotal sent: " + Message.returnTotalMessages());

            } else if (choice == 2) {
                System.out.println("\n--- RECENTLY SENT MESSAGES ---");
                if (Message.sentMessagesList.isEmpty()) {
                    System.out.println("No messages have been sent yet.");
                } else {
                    for (Message m : Message.sentMessagesList) {
                        System.out.println("--------------------------------");
                        System.out.println("Recipient: " + m.getRecipientNumber() + "\nMessage: " + m.getMessageContent());
                    }
                    System.out.println("--------------------------------");
                }
            } else if (choice == 3) {
                boolean subRunning = true;
                while (subRunning) {
                    System.out.println("\n--- STORED MESSAGES MENU ---");
                    System.out.println("a) Display sender and recipient of all stored messages");
                    System.out.println("b) Display the longest stored message");
                    System.out.println("c) Search for a message ID");
                    System.out.println("d) Search for messages for a particular recipient");
                    System.out.println("e) Delete a message using a message hash");
                    System.out.println("f) Display full report of stored messages");
                    System.out.println("g) Return to main menu");
                    System.out.print("Choose an option (a-g): ");
                    String subChoice = input.nextLine().trim().toLowerCase();

                    switch (subChoice) {
                        case "a":
                            System.out.println("\n--- SENDER & RECIPIENT OF STORED MESSAGES ---");
                            if (Message.storedMessagesList.isEmpty()) {
                                System.out.println("No stored messages available.");
                            } else {
                                for (Message m : Message.storedMessagesList) {
                                    System.out.println("Sender: " + username + " | Recipient: " + m.getRecipientNumber());
                                }
                            }
                            break;
                        case "b":
                            System.out.println("\n--- LONGEST STORED MESSAGE ---");
                            String longest = Message.getLongestMessage(Message.storedMessagesList);
                            if (longest.isEmpty()) {
                                System.out.println("No stored messages available.");
                            } else {
                                System.out.println(longest);
                            }
                            break;
                        case "c":
                            System.out.print("Enter Message ID to search: ");
                            String searchId = input.nextLine();
                            Message foundMsg = Message.searchByMessageID(searchId);
                            if (foundMsg != null) {
                                System.out.println("Recipient: " + foundMsg.getRecipientNumber() + " | Message: " + foundMsg.getMessageContent());
                            } else {
                                System.out.println("Message ID not found.");
                            }
                            break;
                        case "d":
                            System.out.print("Enter recipient phone number: ");
                            String searchRecip = input.nextLine();
                            List<String> foundTexts = Message.searchByRecipient(searchRecip);
                            if (foundTexts.isEmpty()) {
                                System.out.println("No messages found for this recipient.");
                            } else {
                                System.out.println("Messages for " + searchRecip + ":");
                                for (String txt : foundTexts) {
                                    System.out.println("- " + txt);
                                }
                            }
                            break;
                        case "e":
                            System.out.print("Enter message hash (or content) to delete: ");
                            String hashDel = input.nextLine();
                            boolean deleted = Message.deleteMessageByHash(hashDel);
                            if (deleted) {
                                System.out.println("Message successfully deleted.");
                            } else {
                                System.out.println("Message not found.");
                            }
                            break;
                        case "f":
                            System.out.println("\n--- FULL STORED MESSAGES REPORT ---");
                            if (Message.storedMessagesList.isEmpty()) {
                                System.out.println("No stored messages available.");
                            } else {
                                for (Message m : Message.storedMessagesList) {
                                    System.out.println(Message.printMessages(m.getMessageID(), m.getMessageHash(), m.getRecipientNumber(), m.getMessageContent()));
                                    System.out.println("--------------------------------");
                                }
                            }
                            break;
                        case "g":
                            subRunning = false;
                            break;
                        default:
                            System.out.println("Invalid option. Choose between a and g.");
                    }
                }
            } else if (choice == 4) {
                System.out.println("Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}
    

