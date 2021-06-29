package FacultyManagement;

import javafx.beans.property.*;

public class Scholarship {
    private StringProperty schId;
    private StringProperty studentName;
    private StringProperty studentReg;
    private DoubleProperty cgpa;
    private IntegerProperty income;
    private StringProperty managedBy;

    public Scholarship(){}

    public Scholarship(String schID, String studentName,String studentReg,double cgpa, int income, String managedBy) {
        this.schId = new SimpleStringProperty(schID);
        this.studentName = new SimpleStringProperty(studentName);
        this.studentReg = new SimpleStringProperty(studentReg);
        this.cgpa = new SimpleDoubleProperty(cgpa);
        this.income = new SimpleIntegerProperty(income);
        this.managedBy = new SimpleStringProperty(managedBy);
    }
    public Scholarship(Scholarship sch){
        this.schId=sch.schId;
        this.studentName = sch.studentName;
        this.studentReg = sch.studentReg;
        this.cgpa = sch.cgpa;
        this.income = sch.income;
        this.managedBy = sch.managedBy;
    }

    public String getSchId() {
        return schId.get();
    }

    public StringProperty schIdProperty() {
        return schId;
    }

    public void setSchId(String schId) {
        this.schId.set(schId);
    }

    public String getStudentName() {
        return studentName.get();
    }

    public StringProperty studentNameProperty() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName.set(studentName);
    }

    public String getStudentReg() {
        return studentReg.get();
    }

    public StringProperty studentRegProperty() {
        return studentReg;
    }

    public void setStudentReg(String studentReg) {
        this.studentReg.set(studentReg);
    }

    public double getCgpa() {
        return cgpa.get();
    }

    public DoubleProperty cgpaProperty() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa.set(cgpa);
    }

    public int getIncome() {
        return income.get();
    }

    public IntegerProperty incomeProperty() {
        return income;
    }

    public void setIncome(int income) {
        this.income.set(income);
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
