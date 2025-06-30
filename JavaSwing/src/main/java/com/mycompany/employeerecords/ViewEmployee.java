
package com.mycompany.employeerecords;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ViewEmployee extends JFrame {

    private Employee employee;
    private EmployeeRecords parent;

    public ViewEmployee(EmployeeRecords parent, Employee employee) {
        this.parent = parent;
        this.employee = employee;

        setTitle("Employee Details - " + employee.getEmployeeId());
        setSize(520, 450);
        setLocationRelativeTo(null);
        setResizable(false); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Just close this frame
       
        makeJFrame();
    }

    private void makeJFrame() {
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Create the header label
        JLabel headerLabel = new JLabel("Profile");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerLabel.setHorizontalAlignment(SwingConstants.LEFT); 

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        separator.setAlignmentX(Component.LEFT_ALIGNMENT); 
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2)); 
        

        // Main top panel with vertical BoxLayout to stack label, separator, buttons vertically
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(headerLabel);
        topPanel.add(separator);

        add(topPanel, BorderLayout.NORTH);
        
        contentPanel.add(topPanel, BorderLayout.NORTH);
        
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        String[][] fields = {
            {"Employee ID:", employee.getEmployeeId()},
            {"Last Name:", employee.getLastName()},
            {"First Name:", employee.getFirstName()},
            {"Birth Date:", employee.getBirthDate()},
            {"Address:", employee.getAddress()},
            {"Phone:", employee.getPhone()},
            {"SSS Number:", employee.getSss()},
            {"PhilHealth Number:", employee.getPhilhealth()},
            {"TIN:", employee.getTin()},
            {"Pag-IBIG Number:", employee.getPagIbig()},
            {"Status:", employee.getStatus()},
            {"Position:", employee.getPosition()},
            {"Supervisor:", employee.getSupervisor()},
            {"Salary:", employee.getSalary()},
            {"Rice Subsidy:", employee.getRiceSubsidy()},
            {"Phone Allowance:", employee.getPhoneAllowance()},
            {"Clothing Allowance:", employee.getClothingAllowance()},
            {"Gross Semi-monthly Rate:", employee.getGrossRate()},
            {"Hourly Rate:", employee.getHourlyRate()}
        };

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font valueFont = new Font("SansSerif", Font.PLAIN, 14);

        gbc.gridy = 0;


        for (String[] field : fields) {
            // Label (left column)
            gbc.gridx = 0;
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
            JLabel label = new JLabel(field[0]);
            label.setFont(labelFont);
            detailsPanel.add(label, gbc);

            // Value (right column)
            gbc.gridx = 1;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            Component valueComponent;

            if (field[0].equals("Address:")) {
                // Use JTextArea for wrapping
                JTextArea textArea = new JTextArea(field[1]);
                textArea.setFont(valueFont);
                textArea.setForeground(Color.DARK_GRAY);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setOpaque(false);
                textArea.setEditable(false);
                textArea.setFocusable(false);
                textArea.setBorder(null);
                valueComponent = textArea;
            } else {
                JLabel value = new JLabel(field[1]);
                value.setFont(valueFont);
                value.setForeground(Color.DARK_GRAY);
                valueComponent = value;
            }

            detailsPanel.add(valueComponent, gbc);

            gbc.gridy++;
        }


        gbc.gridx = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        detailsPanel.add(Box.createVerticalGlue(), gbc);

        JScrollPane scrollPane = new JScrollPane(detailsPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom button panel with Edit, Delete, Close buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");
        JButton btnClose = new JButton("Close");

        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClose);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPanel);

        // Button actions
        btnClose.addActionListener(e -> dispose());

        btnEdit.addActionListener(e -> {
            EditEmployee editFrame = new EditEmployee(parent, employee, ViewEmployee.this);
            editFrame.setVisible(true);
        });

        btnDelete.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete employee " + employee.getEmployeeId() + "?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean deleted = deleteEmployeeFromCSV(employee.getEmployeeId());

                if (deleted) {
                    JOptionPane.showMessageDialog(this, "Employee deleted successfully.");
                    if (parent != null) {
                        parent.reloadEmployeeTable(); // refresh JTable
                    }
                    dispose(); // close the view
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete employee.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
        private boolean deleteEmployeeFromCSV(String employeeId) {
        File file = new File("src/docs/MotorPH-Employee-Data.csv");
        File tempFile = new File("src/docs/temp.csv");

        boolean deleted = false;

        try (
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(employeeId + ",")) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    deleted = true; // we found and skipped the target line
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        // Replace the original file with the temp file
        if (deleted) {
            if (file.delete()) {
                return tempFile.renameTo(file);
            }
        }

        return false;
    }

}


