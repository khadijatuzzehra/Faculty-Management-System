package FacultyManagement;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class teacherDisplay{
    private StringProperty employeeId;
    private StringProperty employeeName;
    private StringProperty hireStatus;
    private StringProperty qualification;

    public teacherDisplay(){}

    public teacherDisplay(String employeeId, String employeeName, String hireStatus, String qualification) {
        this.employeeId = new SimpleStringProperty(employeeId);
        this.employeeName = new SimpleStringProperty(employeeName);
        this.hireStatus = new SimpleStringProperty(hireStatus);
        this.qualification = new SimpleStringProperty(qualification);
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
}
