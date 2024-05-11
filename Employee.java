package Project1;

public class Employee {
    String posCode,empName, statCode, depCode, empPos;
    //int posCode;
    double hourlyRate,hoursWorked, presentNum, regPay, otPay, netPay;
    //deduction;

    Employee(String posCode, String depCode, String statCode, String empName, String empPos, double hourlyRate, double hoursWorked, double presentNum,double regPay, double otPay, double netPay){
        this.posCode = posCode;
        this.depCode = depCode;
        this.statCode = statCode;
        this.empName = empName;
        this.empPos = empPos;
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.presentNum = presentNum;
        this.regPay = regPay;
        this.otPay = otPay;
        this.netPay = netPay;
        //this.deduction = deduction;
    }
}
