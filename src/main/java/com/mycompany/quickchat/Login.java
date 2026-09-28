/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;
import java.util.Scanner;
/**
 *
 * @author LENOVO
 */
public class Login {
    private static Scanner input = new Scanner(System.in);
    private static String registeredUsername;
    private static String registeredPassword;
    private static String registeredCellPhoneNumber;

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
            return "Registration failed.";
        }
        registeredUsername = u;

        System.out.print("Enter password: ");
        String p = input.nextLine();
        if (!checkPasswordComplexity(p)) {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.");
            return "Registration failed.";
        }
        registeredPassword = p;
        System.out.println("Password successfully captured.");

        System.out.print("Enter cell phone number: ");
        String c = input.nextLine();
        if (!checkCellPhoneNumber(c)) {
            System.out.println("Cell phone number is incorrectly formatted or does not contain a valid international code.");
            return "Registration failed.";
        }
        registeredCellPhoneNumber = c;
        System.out.println("Cell phone number successfully captured.");

        return "User registered successfully.";
    }

    public static boolean loginUser(String enteredUsername, String enteredPassword) {
        return enteredUsername.equals(registeredUsername) && enteredPassword.equals(registeredPassword);
    }

    public static String returnLoginStatus(boolean isLoggedIn, String username) {
        if (isLoggedIn) {
            return "Welcome " + username + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}

