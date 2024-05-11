package Project1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EmployeeList {
    static Scanner inp = new Scanner(System.in);

    static ArrayList<Employee> emp = new ArrayList<>();

    static ArrayList<String> fileList = new ArrayList<String>();
    static String posCode, depCode, empName, empPos;
    static double hoursWorked, regPay, otPay, netPay;

    static double hourlyRate, taxRate;
    static double regHours = 176.02;
    static char subCode;
    static String ans ="";

    static String posCode1;

    static String fileName = "";

    static int choice;

    static String remName;

    static int x;

    //index to remove
    static int i;

    static boolean isValidInput = false;

    static boolean isValidInput1 = false;

    static boolean isValidInput2 = false;

    static String statCode;

    static double presentNum;

    static double otHours;

    public static void main(String[] args) throws InvalidInputException{

        do{
            menu();
            System.out.print("Back to Main Menu? ");
            ans = inp.nextLine();
        }while(ans.equalsIgnoreCase("YES"));
        //members();

    }//main
    public static void menu(){
        System.out.println("------------------------------------------------------------------");
        System.out.println("••••••••••••••••• EMPLOYEE PAYROLL SYSTEM •••••••••••••••••••");
        System.out.println("------------------------------------------------------------------");
        System.out.println("******************************************************************");
        System.out.println("           Enter Choice from the Following Options:");
        System.out.println("******************************************************************");
        System.out.println("------------------------------------------------------------------");
        System.out.println("\t [1] - Add Employee Record");
        System.out.println("\t [2] - Display Employee Record");
        System.out.println("\t [3] - Export Record to a File");
        System.out.println("\t [4] - Check for Existing File/s");
        System.out.println("------------------------------------------------------------------");

        try{
            System.out.print("Enter Choice: ");
            choice = inp.nextInt();
            if(choice <= 0  || choice > 5){
                throw new InvalidInputException("Invalid option");

            }
        }catch(InvalidInputException e){
            System.out.println("NOTE: Enter a Valid Number");
            System.out.println("------------------------------------------------------------------");
        }catch(InputMismatchException e){
            System.out.println("Input Mismatch Exception: Enter a Valid Number");
            System.out.println("------------------------------------------------------------------");
        }finally {
            inp.nextLine();
        }
        switch(choice){
            case 1:{
                do{
                    System.out.println("------------------------------------------------------------------");
                    addRecord();
                    inp.nextLine();
                    System.out.print("Add another employee? ");
                    ans = inp.nextLine();
                    //System.out.println("------------------------------------------------------------------");
                }while(ans.equalsIgnoreCase("YES"));
                break;
            }
            case 2:{
                printRecord();
                break;
            }
            case 3:{
                if(emp.isEmpty()){
                    System.out.println("No Employee Record Found...");
                    System.out.println("------------------------------------------------------------------");
                } else {
                    System.out.print("Enter File Name: ");
                    fileName = inp.nextLine();
                    fileName = fileName + ".txt";
                    fileList.add(fileName);
                    writeFile(fileName);
                    System.out.println("------------------------------------------------------------------");
                }//else
                break;
            }
            case 4:{
                checkFile();
                break;
            }
            case 5:{
                removeRecord();
                break;
            }
            default:{
                break;
            }
        }

    }//menu

    public static void addRecord(){

        while(!isValidInput){
            System.out.print("Enter Position Code (000X): ");
            posCode = inp.nextLine();

            if(posCode.equals("011A") || posCode.equals("011B")
                    || posCode.equals("011C") || posCode.equals("011D")){
                isValidInput = true;
            }else{
                System.out.println("Invalid code...");
                System.out.println();
            }
        }

        int j = posCode.length();
        posCode1 = posCode.substring(0,j-1);
        subCode = posCode.charAt(3);

        while(!isValidInput2){
            System.out.print("Status Code [SWD/SOD]: ");
            statCode = inp.nextLine();

            if(statCode.equals("SWD") || statCode.equals("SOD")){
                isValidInput2 = true;
            }else{
                System.out.println("Invalid status...");
                System.out.println();
            }
        }

        System.out.print("Employee Name: ");
        empName = inp.nextLine();

        while(!isValidInput1){
            System.out.print("Hours Worked: ");
            hoursWorked = inp.nextDouble();

            if(!(hoursWorked > 0.0)){
                System.out.println("Try Again...");
                inp.nextLine();
            }else{
                isValidInput1 = true;
            }
        }

        presentNum = hoursWorked / 8.00;

        switch(subCode){
            case 'A':{
                hourlyRate = 290.00;
                empPos = "Senior Programmer";
                break;
            }
            case 'B': {
                hourlyRate = 148.00;
                empPos = "Junior Programmer";
                break;
            }
            case 'C': {
                hourlyRate = 159.00;
                empPos = "System Analyst";
                break;
            }
            case 'D': {
                hourlyRate = 165.00;
                empPos = "Data Analyst";
                break;
            }
            default:{
                break;
            }
        }
        depCode = "MIS";//
        computeSalary(hoursWorked, hourlyRate);

        isValidInput = false;
        isValidInput1 = false;
        isValidInput2 = false;

        emp.add(new Employee(posCode,depCode,statCode,empName,empPos,hourlyRate,hoursWorked, presentNum, regPay,otPay,netPay));
        System.out.println("------------------------------------------------------------------");

    }//addRecord

    public static double computeSalary(double hoursWorked, double hourlyRate){

        if(hoursWorked > regHours){
            //with Overtime Pay (SWD/SOD)
            if(statCode.equals("SWD")){ //SWD: Single With Dependent
                regPay = hoursWorked * hourlyRate;
                otHours = hoursWorked - regHours;
                otPay =  otHours * hourlyRate * 1.5;
                taxRate = regPay * 0.05;
                netPay = (regPay + otPay) - taxRate;
            }
            else {// SOD: Single W/O Dependent
                regPay = hoursWorked * hourlyRate;
                otHours = hoursWorked - regHours;
                otPay =  otHours * hourlyRate * 1.5;
                taxRate = regPay * 0.10;
                netPay = (regPay + otPay) - taxRate;
            }
        } else{

            if(statCode.equals("SWD")){ //SWD: Single With Dependent
                regPay = hoursWorked * hourlyRate;
                otPay =  0;
                taxRate = regPay * 0.05;
                netPay = regPay - taxRate;
            }
            else {// SOD: Single W/O Dependent
                regPay = hoursWorked * hourlyRate;
                otHours = hoursWorked - regHours;
                otPay =  0;
                taxRate = regPay * 0.10;
                netPay = regPay - taxRate;
            }
        }
        return netPay;
    }//computeSalary

    public static void printRecord(){
        if(emp.isEmpty()){
            System.out.println("No Employee Record Found...");
            System.out.println("------------------------------------------------------------------");
        }
        else{
            System.out.println("------------------------------------------------------------------");
            System.out.println("Employee Payroll Records:");
            System.out.println("------------------------------------------------------------------");
            for(x =0; x<emp.size(); x++){
                System.out.println("Employee Position Code       : " + emp.get(x).posCode);
                System.out.println("Department                   : " + emp.get(x).depCode);
                System.out.println("Status                       : " + emp.get(x).statCode);
                System.out.println("Name                         : " + emp.get(x).empName);
                System.out.println("Position                     : " + emp.get(x).empPos);
                System.out.println("Pay Per Hour                 : " + emp.get(x).hourlyRate);
                System.out.println("Hours Worked                 : " + emp.get(x).hoursWorked);
                System.out.println("Present Days:                : " + emp.get(x).presentNum);
                System.out.println("Regular Pay                  : " + emp.get(x).regPay);
                System.out.println("Tax                          :");
                System.out.println("Overtime Pay                 : " + emp.get(x).otPay);
                System.out.println("Net Pay                      : " + emp.get(x).netPay);
                System.out.println("------------------------------------------------------------------");
            }
            //System.out.println("------------------------------------------------------------------");
        }
    }//printRecord

    public static String writeFile(String str){
        //C:\\Users\\Owner\\Desktop\\Payroll_Records\\fileName.txt
        //C:/Users/Owner/Desktop/Payroll_Records/fileName.txt
        try{
            FileWriter writer = new FileWriter(str);
            writer.write("Employee Payroll Record");
            writer.write("\n------------------------------------------------------------------");
            for(x=0; x<emp.size(); x++){
                writer.write("\n\nEmployee Position Code       : " + emp.get(x).posCode);
                writer.write("\nDepartment                   : " + emp.get(x).depCode);
                writer.write("\nStatus                       : " + emp.get(x).statCode);
                writer.write("\nName                         : " + emp.get(x).empName);
                writer.write("\nPosition                     : " + emp.get(x).empPos);
                writer.write("\nPay Per Hour                 : " + emp.get(x).hourlyRate);
                writer.write("\nHours Worked                 : " + emp.get(x).hoursWorked);
                writer.write("\nPresent Days:                : " + emp.get(x).presentNum);
                writer.write("\nRegular Pay                  : " + emp.get(x).regPay);
                writer.write("\nTax                          :");
                writer.write("\nOvertime Pay                 : " + emp.get(x).otPay);
                writer.write("\nNet Pay                      : " + emp.get(x).netPay);
                writer.write("\n------------------------------------------------------------------");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return str;
    }//writeFile

    public static void checkFile(){
        //Add-ons
        //Ask if the user wants to display the content of the file

        //Options
        //Display
        //Delete the most recent file: file.delete()
        if(fileList.isEmpty()){
            System.out.println("No File Exist...");
        }
        else {
            System.out.println("------------------------------------------------------------------");
            System.out.println("File Lists Record:");
            for (x = 0; x < fileList.size(); x++) {
                System.out.println("\t" + (x + 1) + ". " + fileList.get(x));
            }
        }
        System.out.println("------------------------------------------------------------------");

        //since the file is already create from createFile() method
        //this does not create a new file, it just checks if the fileName exist already
        File file = new File(fileName);

        //check if the file exist
        if(file.exists()){
            //return the path of file
            System.out.println("Most Recent File:");
            System.out.println("\tFile Directory: " + file.getAbsolutePath());
            System.out.println("------------------------------------------------------------------");
        }
        else {
            System.out.println("No File Found...");
            System.out.println("------------------------------------------------------------------");
        }
    }//checkFile

    public static void removeRecord(){

        if(emp.isEmpty()){
            System.out.println("No Employee Record Found...");
            System.out.println("------------------------------------------------------------------");
        } else {
            do{
                System.out.println("\n------------------------------------------------------------------");
                System.out.println("                  Existing Employees Record                        ");
                System.out.println("------------------------------------------------------------------");
                System.out.println("\tNUMBER   \tNAME \t         POSITION");
                for(x=0; x<emp.size(); x++){
                    System.out.println("\t" + (x+1) + ". \t" + emp.get(x).empName
                            + "\t:     " + emp.get(x).empPos);
                }

                System.out.print("Employee to Remove: ");
                remName = inp.nextLine();
//              System.out.println(removeNum);

                if(remName.isEmpty()){
                    System.out.println("Cannot be empty..");
                } else {
                    for(x = 0; x < emp.size(); x++){
                        if(remName.equals(emp.get(x).empName)){
                            i = x;
                        }
                    }
                    emp.remove(i);
                }

                inp.nextLine();
                System.out.print("Try again? ");
                ans = inp.nextLine();

            }while(ans.equalsIgnoreCase("YES"));
        }
    }//removeRecord

    public static void members(){
        System.out.println("\n\n------------------------------------------------------------------");
        System.out.println("                      GROUP DETAILS                             ");
        System.out.println("------------------------------------------------------------------");
        System.out.println("\tSurname, First Name M.I");
        System.out.println("\tBitara, Aaron John");
        System.out.println("\tSantos, Rommer");
        System.out.println("\tVelasco, Geoff");
        System.out.println("------------------------------------------------------------------");
        System.out.println("                THANK YOU FOR USING OUR PROGRAM                   " );
        System.out.println("------------------------------------------------------------------");
    }//members

}//class
