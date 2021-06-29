package FacultyManagement;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Accountant{
    private StringProperty employeeId;
    private StringProperty employeeName;
    private IntegerProperty salary;
    private StringProperty hireStatus;
    private StringProperty qualification;
    private IntegerProperty age;
    private StringProperty managedBy;

    public Accountant(){}

    public Accountant(String employeeId, String employeeName, int salary,
                   String hireStatus, String qualification,
                   int age, String managedBy) {
        this.employeeId = new SimpleStringProperty(employeeId);
        this.employeeName = new SimpleStringProperty(employeeName);
        this.salary = new SimpleIntegerProperty(salary);
        this.hireStatus = new SimpleStringProperty(hireStatus);
        this.qualification = new SimpleStringProperty(qualification);
        this.age = new SimpleIntegerProperty(age);
        this.managedBy = new SimpleStringProperty(managedBy);
    }

    public Accountant(Accountant accountant){
        this.employeeId =accountant.employeeId;
        this.employeeName=accountant.employeeName;
        this.salary=accountant.salary;
        this.hireStatus =accountant.hireStatus;
        this.qualification=accountant.qualification;
        this.age=accountant.age;
        this.managedBy=accountant.managedBy;
    }
    public String getEmployeeId() {
        return employeeId.get();
    }

    public StringProperty employeeIdProperty() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId.set(employeeId);
    }

    public String getEmployeeName() {
        return employeeName.get();
    }

    public StringProperty employeeNameProperty() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName.set(employeeName);
    }

    public int getSalary() {
        return salary.get();
    }

    public IntegerProperty salaryProperty() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary.set(salary);
    }

    public String getHireStatus() {
        return hireStatus.get();
    }

    public StringProperty hireStatusProperty() {
        return hireStatus;
    }

    public void setHireStatus(String hireStatus) {
        this.hireStatus.set(hireStatus);
    }

    public String getQualification() {
        return qualification.get();
    }

    public StringProperty qualificationProperty() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification.set(qualification);
    }

    public int getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public String getManagedBy() {
        return managedBy.get();
    }

    public StringProperty managedByProperty() {
        return managedBy;
    }

    public void setManagedBy(String managedBy) {
        this.managedBy.set(managedBy);
    }
}
