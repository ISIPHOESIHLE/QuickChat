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
        if (username.contains("_") && username.length() <= 5) {
            System.out.println("Username successfully captured.");
            return true;
        } else {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than 5 characters in length.");
            return false;
        }
    }

    public static boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) {
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
        return cellNumber.matches("(\\+27|0)[0-9]{9}");
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
        return enteredUser.equals(username) && enteredPass.equals(password);
    }
    public static void main(String[] args) {
     Scanner input = new Scanner(System.in);
        List<Message> savedMessages = new ArrayList<>();

        String regStatus = Login.registerUser();
        System.out.println(regStatus);
        if (!regStatus.equals("User registered successfully.")) {
            return;
        }

        System.out.println("\n--- LOGIN SCREEN ---");
        System.out.print("Enter username: ");
        String u = input.nextLine();
        System.out.print("Enter password: ");
        String p = input.nextLine();

        boolean loggedIn = Login.loginUser(u, p);
        System.out.println(Login.returnLoginStatus(loggedIn, u));
        if (!loggedIn) {
            return;
        }

        System.out.println("\nWelcome to QuickChat.");
        boolean running = true;
        int counter = 0;

        while (running) {
            System.out.println("\n--- QUICKCHAT MENU ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
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
                    String id = Message.createMessageID();
                    String hash = Message.createMessageHash(id, counter, text);

                    Message msgObj = new Message(cell, text, counter);

                    System.out.println("1) Send | 0) Discard | 2) Store");
                    int action = input.nextInt();
                    input.nextLine();

                    System.out.println(Message.sentMessage(action));
                    
                    if (action == 1 || action == 2) {
                        System.out.println("\n" + Message.printMessages(id, hash, cell, text));
                    }

                    if (action == 2) {
                        savedMessages.add(msgObj);
                        Message.storeMessage(savedMessages, "quickChatMessageStorage.json");
                    }
                }
                System.out.println("\nTotal sent: " + Message.returnTotalMessages());

            } else if (choice == 2) {
              System.out.println("\n--- RECENTLY STORED MESSAGES ---");
                if (savedMessages.isEmpty()) {
                    System.out.println("No messages have been stored yet.");
                } else {
                    for (Message m : savedMessages) {
                        System.out.println("--------------------------------");                    
                        System.out.println("Recipient: " + m);
                    }
                    System.out.println("--------------------------------");
                }
            } else if (choice == 3) {
                System.out.println("Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}
    

