    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javaswing;

/**
 *
 * @author Admin
 */
public class Employee {
    private String employeeNumber; 
    private String name; 
    private double hourlyRate;
    private String position;
    
    
    public Employee (String employeeNumber, String name, double hourlyRate, String position){
        this.employeeNumber = employeeNumber;
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.position = position;
    }
    
    
    public String getName(){
        return name;
    }
    
    public String getEmployeeNumber(){
        return employeeNumber;
    }
    
    public double getHourlyRate(){
        return hourlyRate;
    }
    
    public String getPosition(){
        return position;
    }
    
   
    
    public void displayInfo(){
        System.out.println(
                "Employee Number: " + employeeNumber + "\n" +
                "Employee Name: " + name + "\n" +
                "Position: " + position + "\n" +
                "Hourly Rate: " + hourlyRate + "\n"
        );
    }
    
}
