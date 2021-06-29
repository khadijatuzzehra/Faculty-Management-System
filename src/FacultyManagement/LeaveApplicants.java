package FacultyManagement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class LeaveApplicants {
    private StringProperty leaveApplicantId;
    private StringProperty leaveApplicantName;
    private StringProperty reason;
    private StringProperty status;
    private StringProperty startDate;
    private StringProperty endDate;

    public LeaveApplicants(String leaveApplicantId, String leaveAppName, String reason, String status, String startDate, String endDate) {
        this.leaveApplicantId = new SimpleStringProperty(leaveApplicantId);
        this.leaveApplicantName = new SimpleStringProperty(leaveAppName);
        this.reason=new SimpleStringProperty(reason);
        this.status=new SimpleStringProperty(status);
        this.startDate = new SimpleStringProperty(startDate);
        this.endDate = new SimpleStringProperty(endDate);
    }
    public LeaveApplicants (LeaveApplicants leaveApplicants){
        this.leaveApplicantId=leaveApplicants.leaveApplicantId;
        this.leaveApplicantName=leaveApplicants.leaveApplicantName;
        this.reason=leaveApplicants.reason;
        this.status=leaveApplicants.status;
        this.startDate=leaveApplicants.startDate;
        this.endDate=leaveApplicants.endDate;
    }

    public String getLeaveApplicantId() {
        return leaveApplicantId.get();
    }

    public StringProperty leaveApplicantIdProperty() {
        return leaveApplicantId;
    }

    public void setLeaveApplicantId(String leaveApplicantId) {
        this.leaveApplicantId.set(leaveApplicantId);
    }

    public String getLeaveApplicantName() {
        return leaveApplicantName.get();
    }

    public StringProperty leaveApplicantNameProperty() {
        return leaveApplicantName;
    }

    public void setLeaveApplicantName(String leaveApplicantName) {
        this.leaveApplicantName.set(leaveApplicantName);
    }

    public String getReason() {
        return reason.get();
    }

    public StringProperty reasonProperty() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason.set(reason);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getStartDate() {
        return startDate.get();
    }

    public StringProperty startDateProperty() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }

    public String getEndDate() {
        return endDate.get();
    }

    public StringProperty endDateProperty() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate.set(endDate);
    }
}
