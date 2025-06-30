/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.employeerecords;

/**
 *
 * @author Admin
 */
import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AddEmployee extends JFrame {
    
    private EmployeeRecords parent;
    
    private JPanel contentPanel;

    private JTextField txtEmployeeId, txtLastName, txtFirstName, txtBirthDate, txtAddress, txtPhone,
            txtSSS, txtPhilhealth, txtTIN, txtPagIbig, txtStatus, txtPosition, txtSupervisor,
            txtSalary, txtRiceSubsidy, txtPhoneAllowance, txtClothingAllowance,
            txtGrossRate, txtHourlyRate;

    public AddEmployee(EmployeeRecords parent) {
        setTitle("Add New Employee");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header label
        JLabel headerLabel = new JLabel("Employee Details");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        separator.setMaximumSize(new Dimension(600, 2));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel separatorPanel = new JPanel(new BorderLayout());
        separatorPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); 
        separatorPanel.add(separator, BorderLayout.CENTER);

        
        // Top panel with vertical BoxLayout
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(headerLabel);
        topPanel.add(separatorPanel);

        add(topPanel, BorderLayout.NORTH);
        
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        

        String[] labels = {
            "Employee ID", "Last Name", "First Name", "Birth Date", "Address", "Phone Number",
            "SSS Number", "PhilHealth Number", "TIN", "Pag-IBIG Number", "Status", "Position",
            "Supervisor", "Salary", "Rice Subsidy", "Phone Allowance", "Clothing Allowance",
            "Gross Rate", "Hourly Rate"
        };

        JTextField[] textFields = new JTextField[labels.length];

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            panel.add(new JLabel(labels[i] + ":"), gbc);

            gbc.gridx = 1;
            textFields[i] = new JTextField(20);
            panel.add(textFields[i], gbc);
        }

        // Assign to class fields for access
        txtEmployeeId = textFields[0];
        txtLastName = textFields[1];
        txtFirstName = textFields[2];
        txtBirthDate = textFields[3];
        txtAddress = textFields[4];
        txtPhone = textFields[5];
        txtSSS = textFields[6];
        txtPhilhealth = textFields[7];
        txtTIN = textFields[8];
        txtPagIbig = textFields[9];
        txtStatus = textFields[10];
        txtPosition = textFields[11];
        txtSupervisor = textFields[12];
        txtSalary = textFields[13];
        txtRiceSubsidy = textFields[14];
        txtPhoneAllowance = textFields[15];
        txtClothingAllowance = textFields[16];
        txtGrossRate = textFields[17];
        txtHourlyRate = textFields[18];

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");

        buttonsPanel.add(btnSave);
        buttonsPanel.add(btnCancel);

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(buttonsPanel, gbc);

        add(panel);

        // Button actions
        btnCancel.addActionListener(e -> dispose());

        btnSave.addActionListener(e -> {
            // Collect all field values
            String[] values = {
                txtEmployeeId.getText().trim(),
                txtLastName.getText().trim(),
                txtFirstName.getText().trim(),
                txtBirthDate.getText().trim(),
                txtAddress.getText().trim(),
                txtPhone.getText().trim(),
                txtSSS.getText().trim(),
                txtPhilhealth.getText().trim(),
                txtTIN.getText().trim(),
                txtPagIbig.getText().trim(),
                txtStatus.getText().trim(),
                txtPosition.getText().trim(),
                txtSupervisor.getText().trim(),
                txtSalary.getText().trim(),
                txtRiceSubsidy.getText().trim(),
                txtPhoneAllowance.getText().trim(),
                txtClothingAllowance.getText().trim(),
                txtGrossRate.getText().trim(),
                txtHourlyRate.getText().trim()
            };

            // Validate required fields 
            for (String val : values) {
                if (val.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }


            try {
                File file = new File("src/docs/MotorPH-Employee-Data.csv");
                boolean fileExists = file.exists();

                FileWriter fw = new FileWriter(file, true); // append = true
                BufferedWriter writer = new BufferedWriter(fw);

              
                if (!fileExists) {
                    writer.newLine();
                }

                // Build the row and write
                String csvRow = String.join(",", values);
                writer.write(csvRow);
                writer.newLine();
                writer.close();

                // Success alert message
                JOptionPane.showMessageDialog(this, "Employee data saved successfully.");

                // Refresh main employee table
                if (parent != null) {
                    parent.reloadEmployeeTable();
                }

                // Close the window
                dispose();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to save employee data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    }
}
