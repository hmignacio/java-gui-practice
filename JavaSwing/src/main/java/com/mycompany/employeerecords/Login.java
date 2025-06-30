package com.mycompany.employeerecords;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Login extends JFrame implements ActionListener {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;
    
    
    private Map<String, String> userCredentials = new HashMap<>();
    

    public Login() {
        loadUsersFromCSV("src/docs/EmployeeAccounts.csv");
        
        setTitle("MotorPH Payroll System - Login");
        setSize(450, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        JLabel titleLabel = new JLabel("MotorPH Employee Portal");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 5, 10); 
        panel.add(titleLabel, gbc);

        // Black Separator 
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.BLACK);
        separator.setPreferredSize(new Dimension(1, 2));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(separator, gbc);

        // Username Label
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(new JLabel("Employee Number:"), gbc);

        // Username Field
        userField = new JTextField(18);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(userField, gbc);

        // Password Label
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(new JLabel("Password:"), gbc);

        // Password Field
        passField = new JPasswordField(18);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(passField, gbc);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(46, 204, 113)); // green
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(loginButton, gbc);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String employeeNumber = userField.getText().trim();
        String password = new String(passField.getPassword()).trim();

        if (userCredentials.containsKey(employeeNumber) && userCredentials.get(employeeNumber).equals(password)) {
            dispose();
            SwingUtilities.invokeLater(() -> new EmployeeRecords(employeeNumber).setVisible(true));
        } else {
            JOptionPane.showMessageDialog(this, "Invalid employee number or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadUsersFromCSV(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean skipHeader = true;

            while ((line = br.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String employeeNumber = parts[0].trim();
                    String password = parts[1].trim();
                    userCredentials.put(employeeNumber, password);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to load EmployeeAccounts.csv\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}
