
package com.mycompany.projectfinal;

/**
 *
 * @author iffath
 */

import java.awt.*;
import javax.swing.*;

public class LibraryTest {
    public static void main(String[] args) {
        try {
            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            System.out.println("======================================");
            System.out.println("  Library Management System");
            System.out.println("======================================");
            System.out.println("Starting application...");
            
            
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    LoginForm loginForm = new LoginForm();
                    loginForm.setVisible(true);
                    System.out.println("Login form displayed.");
                    System.out.println("Default credentials:");
                    System.out.println("  Username: admin");
                    System.out.println("  Password: admin123");
                }
            });
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Error starting application: " + e.getMessage(),
                "Startup Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}