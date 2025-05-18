/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.javaswing;

/**
 *
 * @author Admin
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JavaSwing extends JFrame {

    private JTextField employeeNumberTxt, nameTxt, positionTxt, hourlyRateTxt, hoursWorkedTxt;
    private JButton submitButton;
    private JLabel employeeNumberLabel, nameLabel, positionLabel, grossPayLabel, netPayLabel;

    public JavaSwing() {
        setTitle("MotorPH Employee App");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        int row = 0;

        // Updated fields
        addLabelAndField("Employee Number:", employeeNumberTxt = new JTextField(), row++, gbc);
        addLabelAndField("Employee Name:", nameTxt = new JTextField(), row++, gbc);
        addLabelAndField("Position:", positionTxt = new JTextField(), row++, gbc);
        addLabelAndField("Hourly Rate (PHP):", hourlyRateTxt = new JTextField(), row++, gbc);
        addLabelAndField("Hours Worked:", hoursWorkedTxt = new JTextField(), row++, gbc);

        // Submit button
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        submitButton = new JButton("Print Information & Calculate Pay");
        add(submitButton, gbc);

        // Output labels
        gbc.gridwidth = 2;
        employeeNumberLabel = new JLabel(" ");
        nameLabel = new JLabel(" ");
        positionLabel = new JLabel(" ");
        grossPayLabel = new JLabel(" ");
        netPayLabel = new JLabel(" ");

        gbc.gridy = row++; add(employeeNumberLabel, gbc);
        gbc.gridy = row++; add(nameLabel, gbc);
        gbc.gridy = row++; add(positionLabel, gbc);
        gbc.gridy = row++; add(grossPayLabel, gbc);
        gbc.gridy = row++; add(netPayLabel, gbc);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printEmployee();
            }
        });

        setVisible(true);
    }

    private void addLabelAndField(String labelText, JTextField field, int row, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        field.setPreferredSize(new Dimension(250, 25));
        add(field, gbc);
    }

    private void printEmployee() {
    String employeeNumber = employeeNumberTxt.getText().trim();
    String name = nameTxt.getText().trim();
    String position = positionTxt.getText().trim();
    String hourlyRateStr = hourlyRateTxt.getText().trim();
    String hoursWorkedStr = hoursWorkedTxt.getText().trim();

    if (employeeNumber.isEmpty() || name.isEmpty() || position.isEmpty() ||
        hourlyRateStr.isEmpty() || hoursWorkedStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        int employeeNumInt = Integer.parseInt(employeeNumber);
        if (employeeNumInt < 0) throw new NumberFormatException("Employee number must be non-negative");

        double hourlyRate = Double.parseDouble(hourlyRateStr);
        double hoursWorked = Double.parseDouble(hoursWorkedStr);
        if (hourlyRate < 0 || hoursWorked < 0) throw new NumberFormatException("Negative pay values");

        double grossPay = hourlyRate * hoursWorked;
        double taxRate = 0.12; // 12% tax
        double netPay = grossPay * (1 - taxRate);

        employeeNumberLabel.setText("Employee Number: " + employeeNumber);
        nameLabel.setText("Employee Name: " + name);
        positionLabel.setText("Position: " + position);
        grossPayLabel.setText(String.format("Gross Pay: PHP %.2f", grossPay));
        netPayLabel.setText(String.format("Net Pay (after 12%% tax): PHP %.2f", netPay));

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this,
            "Please enter valid numeric values:\n- Employee Number must be a non-negative whole number\n- Hourly Rate and Hours Worked must be valid numbers",
            "Input Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

    public static void main(String[] args) {
        new JavaSwing();
    }
}


    