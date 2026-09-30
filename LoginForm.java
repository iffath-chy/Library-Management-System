
package com.mycompany.projectfinal;

/**
 *
 * @author iffath
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class LoginForm extends JFrame {
    // Darker purple theme colors (same as LibraryManagementGUI)
    private final Color DARK_PURPLE = new Color(51, 0, 102);
    private final Color PRIMARY_PURPLE = new Color(75, 0, 130);
    private final Color MEDIUM_PURPLE = new Color(106, 13, 173);
    private final Color ACCENT_PURPLE = new Color(138, 43, 226);
    private final Color LIGHT_PURPLE = new Color(177, 156, 217);
    private final Color VERY_LIGHT_PURPLE = new Color(230, 230, 250);
    private final Color WHITE_BG = new Color(255, 255, 255);
    private final Color BLACK_TEXT = new Color(30, 30, 30);
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton exitButton;
    
    public LoginForm() {
        setTitle("Library Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 500); // Increased width for wider fields
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Create gradient from dark purple to primary purple
                GradientPaint gradient = new GradientPaint(
                    0, 0, DARK_PURPLE,
                    getWidth(), getHeight(), PRIMARY_PURPLE
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        // Center panel for login form
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false); // Make transparent to show gradient
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 0.3;
        
        JLabel titleLabel = new JLabel("LIBRARY MANAGEMENT SYSTEM");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(titleLabel, gbc);
        
        // Subtitle
        gbc.gridy = 1;
        gbc.weighty = 0.1;
        JLabel subtitleLabel = new JLabel("Please login to continue");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(LIGHT_PURPLE);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(subtitleLabel, gbc);
        
        // Login form panel (with white background) - WIDER
        gbc.gridy = 2;
        gbc.weighty = 0.6;
        gbc.gridwidth = 2;
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(VERY_LIGHT_PURPLE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_PURPLE, 2),
            BorderFactory.createEmptyBorder(25, 35, 25, 35) // Increased horizontal padding
        ));
        
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(12, 12, 12, 12);
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username label
        formGbc.gridx = 0;
        formGbc.gridy = 0;
        formGbc.anchor = GridBagConstraints.WEST;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
        usernameLabel.setForeground(BLACK_TEXT);
        formPanel.add(usernameLabel, formGbc);
        
        // Username field - WIDER
        formGbc.gridx = 1;
        formGbc.gridy = 0;
        formGbc.weightx = 1.0; // Take available horizontal space
        formGbc.gridwidth = 1;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        usernameField = createStyledTextField(30); // Increased column count for wider field
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formPanel.add(usernameField, formGbc);
        
        // Reset weight for next row
        formGbc.weightx = 0;
        
        // Spacer between fields
        formGbc.gridx = 0;
        formGbc.gridy = 1;
        formGbc.gridwidth = 2;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(Box.createVerticalStrut(20), formGbc);
        
        // Password label
        formGbc.gridx = 0;
        formGbc.gridy = 2;
        formGbc.gridwidth = 1;
        formGbc.anchor = GridBagConstraints.WEST;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
        passwordLabel.setForeground(BLACK_TEXT);
        formPanel.add(passwordLabel, formGbc);
        
        // Password field - WIDER
        formGbc.gridx = 1;
        formGbc.gridy = 2;
        formGbc.weightx = 1.0; // Take available horizontal space
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        passwordField = createStyledPasswordField(30); // Increased column count for wider field
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formPanel.add(passwordField, formGbc);
        
        centerPanel.add(formPanel, gbc);
        
        // Button panel
        gbc.gridy = 3;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        
        // Login button
        loginButton = createStyledButton("Login", MEDIUM_PURPLE);
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
        
        // Add Enter key support for login
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
        
        // Exit button
        exitButton = createStyledButton("Exit", new Color(100, 100, 100));
        exitButton.setPreferredSize(new Dimension(120, 40));
        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    LoginForm.this,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);
        centerPanel.add(buttonPanel, gbc);
        
        // Footer with default credentials
        gbc.gridy = 4;
        gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.SOUTH;
        
        JLabel footerLabel = new JLabel("Default: admin / admin123");
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        footerLabel.setForeground(LIGHT_PURPLE);
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(footerLabel, gbc);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel);
        
        // Set focus to username field when form opens
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                usernameField.requestFocusInWindow();
            }
        });
    }
    
    private JTextField createStyledTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setBackground(WHITE_BG);
        textField.setForeground(BLACK_TEXT);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textField.setCaretColor(MEDIUM_PURPLE);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MEDIUM_PURPLE, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15) // Increased horizontal padding
        ));
        
        // Set preferred size for wider field
        textField.setPreferredSize(new Dimension(300, 35));
        
        // Add focus effect
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ACCENT_PURPLE, 2),
                    BorderFactory.createEmptyBorder(9, 14, 9, 14)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(MEDIUM_PURPLE, 1),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
        });
        
        // Ensure text is always black
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                textField.setForeground(BLACK_TEXT);
            }
        });
        
        return textField;
    }
    
    private JPasswordField createStyledPasswordField(int columns) {
        JPasswordField passwordField = new JPasswordField(columns);
        passwordField.setBackground(WHITE_BG);
        passwordField.setForeground(BLACK_TEXT);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passwordField.setCaretColor(MEDIUM_PURPLE);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MEDIUM_PURPLE, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15) // Increased horizontal padding
        ));
        
        // Set preferred size for wider field
        passwordField.setPreferredSize(new Dimension(300, 35));
        
        // Ensure password characters are black
        passwordField.setEchoChar('•');
        
        // Add focus effect
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ACCENT_PURPLE, 2),
                    BorderFactory.createEmptyBorder(9, 14, 9, 14)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                passwordField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(MEDIUM_PURPLE, 1),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
        });
        
        // Ensure password text is always black
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                passwordField.setForeground(BLACK_TEXT);
            }
        });
        
        return passwordField;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(bgColor.darker().darker());
                } else if (getModel().isRollover()) {
                    GradientPaint gradient = new GradientPaint(0, 0, bgColor.brighter(), 0, getHeight(), bgColor);
                    g2d.setPaint(gradient);
                } else {
                    GradientPaint gradient = new GradientPaint(0, 0, bgColor, 0, getHeight(), bgColor.darker());
                    g2d.setPaint(gradient);
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                super.paintComponent(g);
            }
        };
        
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setPreferredSize(new Dimension(120, 40));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ACCENT_PURPLE, 2),
                    BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 1),
                    BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
            }
        });
        
        return button;
    }
    
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        
        // Simple authentication (replace with proper authentication)
        if (username.isEmpty() || password.isEmpty()) {
            showErrorDialog("Please enter both username and password!");
            return;
        }
        
        // Default credentials (for demo purposes)
        if (username.equals("admin") && password.equals("admin123")) {
            // Successful login
            loginSuccessful();
        } else {
            // Failed login
            showErrorDialog("Invalid username or password!\n\nDefault credentials:\nUsername: admin\nPassword: admin123");
            passwordField.setText("");
            usernameField.requestFocus();
        }
    }
    
    private void loginSuccessful() {
        // Show success message
        JOptionPane.showMessageDialog(
            this,
            "Login successful!\nWelcome to Library Management System.",
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // Close login form
        this.dispose();
        
        // Open main application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LibraryManagementGUI mainGUI = new LibraryManagementGUI();
                mainGUI.setVisible(true);
                System.out.println("Main application launched.");
            }
        });
    }
    
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Login Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    // Optional: Add main method for testing the login form standalone
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            }
        });
    }
}