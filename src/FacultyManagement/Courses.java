package FacultyManagement;

import javafx.beans.property.*;

public class Courses {
    private StringProperty sID;
    private StringProperty sName;
    private StringProperty cID;
    private DoubleProperty attn;

    public Courses(){}
    public Courses(String sID,String sName,String cID, double attendance) {
        this.sID= new SimpleStringProperty(sID);
        this.sName= new SimpleStringProperty(sName);
        this.cID= new SimpleStringProperty(cID);
        this.attn= new SimpleDoubleProperty(attendance);
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

    public double getAttn() {
        return attn.get();
    }

    public DoubleProperty attnProperty() {
        return attn;
    }

    public void setAttn(int attn) {
        this.attn.set(attn);
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
