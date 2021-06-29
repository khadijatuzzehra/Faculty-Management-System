package FacultyManagement;

import javafx.beans.property.*;

public class Teaches {
    private StringProperty sID;
    private StringProperty sName;
    private StringProperty cID;
    private DoubleProperty gpa;

    public Teaches(){}
    public Teaches(String sID,String sName,String cID, double gpa) {
        this.sID=new SimpleStringProperty(sID);
        this.sName=new SimpleStringProperty(sName);
        this.cID=new SimpleStringProperty(cID);
        this.gpa=new SimpleDoubleProperty(gpa);
    }

    public String getsID() {
        return sID.get();
    }

    public StringProperty sIDProperty() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID.set(sID);
    }

    public String getcID() {
        return cID.get();
    }

    public StringProperty cIDProperty() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID.set(cID);
    }

    public double getGpa() {
        return gpa.get();
    }

    public DoubleProperty gpaProperty() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa.set(gpa);
    }

    public String getsName() {
        return sName.get();
    }

    public StringProperty sNameProperty() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName.set(sName);
    }
}
