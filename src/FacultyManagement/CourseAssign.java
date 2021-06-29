package FacultyManagement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CourseAssign {
    private StringProperty instructorId;
    private StringProperty instructorName;
    private StringProperty coursesAvailable;

    public CourseAssign(String instructorId, String instructorName, String coursesAvailable) {
        this.instructorId = new SimpleStringProperty(instructorId);
        this.instructorName = new SimpleStringProperty(instructorName);
        this.coursesAvailable = new SimpleStringProperty(coursesAvailable);
    }

    public String getInstructorId() {
        return instructorId.get();
    }

    public StringProperty instructorIdProperty() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId.set(instructorId);
    }

    public String getInstructorName() {
        return instructorName.get();
    }

    public StringProperty instructorNameProperty() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName.set(instructorName);
    }

    public String getCoursesAvailable() {
        return coursesAvailable.get();
    }

    public StringProperty coursesAvailableProperty() {
        return coursesAvailable;
    }

    public void setCoursesAvailable(String coursesAvailable) {
        this.coursesAvailable.set(coursesAvailable);
    }
}
