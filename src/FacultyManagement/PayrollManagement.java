package FacultyManagement;
import javafx.beans.property.IntegerProperty;import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class PayrollManagement {
    private StringProperty teacherId;
    private StringProperty teacherEmail;
    private StringProperty teacherName;
    private IntegerProperty teacherSalary;
    private StringProperty teacherRole;
    public PayrollManagement(){}
    public PayrollManagement(String tId, String tEmail,String tName,int tSalary,String tRole){
        this.teacherId=new SimpleStringProperty(tId);
        this.teacherEmail=new SimpleStringProperty(tEmail);
        this.teacherName=new SimpleStringProperty(tName);
        this.teacherSalary=new SimpleIntegerProperty(tSalary);
        this.teacherRole=new SimpleStringProperty(tRole);
    }
    public String getTeacherId() {
        return teacherId.get();
    }
    public StringProperty teacherIdProperty() {
        return teacherId;
    }
    public void setTeacherId(String teacherId) {
        this.teacherId.set(teacherId);
    }
    public String getTeacherEmail() {
        return teacherEmail.get();
    }
    public StringProperty teacherEmailProperty() {
        return teacherEmail;
    }
    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail.set(teacherEmail);
    }
    public String getTeacherName() {
        return teacherName.get();
    }
    public StringProperty teacherNameProperty() {
        return teacherName;
    }
    public void setTeacherName(String teacherName) {
        this.teacherName.set(teacherName);
    }
    public int getTeacherSalary() {
        return teacherSalary.get();
    }
    public IntegerProperty teacherSalaryProperty() {
        return teacherSalary;
    }
    public void setTeacherSalary(int teacherSalary) {
        this.teacherSalary.set(teacherSalary);
    }
    public String getTeacherRole() {
        return teacherRole.get();
    }
    public StringProperty teacherRoleProperty() {
        return teacherRole;
    }
    public void setTeacherRole(String teacherRole) {
        this.teacherRole.set(teacherRole);
    }
}
