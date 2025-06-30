
package com.mycompany.employeerecords;

import com.opencsv.CSVReader; 
import com.opencsv.exceptions.CsvException;


import java.io.*;
import java.net.*;
import java.util.*;
import java.time.*;
import java.time.format.*;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class SalaryComputation {
    
    private static final Attendance attendance = new Attendance();
   
    
    private static List<String[]> readCSVFromURL(String urlString) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(urlString).openStream()));
             CSVReader csvReader = new CSVReader(br)) {
            data = csvReader.readAll();
        } catch (IOException | CsvException e) {
        }
        return data;
    }
    
    
    //calculation for gross pay
    public static double GrossPay(String employeeNumber, double minutesWorked, double overtimeMins){ 
                String csvURL = "https://drive.google.com/uc?export=download&id=1Gh7C6XjNXvdYJHEnS39kXN21CtkL-1Zh";
        
        //declaration of the variables to use
        double hourlyRate = 0.0;
        double salary = 0.0;
        double riceSubsidy = 0.0;
        double phoneAllowance = 0.0;
        double overtimeRate = 0.0;
        double overtimeSalary = 0.0;
        double basePay = 0.0;
        
    
            /*try(CSVReader csvReader = new CSVReader(new FileReader(csvFile));){
            *    List<String[]> rows = csvReader.readAll();
            */

        List<String[]> rows = readCSVFromURL(csvURL);

            for(int i = 1; i < rows.size(); i++){
                String[]value = rows.get(i);
                if(parseInt(value[0]) == parseInt(employeeNumber)){
                    hourlyRate = (parseFloat(value[18]) / 60);
                    //add subsidies
                    //replace method is used to negate or escape the comma -> 1,500
                    riceSubsidy = parseFloat(value[14].replace(",", ""));
                    phoneAllowance = parseInt(value[15].replace(",", ""));
                    //overtime calculation based on labor code
                    overtimeRate = (hourlyRate * 1.25) / 60;
                    break;
                }
            }
            
            
            basePay = (hourlyRate * minutesWorked);
            riceSubsidy = riceSubsidy / 4;
            phoneAllowance = phoneAllowance / 4;
            overtimeSalary = (overtimeRate * overtimeMins);
            salary =  basePay + riceSubsidy + phoneAllowance + overtimeSalary;
            
            System.out.println("\n--- Gross Pay Breakdown ---");
            System.out.printf("Base Pay: PHP  %, .2f%n", basePay);
            System.out.printf("Phone Allowance: PHP  %, .2f%n", phoneAllowance);
            System.out.printf("Rice Subsidy: PHP  %, .2f%n", riceSubsidy);
            System.out.printf("Overtime Pay: PHP %, .2f%n", overtimeSalary);
            System.out.println("\n--- End of Gross Pay Breakdown ---");
            System.out.println("");
            
                     
        return salary;
    };
    
    
  
    // COMPUTE SALARY DEDUCTIONS
    public static double computeDeductions(double salary, String employeeId, double late) {
        
    String csvURL = "https://drive.google.com/uc?export=download&id=1Gh7C6XjNXvdYJHEnS39kXN21CtkL-1Zh";
    double[][] sssRanges = {
        {0, 3249.99, 135.0}, {3250, 3749.99, 157.5}, {3750, 4249.99, 180.0},
        {4250, 4749.99, 202.5}, {4750, 5249.99, 225.0}, {5250, 5749.99, 247.5},
        {5750, 6249.99, 270.0}, {6250, 6749.99, 292.5}, {6750, 7249.99, 315.0},
        {7250, 7749.99, 337.5}, {7750, 8249.99, 360.0}, {8250, 8749.99, 382.5},
        {8750, 9249.99, 405.0}, {9250, 9749.99, 427.5}, {9750, 10249.99, 450.0},
        {10250, 10749.99, 472.5}, {10750, 11249.99, 495.0}, {11250, 11749.99, 517.5},
        {11750, 12249.99, 540.0}, {12250, 12749.99, 562.5}, {12750, Double.MAX_VALUE, 585.0}
    };

    double sssDeduction = 0;
    for (double[] range : sssRanges) {
        if (salary >= range[0] && salary <= range[1]) {
            sssDeduction = range[2];
            break;
        }
    }
    
    List<String[]> rows = readCSVFromURL(csvURL);
    
    double lateRate = 0.0;

            for(int i = 1; i < rows.size(); i++){
                String[]value = rows.get(i);
                if(parseInt(value[0]) == parseInt(employeeId)){
                    lateRate = parseFloat(value[18]) / 60;
                    break;
                }
            }

    double philHealthDeduction = (salary >= 10000 && salary <= 100000) ? (salary * 0.05) / 2 : 0;
    double pagIbigDeduction = 100.0;
    double lateDeductions = lateRate * late;

    double taxableIncome = salary - (sssDeduction + philHealthDeduction + pagIbigDeduction);
    double withholdingTax = 0;

    if (taxableIncome > 20833 && taxableIncome < 33333) {
        withholdingTax = (taxableIncome - 20833) * 0.20;
    } else if (taxableIncome >= 33333 && taxableIncome < 66667) {
        withholdingTax = 2500 + (taxableIncome - 33333) * 0.25;
    } else if (taxableIncome >= 66667 && taxableIncome < 166667) {
        withholdingTax = 10833 + (taxableIncome - 66667) * 0.30;
    } else if (taxableIncome >= 166667 && taxableIncome < 666667) {
        withholdingTax = 40833 + (taxableIncome - 166667) * 0.32;
    } else if (taxableIncome >= 666667) {
        withholdingTax = 200833 + (taxableIncome - 666667) * 0.35;
    }

    double totalDeductions = (sssDeduction / 4) + (philHealthDeduction / 4) + (pagIbigDeduction / 4) + (withholdingTax / 4) + lateDeductions;
    double netSalary = salary - totalDeductions;

    System.out.println("\n--- Deductions Breakdown ---");
    System.out.printf("SSS Deduction: PHP  %, .2f%n", sssDeduction);
    System.out.printf("PhilHealth Deduction: PHP  %, .2f%n", philHealthDeduction);
    System.out.printf("Pag-IBIG Deduction: PHP  %, .2f%n", pagIbigDeduction);
    System.out.printf("Withholding Tax: PHP %, .2f%n", withholdingTax);
    System.out.printf("Late Deductions: PHP %, .2f%n", lateDeductions);
    System.out.printf("Total Deductions: PHP %, .2f%n", totalDeductions);
    System.out.println("\n--- End of Deductions Breakdown ---");
    System.out.println("");

    return netSalary;
 }
    
    //METHOD FOR ATTENDANCE RECORD + SALARY COMPUTATION
    public void computeSalary(String employeeId, int year, int month) {
        
        //System.out.println("|-- Attendance Record of Employee " + employeeId + " --|");
        
        ArrayList<ArrayList<Double>> attendanceCollector = attendance.displayAttendance(employeeId, year, month);

           double totalGross = 0.0;
           double totalNet = 0.0;

           for (int i = 0; i < attendanceCollector.size(); i++) {
               ArrayList<Double> weekData = attendanceCollector.get(i);
               if (weekData.size() >= 3) {
                   double work = weekData.get(0);
                   double late = weekData.get(1);
                   double ot   = weekData.get(2);

                   double grossPay = GrossPay(employeeId, work, ot);
                   System.out.printf("Gross Pay for Week %d : PHP %, .2f\n", i + 1, grossPay);
                   double netPay = computeDeductions(grossPay, employeeId, late);
                   System.out.printf("Net Pay for Week %d : PHP %, .2f\n", i + 1, netPay);
                   System.out.println("------------------------------");
                   totalGross += grossPay;
                   totalNet += netPay;

               } else {
                   System.out.println("⚠️ Skipping week due to incomplete data!");
               }
           }
           System.out.println("==============================");
           System.out.printf("Gross Pay for the month: PHP %, .2f\n", totalGross);
           System.out.printf("Net Pay for the month: PHP %, .2f\n", totalNet);
           System.out.println("==============================");
        }
     
  }

