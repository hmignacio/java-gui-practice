package com.mycompany.employeerecords;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class EditEmployee extends JFrame {

    private EmployeeRecords parent;
    private Employee employee;
    private ViewEmployee viewWindow;

    private JTextField txtEmployeeId, txtLastName, txtFirstName, txtBirthDate, txtAddress, txtPhone,
            txtSSS, txtPhilhealth, txtTIN, txtPagIbig, txtStatus, txtPosition, txtSupervisor,
            txtSalary, txtRiceSubsidy, txtPhoneAllowance, txtClothingAllowance,
            txtGrossRate, txtHourlyRate;

    public EditEmployee(EmployeeRecords parent, Employee employee, ViewEmployee viewWindow) {
        this.parent = parent;
        this.employee = employee;
        this.viewWindow = viewWindow;

        setTitle("Edit Employee - " + employee.getEmployeeId());
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Header ---
        JLabel headerLabel = new JLabel("Edit Employee");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JSeparator separator = new JSeparator();
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(headerLabel);
        topPanel.add(separator);
        add(topPanel, BorderLayout.NORTH);

        // --- Form Fields ---
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {
            "Employee ID", "Last Name", "First Name", "Birth Date", "Address", "Phone Number",
            "SSS Number", "PhilHealth Number", "TIN", "Pag-IBIG Number", "Status", "Position",
            "Supervisor", "Salary", "Rice Subsidy", "Phone Allowance", "Clothing Allowance",
            "Gross Rate", "Hourly Rate"
        };

        JTextField[] textFields = new JTextField[labels.length];
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            panel.add(new JLabel(labels[i] + ":"), gbc);

            gbc.gridx = 1;
            textFields[i] = new JTextField(20);
            panel.add(textFields[i], gbc);
        }

        txtEmployeeId = textFields[0]; txtEmployeeId.setEditable(false);
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

        // Fill with current values
        txtEmployeeId.setText(employee.getEmployeeId());
        txtLastName.setText(employee.getLastName());
        txtFirstName.setText(employee.getFirstName());
        txtBirthDate.setText(employee.getBirthDate());
        txtAddress.setText(employee.getAddress());
        txtPhone.setText(employee.getPhone());
        txtSSS.setText(employee.getSss());
        txtPhilhealth.setText(employee.getPhilhealth());
        txtTIN.setText(employee.getTin());
        txtPagIbig.setText(employee.getPagIbig());
        txtStatus.setText(employee.getStatus());
        txtPosition.setText(employee.getPosition());
        txtSupervisor.setText(employee.getSupervisor());
        txtSalary.setText(employee.getSalary());
        txtRiceSubsidy.setText(employee.getRiceSubsidy());
        txtPhoneAllowance.setText(employee.getPhoneAllowance());
        txtClothingAllowance.setText(employee.getClothingAllowance());
        txtGrossRate.setText(employee.getGrossRate());
        txtHourlyRate.setText(employee.getHourlyRate());

        // --- Save / Cancel Buttons ---
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");

        buttonsPanel.add(btnSave);
        buttonsPanel.add(btnCancel);

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(buttonsPanel, gbc);

        add(panel, BorderLayout.CENTER);

        // --- Actions ---
        btnCancel.addActionListener(e -> dispose());

        btnSave.addActionListener(e -> {
            String[] updated = {
                txtEmployeeId.getText().trim(), txtLastName.getText().trim(), txtFirstName.getText().trim(),
                txtBirthDate.getText().trim(), txtAddress.getText().trim(), txtPhone.getText().trim(),
                txtSSS.getText().trim(), txtPhilhealth.getText().trim(), txtTIN.getText().trim(),
                txtPagIbig.getText().trim(), txtStatus.getText().trim(), txtPosition.getText().trim(),
                txtSupervisor.getText().trim(), txtSalary.getText().trim(), txtRiceSubsidy.getText().trim(),
                txtPhoneAllowance.getText().trim(), txtClothingAllowance.getText().trim(),
                txtGrossRate.getText().trim(), txtHourlyRate.getText().trim()
            };

            updateEmployeeInCSV(updated);

            JOptionPane.showMessageDialog(this, "Employee updated successfully.");
            if (parent != null) {
                parent.reloadEmployeeTable();
            }
            if (viewWindow != null) {
                viewWindow.dispose(); // close the view after saving
            }
            dispose();
        });
    }

    private void updateEmployeeInCSV(String[] updatedValues) {
        File file = new File("src/docs/MotorPH-Employee-Data.csv");
        java.util.List<String> allLines = new java.util.ArrayList<>();
        String updatedId = updatedValues[0];

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(updatedId + ",")) {
                    allLines.add(String.join(",", updatedValues));
                } else {
                    allLines.add(line);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading CSV: " + ex.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : allLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error writing CSV: " + ex.getMessage());
        }
    }
}