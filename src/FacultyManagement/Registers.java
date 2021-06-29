package FacultyManagement;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Registers {
    private StringProperty cID;
    private StringProperty cTitle;
    private StringProperty cInst;


    public Registers(){}

    public Registers(String cinst,String cID, String cTitle){
        this.cInst=new SimpleStringProperty(cinst);
        this.cID=new SimpleStringProperty(cID);
        this.cTitle=new SimpleStringProperty(cTitle);
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

    public String getcTitle() {
        return cTitle.get();
    }

    public StringProperty cTitleProperty() {
        return cTitle;
    }

    public void setcTitle(String cTitle) {
        this.cTitle.set(cTitle);
    }

    public String getcInst() {
        return cInst.get();
    }

    public StringProperty cInstProperty() {
        return cInst;
    }

    public void setcInst(String cInst) {
        this.cInst.set(cInst);
    }
}
