package FacultyManagement;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private StringProperty studentId;
    private StringProperty name;
    private StringProperty regNum;
    private DoubleProperty CGPA;
    private StringProperty feeStatus;
    private SimpleStringProperty scholarshipId;

    public Student(){}

    public Student(String sID1, String name1, String regNum1, double cgpa,String feeStatus1,String schId1) {
        this.studentId = new SimpleStringProperty(sID1);
        this.name = new SimpleStringProperty(name1);
        this.regNum = new SimpleStringProperty(regNum1);
        this.CGPA=new SimpleDoubleProperty(cgpa);
        this.feeStatus = new SimpleStringProperty(feeStatus1);
        this.scholarshipId=new SimpleStringProperty(schId1);
    }
    public Student(Student s1){
        this.studentId =s1.studentId;
        this.name=s1.name;
        this.regNum=s1.regNum;
        this.CGPA=s1.CGPA;
        this.feeStatus=s1.feeStatus;
        this.scholarshipId =s1.scholarshipId;
    }

    public String getStudentId() {
        return studentId.get();
    }

    public StringProperty studentIdProperty() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId.set(studentId);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getRegNum() {
        return regNum.get();
    }

    public StringProperty regNumProperty() {
        return regNum;
    }

    public void setRegNum(String regNum) {
        this.regNum.set(regNum);
    }

    public double getCGPA() {
        return CGPA.get();
    }

    public DoubleProperty CGPAProperty() {
        return CGPA;
    }

    public void setCGPA(double CGPA) {
        this.CGPA.set(CGPA);
    }

    public String getFeeStatus() {
        return feeStatus.get();
    }

    public StringProperty feeStatusProperty() {
        return feeStatus;
    }

    public void setFeeStatus(String feeStatus) {
        this.feeStatus.set(feeStatus);
    }

    public String getScholarshipId() {
        return scholarshipId.get();
    }

    public SimpleStringProperty scholarshipIdProperty() {
        return scholarshipId;
    }

    public void setScholarshipId(String scholarshipId) {
        this.scholarshipId.set(scholarshipId);
    }
}
