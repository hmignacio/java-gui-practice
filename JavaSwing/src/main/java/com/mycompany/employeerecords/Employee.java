/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.employeerecords;
/**
 * Represents Employee with personal and employment details.
 */
public class Employee {
    private String employeeId, lastName, firstName, birthDate; // Basic Employee Details as per MotorPH Requirements
    private String address, phone; // Additional Employee Details - Personal
    private String sss, philhealth, tin, pagIbig; // Additional Employee Details - Benefits
    private String status, position, supervisor; // Additional Employee Details - Employment
    private String salary, riceSubsidy, phoneAllowance, clothingAllowance, grossRate, hourlyRate; // Additional Employee Details - Salary

    /**
     * Constructs Employee object with the given details.
     */
    public Employee(String employeeId, String lastName, String firstName, String birthDate, String address, String phone,
                    String sss, String philhealth, String tin, String pagIbig, String status, String position,
                    String supervisor, String salary, String riceSubsidy, String phoneAllowance,
                    String clothingAllowance, String grossRate, String hourlyRate) {
        this.employeeId = employeeId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.address = address;
        this.phone = phone;
        this.sss = sss;
        this.philhealth = philhealth;
        this.tin = tin;
        this.pagIbig = pagIbig;
        this.status = status;
        this.position = position;
        this.supervisor = supervisor;
        this.salary = salary;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.grossRate = grossRate;
        this.hourlyRate = hourlyRate;
    }

    // Getters and Setters
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getSss() { return sss; }
    public void setSss(String sss) { this.sss = sss; }

    public String getPhilhealth() { return philhealth; }
    public void setPhilhealth(String philhealth) { this.philhealth = philhealth; }

    public String getTin() { return tin; }
    public void setTin(String tin) { this.tin = tin; }

    public String getPagIbig() { return pagIbig; }
    public void setPagIbig(String pagIbig) { this.pagIbig = pagIbig; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getSupervisor() { return supervisor; }
    public void setSupervisor(String supervisor) { this.supervisor = supervisor; }

    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }

    public String getRiceSubsidy() { return riceSubsidy; }
    public void setRiceSubsidy(String riceSubsidy) { this.riceSubsidy = riceSubsidy; }

    public String getPhoneAllowance() { return phoneAllowance; }
    public void setPhoneAllowance(String phoneAllowance) { this.phoneAllowance = phoneAllowance; }

    public String getClothingAllowance() { return clothingAllowance; }
    public void setClothingAllowance(String clothingAllowance) { this.clothingAllowance = clothingAllowance; }

    public String getGrossRate() { return grossRate; }
    public void setGrossRate(String grossRate) { this.grossRate = grossRate; }

    public String getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(String hourlyRate) { this.hourlyRate = hourlyRate; }

     @Override
    public String toString() {
        return employeeId + " - " + lastName + ", " + firstName;
    }
}
