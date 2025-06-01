package com.mycompany.employeerecords;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.text.NumberFormat;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Scanner;

public class Salary extends JFrame {
    private Employee employee;
    private JComboBox<String> monthComboBox, yearComboBox;
    private JLabel grossPayLabel, netPayLabel, totalHoursLabel, minutesWorkedLabel, otMinutesLabel, resultLabel;
    private JTextArea benefitsArea, deductionsArea;


    public Salary(Employee employee) {
        this.employee = employee;

        setTitle("Salary Calculator");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        JLabel headerLabel = new JLabel(employee.getFirstName() + " " + employee.getLastName() + " Salary Information");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        headerPanel.add(headerLabel, BorderLayout.NORTH);

        // Create a gray, thick separator
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.GRAY);
        separator.setPreferredSize(new Dimension(1, 3)); // Thickness: 3px
        separator.setBackground(Color.GRAY); // Ensures visibility on all LAFs

        // Wrap in a panel to add left and right margins
        JPanel separatorPanel = new JPanel(new BorderLayout());
        separatorPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); // Top, Left, Bottom, Right padding
        separatorPanel.add(separator, BorderLayout.CENTER);

        // Add to the frame or header sectio
        headerPanel.add(separatorPanel, BorderLayout.SOUTH);
        
        add(headerPanel, BorderLayout.NORTH);


        // Center Panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Column 1 - Labels
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("Select Year:"), gbc);
        gbc.gridy++;
        centerPanel.add(new JLabel("Select Month:"), gbc);
        gbc.gridy++;
        centerPanel.add(new JLabel("Gross Pay:"), gbc);
        gbc.gridy++;
        centerPanel.add(new JLabel("Net Pay:"), gbc);
        gbc.gridy++;
        centerPanel.add(new JLabel("Total Hours Worked:"), gbc);
        gbc.gridy++;
        centerPanel.add(new JLabel("Minutes Worked:"), gbc);
        gbc.gridy++;
        centerPanel.add(new JLabel("OT Minutes Worked:"), gbc);

        // Column 2 - Fields
        gbc.gridx = 1;
        gbc.gridy = 0;
        yearComboBox = new JComboBox<>(new String[]{"2024", "2025"});
        centerPanel.add(yearComboBox, gbc);
        gbc.gridy++;
        monthComboBox = new JComboBox<>(new String[]{
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        });
        centerPanel.add(monthComboBox, gbc);
        gbc.gridy++;
        grossPayLabel = new JLabel();
        centerPanel.add(grossPayLabel, gbc);
        gbc.gridy++;
        netPayLabel = new JLabel();
        centerPanel.add(netPayLabel, gbc);
        gbc.gridy++;
        totalHoursLabel = new JLabel();
        centerPanel.add(totalHoursLabel, gbc);
        gbc.gridy++;
        minutesWorkedLabel = new JLabel();
        centerPanel.add(minutesWorkedLabel, gbc);
        gbc.gridy++;
        otMinutesLabel = new JLabel();
        centerPanel.add(otMinutesLabel, gbc);

        // Column 3 - Labels for Benefits & Deductions
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        centerPanel.add(new JLabel("Benefits:"), gbc);
        gbc.gridy = 1;
        benefitsArea = new JTextArea(6, 20);
        benefitsArea.setLineWrap(true);
        benefitsArea.setWrapStyleWord(true);
        benefitsArea.setEditable(false);
        centerPanel.add(new JScrollPane(benefitsArea), gbc);

        gbc.gridy = 3;
        centerPanel.add(new JLabel("Deductions:"), gbc);
        gbc.gridy = 4;
        deductionsArea = new JTextArea(6, 20);
        deductionsArea.setLineWrap(true);
        deductionsArea.setWrapStyleWord(true);
        deductionsArea.setEditable(false);
        centerPanel.add(new JScrollPane(deductionsArea), gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Footer buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton computeButton = new JButton("Compute");
        JButton closeButton = new JButton("Close");
        buttonPanel.add(computeButton);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);


         //Action listeners
        computeButton.addActionListener(e -> {
            String selectedMonth = (String) monthComboBox.getSelectedItem();
            String selectedYear = (String) yearComboBox.getSelectedItem();
            int monthNumber = getMonthNumber(selectedMonth);
            int year = Integer.parseInt(selectedYear);
            
            if(year != 2024){
                JOptionPane.showMessageDialog(this, "No records found for this year.");
                return;
            }
            if (monthNumber == -1) {
                JOptionPane.showMessageDialog(this, "Please select a valid month.");
                return;
            }
            else if (monthNumber < 6) {
                JOptionPane.showMessageDialog(this, "No records found for this month.");
                return;
            }

            ArrayList<ArrayList<Double>> attendanceData = new Attendance().displayAttendance(employee.getEmployeeId(), year, monthNumber);

            double totalGross = 0.0;
            double totalNet = 0.0;
            double totalMinutes = 0.0;
            double totalOT = 0.0;

            StringBuilder benefitsText = new StringBuilder();
            StringBuilder deductionsText = new StringBuilder();

            for (int i = 0; i < attendanceData.size(); i++) {
                ArrayList<Double> weekData = attendanceData.get(i);
                if (weekData.size() >= 3) {
                    double work = weekData.get(0);
                    double late = weekData.get(1);
                    double ot   = weekData.get(2);

                    double grossPay = SalaryComputation.GrossPay(employee.getEmployeeId(), work, ot);
                    double netPay = SalaryComputation.computeDeductions(grossPay, employee.getEmployeeId(), late);

                    totalGross += grossPay;
                    totalNet += netPay;
                    totalMinutes += work;
                    totalOT += ot;
                }
            }

            // Update GUI
            grossPayLabel.setText(String.format("PHP %, .2f", totalGross));
            netPayLabel.setText(String.format("PHP %, .2f", totalNet));
            totalHoursLabel.setText(String.format("%.2f", totalMinutes / 60));
            minutesWorkedLabel.setText(String.format("%.0f mins", totalMinutes));
            otMinutesLabel.setText(String.format("%.0f mins", totalOT));

            // Simulate breakdown (you can pass these from the methods if needed)
            benefitsText.append("• Rice Subsidy\n• Phone Allowance\n• OT Pay");
            deductionsText.append("• SSS\n• PhilHealth\n• Pag-IBIG\n• Withholding Tax\n• Late Deductions");

            benefitsArea.setText(benefitsText.toString());
            deductionsArea.setText(deductionsText.toString());
        });


        closeButton.addActionListener(e -> dispose());
    }

    
    private int getMonthNumber(String monthName) {
        try {
            Month month = Month.valueOf(monthName.toUpperCase());
            return month.getValue(); // 1 to 12
        } catch (IllegalArgumentException e) {
            return -1; // invalid month
        }
    }

}
