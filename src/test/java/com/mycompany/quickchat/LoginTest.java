/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.quickchat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author LENOVO
 */
public class LoginTest {
    
    @Test
    public void testCheckUsernameValid() {
        // Test a username with an underscore and <= 5 characters
        assertTrue(Login.checkUsername("kyl_1"));
    }

    @Test
    public void testCheckUsernameInvalid() {
        // Test a username missing an underscore or too long
        assertFalse(Login.checkUsername("kylestring"));
    }

    @Test
    public void testCheckPasswordComplexityValid() {
        // Test a password with >= 8 chars, 1 uppercase, 1 number, 1 special char
        assertTrue(Login.checkPasswordComplexity("Ch@t1234"));
    }

    @Test
    public void testCheckPasswordComplexityInvalid() {
        // Test a password missing a special character or too short
        assertFalse(Login.checkPasswordComplexity("password123"));
    }
}