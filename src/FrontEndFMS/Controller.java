package FrontEndFMS;

//ALL IMPORTS HERE//
import FacultyManagement.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Controller {
    public Button FData1;
    public Button FData11;
    //REG GUI LINKED//
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField emailId;
    @FXML
    private TextField empName;
    @FXML
    private TextField UserAge;
    @FXML
    private TextField contactNum;
    @FXML
    private TextField empQual;
    @FXML
    private TextField empSkills;
    @FXML
    private Label regStatus;

    //LOGIN GUI LINKED//
    @FXML
    private PasswordField loginPassword;
    @FXML
    private TextField loginEmail;
    @FXML
    private Label loginStatus;

    //ACCOUNTANT MANAGED GUI LINKED WITH FXML//
    @FXML
    TableView<Accountant> tableID;
    @FXML
    TableColumn<Accountant, String> accountantID;
    @FXML
    TableColumn<Accountant, String> accountantName;
    @FXML
    TableColumn<Accountant, Integer> accountantSal;
    @FXML
    TableColumn<Accountant, String> accountantStatus;
    @FXML
    TableColumn<Accountant, String> accountantQualification;
    @FXML
    TableColumn<Accountant, Integer> accountantAge;
    @FXML
    TableColumn<Accountant, String> accountantManagedby;
    private ObservableList<Accountant> data2;

    //SCHOLARSHIP MANAGED GUI LINKED WITH FXML//
    @FXML
    TableView<Scholarship> tableIDS;
    @FXML
    TableColumn<Scholarship, String> schID;
    @FXML
    TableColumn<Scholarship, String> studentName;
    @FXML
    TableColumn<Scholarship, String> studentReg;
    @FXML
    TableColumn<Scholarship, Double> studentCGPA;
    @FXML
    TableColumn<Scholarship, Integer> studentIncome;
    @FXML
    TableColumn<Scholarship, String> approveStatus;
    private ObservableList<Scholarship> data3;

    //TEACHER MANAGED GUI LINKED WITH FXML//
    @FXML
    TableView<Teacher> TableID;
    @FXML
    TableColumn<Teacher, String> teacherID;
    @FXML
    TableColumn<Teacher, String> teacherName;
    @FXML
    TableColumn<Teacher, Integer> teacherSal;
    @FXML
    TableColumn<Teacher, String> teacherStatus;
    @FXML
    TableColumn<Teacher, String> teacherRole;
    @FXML
    TableColumn<Teacher, String> teacherQualification;
    @FXML
    TableColumn<Teacher, Integer> teacherAge;
    @FXML
    TableColumn<Teacher, String> teacherManagedby;
    private ObservableList<Teacher> data;

    //LEAVES MANAGED GUI LINKED WITH FXML//
    @FXML
    TableView<LeaveApplicants> TableIDL;
    @FXML
    TableColumn<LeaveApplicants, String> applicantID;
    @FXML
    TableColumn<LeaveApplicants, String> applicantName;
    @FXML
    TableColumn<LeaveApplicants, String> applicantReason;
    @FXML
    TableColumn<LeaveApplicants, String> applicantStatus;
    @FXML
    TableColumn<LeaveApplicants, String> startDate;
    @FXML
    TableColumn<LeaveApplicants, String> endDate;
    private ObservableList<LeaveApplicants> dataLeave;

    //COURSES ASSIGNED//
    @FXML
    TableView<CourseAssign> tableInst;
    @FXML
    TableColumn<CourseAssign, String> instructorId;
    @FXML
    TableColumn<CourseAssign, String> instructorName;
    @FXML
    TableColumn<CourseAssign, String> coursesAvailable;
    private ObservableList<CourseAssign> dataInstructor;

    //LEAVES MANAGED//
    public void loadLeaves(ActionEvent event) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
            this.dataLeave = FXCollections.observableArrayList();
            ResultSet rs3 = conn.createStatement().executeQuery("select leave_applicants.leave_applicants_id, employee.name, leave_applicants.reason,leave.granted, leave_applicants.start_date,leave_applicants.end_date\n" +
                    "from employee,leave,leave_applicants where leave_applicants.leave_applicants_id=leave.applicantid and leave.applicantid=employee.empid");
            while (rs3.next()) {
                this.dataLeave.add(new LeaveApplicants(rs3.getString(1), rs3.getString(2),
                        rs3.getString(3), rs3.getString(4), rs3.getString(5),
                        rs3.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.applicantID.setCellValueFactory(new PropertyValueFactory<LeaveApplicants, String>("leaveApplicantId"));
        this.applicantName.setCellValueFactory(new PropertyValueFactory<LeaveApplicants, String>("leaveApplicantName"));
        this.applicantReason.setCellValueFactory(new PropertyValueFactory<LeaveApplicants, String>("reason"));
        this.applicantStatus.setCellValueFactory(new PropertyValueFactory<LeaveApplicants, String>("status"));
        this.startDate.setCellValueFactory(new PropertyValueFactory<LeaveApplicants, String>("startDate"));
        this.endDate.setCellValueFactory(new PropertyValueFactory<LeaveApplicants, String>("endDate"));
        this.TableIDL.setItems(this.dataLeave);
    }

    public void getLeaveDetails() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("leaveApplication.fxml"));
        Controller controller = loader.getController();
        if (TableIDL.getSelectionModel().getSelectedItem() == null)
            AlertBox("Error", "Kindly Select a row");

        else {
            try {
                String appId = TableIDL.getSelectionModel().getSelectedItem().getLeaveApplicantId();
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                PreparedStatement grantLeave = conn.prepareStatement("update leave set granted='Y' where applicantid=?");
                grantLeave.setString(1, appId);
                grantLeave.executeQuery();
                AlertBox("Alert", "Leave Approved load again to view changes");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteLeave() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("leaveApplication.fxml"));
        Controller controller = loader.getController();
        if (TableIDL.getSelectionModel().getSelectedItem() == null)
            AlertBox("Error", "Kindly Select a row");

        else {
            try {
                String appId = TableIDL.getSelectionModel().getSelectedItem().getLeaveApplicantId();
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                PreparedStatement deleteLeave = conn.prepareStatement("delete from leave where applicantid=?");
                deleteLeave.setString(1, appId);
                deleteLeave.executeQuery();
                PreparedStatement deleteLeave2 = conn.prepareStatement("delete from leave_applicants where  leave_applicants.leave_applicants_id=?");
                deleteLeave2.setString(1, appId);
                deleteLeave2.executeQuery();
                AlertBox("Alert", "Leave rejected");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //TEACHER MANAGED//
    public void loadPerson(ActionEvent event) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
            this.data = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("select employee.empid,employee.name, employee.salary,teacher.hire_status, teacher.role,employee.qualification,employee.age,teacher.hired_by from HR.teacher,HR.employee where teacher.empid=employee.empid");
            while (rs.next()) {
                this.data.add(new Teacher(rs.getString(1), rs.getString(2),
                        rs.getInt(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getInt(7), rs.getString(8)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.teacherID.setCellValueFactory(new PropertyValueFactory<Teacher, String>("employeeId"));
        this.teacherName.setCellValueFactory(new PropertyValueFactory<Teacher, String>("employeeName"));
        this.teacherSal.setCellValueFactory(new PropertyValueFactory<Teacher, Integer>("salary"));
        this.teacherStatus.setCellValueFactory(new PropertyValueFactory<Teacher, String>("hireStatus"));
        this.teacherRole.setCellValueFactory(new PropertyValueFactory<Teacher, String>("role"));
        this.teacherQualification.setCellValueFactory(new PropertyValueFactory<Teacher, String>("qualification"));
        this.teacherAge.setCellValueFactory(new PropertyValueFactory<Teacher, Integer>("age"));
        this.teacherManagedby.setCellValueFactory(new PropertyValueFactory<Teacher, String>("managedBy"));
        this.TableID.setItems(this.data);
    }

    public void getTeacher() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("hireTeacher.fxml"));
        Controller controller = loader.getController();
        if (TableID.getSelectionModel().getSelectedItem() == null)
            AlertBox("Error", "Kindly Select a row");
        else {
            try {
                String empId = TableID.getSelectionModel().getSelectedItem().getEmployeeId();
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                PreparedStatement grantLeave = conn.prepareStatement("update teacher set hire_status='Y' where empid=?");
                grantLeave.setString(1, empId);
                grantLeave.executeQuery();
                //trying something new//
                TextInputDialog inputDialog = new TextInputDialog("Salary");
                inputDialog.initStyle(StageStyle.TRANSPARENT);
                inputDialog.initStyle(StageStyle.UNDECORATED);
                inputDialog.show();
                Button confirmation = (Button) inputDialog.getDialogPane().lookupButton(ButtonType.OK);
                confirmation.setOnAction((ActionEvent) -> {
                    try {
                        if (inputDialog.getEditor().getText() != null && (!inputDialog.getEditor().getText().matches("^[a-zA-Z ]*$"))) {
                            String salary = inputDialog.getEditor().getText();
                            int newSalary = Integer.parseInt(salary);
                            PreparedStatement salDB = conn.prepareStatement("update employee set salary=? where empid=?");
                            salDB.setInt(1, newSalary);
                            salDB.setString(2, empId);
                            salDB.executeQuery();
                            AlertBox("Alert", "Teacher Hired");
                        } else {
                            AlertBox("Alert", "Invalid Salary");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                TextInputDialog inputDialog2 = new TextInputDialog("Lecturer or Professor");
                inputDialog2.initStyle(StageStyle.TRANSPARENT);
                inputDialog2.initStyle(StageStyle.UNDECORATED);
                inputDialog2.show();
                Button confirmation2 = (Button) inputDialog2.getDialogPane().lookupButton(ButtonType.OK);
                confirmation2.setOnAction((ActionEvent) -> {
                    try {
                        if (inputDialog2.getEditor().getText() != null && (inputDialog2.getEditor().getText().matches("^[a-zA-Z ]*$"))) {
                            String role = inputDialog2.getEditor().getText();
                            PreparedStatement roleDB = conn.prepareStatement("update teacher set hired_by='2', role=? where empid=?");
                            roleDB.setString(1, role);
                            roleDB.setString(2, empId);
                            roleDB.executeQuery();
                        }
                        else {
                            AlertBox("Alert", "Invalid Role");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void fireTeacher() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fireTeacher.fxml"));
        Controller controller = loader.getController();
        if (TableID.getSelectionModel().getSelectedItem() == null)
            AlertBox("Error", "Kindly Select a row");

        else {
            try {
                String empId = TableID.getSelectionModel().getSelectedItem().getEmployeeId();
                System.out.println(empId);
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                PreparedStatement grantLeave = conn.prepareStatement("delete from employee where empid=?");
                grantLeave.setString(1, empId);
                grantLeave.executeQuery();
                AlertBox("Alert", "Accountant hired");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //ACCOUNTANT MANAGED//
    public void loadAccountant(ActionEvent event) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
            this.data2 = FXCollections.observableArrayList();
            ResultSet rs1 = conn.createStatement().executeQuery("select employee.empid,employee.name, employee.salary, accountant.hire_status,employee.qualification,employee.age,accountant.manages from HR.accountant,HR.employee where accountant.empid=employee.empid");
            while (rs1.next()) {
                this.data2.add(new Accountant(rs1.getString(1), rs1.getString(2), rs1.getInt(3), rs1.getString(4), rs1.getString(5), rs1.getInt(6), rs1.getString(7)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.accountantID.setCellValueFactory(new PropertyValueFactory<Accountant, String>("employeeId"));
        this.accountantName.setCellValueFactory(new PropertyValueFactory<Accountant, String>("employeeName"));
        this.accountantSal.setCellValueFactory(new PropertyValueFactory<Accountant, Integer>("salary"));
        this.accountantStatus.setCellValueFactory(new PropertyValueFactory<Accountant, String>("hireStatus"));
        this.accountantQualification.setCellValueFactory(new PropertyValueFactory<Accountant, String>("qualification"));
        this.accountantAge.setCellValueFactory(new PropertyValueFactory<Accountant, Integer>("age"));
        this.accountantManagedby.setCellValueFactory(new PropertyValueFactory<Accountant, String>("managedBy"));
        this.tableID.setItems(this.data2);
    }

    public void getAccountant(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("hireAccountant.fxml"));
        Controller controller = loader.getController();
        if (tableID.getSelectionModel().getSelectedItem() == null)
            AlertBox("Error", "Kindly Select a row");

        else {
            try {
                String empId = tableID.getSelectionModel().getSelectedItem().getEmployeeId();
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                PreparedStatement hireAcc = conn.prepareStatement("update accountant set hire_status='Y' where empid=?");
                hireAcc.setString(1, empId);
                hireAcc.executeQuery();
                //trying something new//
                TextInputDialog inputDialog = new TextInputDialog("Set Salary for Employee");
                inputDialog.initStyle(StageStyle.TRANSPARENT);
                inputDialog.initStyle(StageStyle.UNDECORATED);
                inputDialog.show();
                Button confirmation = (Button) inputDialog.getDialogPane().lookupButton(ButtonType.OK);
                confirmation.setOnAction((ActionEvent) -> {
                    try {
                        if (inputDialog.getEditor().getText() != null && (!inputDialog.getEditor().getText().matches("^[a-zA-Z ]*$"))) {
                            String salary = inputDialog.getEditor().getText();
                            int newSalary = Integer.parseInt(salary);
                            PreparedStatement salDB = conn.prepareStatement("update employee set salary=? where empid=?");
                            salDB.setInt(1, newSalary);
                            salDB.setString(2, empId);
                            salDB.executeQuery();

                            PreparedStatement hiredBy = conn.prepareStatement("update accountant set manages='2' where empid=?");
                            hiredBy.setString(2, empId);
                            hiredBy.executeQuery();

                            AlertBox("Alert", "Accountant Hired");
                        } else {
                            AlertBox("Alert", "Please enter the new salary");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void fireAccountant() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("hireAccountant.fxml"));
        Controller controller = loader.getController();
        if (tableID.getSelectionModel().getSelectedItem() == null)
            AlertBox("Error", "Kindly Select a row");

        else {
            try {
                String empId = tableID.getSelectionModel().getSelectedItem().getEmployeeId();
                System.out.println(empId);
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                PreparedStatement fireAcc = conn.prepareStatement("delete from employee where empid=?");
                fireAcc.setString(1, empId);
                fireAcc.executeQuery();
                AlertBox("Alert", "Application Approved");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //SCHOLARSHIP APPLICATIONS MANAGED//
    public void loadScholarship() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
            this.data3 = FXCollections.observableArrayList();
            ResultSet rs3 = conn.createStatement().executeQuery("select scholarship.schid,student.name,student.reg,scholarship.cgpa,scholarship.income,scholarship.administrator_empid from student,scholarship where scholarship.schid=student.scholarship_schid");
            while (rs3.next()) {
                this.data3.add(new Scholarship(rs3.getString(1), rs3.getString(2),
                        rs3.getString(3), rs3.getDouble(4), rs3.getInt(5),
                        rs3.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.schID.setCellValueFactory(new PropertyValueFactory<Scholarship, String>("schId"));
        this.studentName.setCellValueFactory(new PropertyValueFactory<Scholarship, String>("studentName"));
        this.studentReg.setCellValueFactory(new PropertyValueFactory<Scholarship, String>("studentReg"));
        this.studentCGPA.setCellValueFactory(new PropertyValueFactory<Scholarship, Double>("cgpa"));
        this.studentIncome.setCellValueFactory(new PropertyValueFactory<Scholarship, Integer>("income"));
        this.approveStatus.setCellValueFactory(new PropertyValueFactory<Scholarship, String>("managedBy"));
        this.tableIDS.setItems(this.data3);
    }

    public void getScholarshipDetails(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("scholarshipApplication.fxml"));
        Controller controller = loader.getController();
        if (tableIDS.getSelectionModel().getSelectedItem() == null)
            AlertBox("Error", "Kindly Select a row");
        else {
            try {
                String sID = tableIDS.getSelectionModel().getSelectedItem().getSchId();
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                PreparedStatement grantSch = conn.prepareStatement("update scholarship set administrator_empid='2' where schid=?");
                grantSch.setString(1, sID);
                grantSch.executeQuery();
                AlertBox("Alert", "Application Approved");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void rejectScholarship() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("scholarshipApplication.fxml"));
        Controller controller = loader.getController();
        if (tableIDS.getSelectionModel().getSelectedItem() == null)
            AlertBox("Error", "Kindly Select a row");
        else {
            try {
                String sID = tableIDS.getSelectionModel().getSelectedItem().getSchId();
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                PreparedStatement rejectSch = conn.prepareStatement("DELETE FROM SCHOLARSHIP WHERE schid=?");
                rejectSch.setString(1, sID);
                rejectSch.executeQuery();
                AlertBox("Alert", "Application Rejected");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void manageScholarship(javafx.event.ActionEvent actionEvent) {
        try {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("scholarshipApplications.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //DISPLAY LEAVE APPLICATION IN JTABLE//
    public void manageLeaves(javafx.event.ActionEvent actionEvent) {
        try {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("manageLeaves.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //DISPLAY THIS AFTER LOGIN//
    public void homeAdmin(javafx.event.ActionEvent actionEvent) {
        try {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("homeAdmin.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ON EXIT ACTION CLOSE APPLICATION//
    public void close(javafx.event.ActionEvent event) {
        try {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ON BACK ACTION DISPLAY STATUS WINDOW//
    public void Status(javafx.event.ActionEvent actionEvent) {
        try {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("StatusWindow.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //DISPLAY ACCOUNTANT IN JTABLE//
    public void manageAccountant(javafx.event.ActionEvent event) {
        try {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("hireAccountant.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //DISPLAY TEACHER IN JTABLE//
    public void manageTeacher(javafx.event.ActionEvent event) {
        try {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("hireTeacher.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void CourseTeacher(javafx.event.ActionEvent event) {
        try {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("CourseAssigns.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void TeacherOpts(javafx.event.ActionEvent event) {
        try {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("teacherManageOpt.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //STATIC METHOD USE FOR ALERT BOX//
    public static void AlertBox(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(300);
        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close window");
        closeButton.setOnAction(e -> window.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public void LoginAdministrator(ActionEvent event) {
        try {

            if (loginEmail.getText().isEmpty() || loginPassword.getText().isEmpty()) {
                System.out.println("Empty Text Field");
                AlertBox("Registration Error", "Empty Text field");
                loginStatus.setText("Empty Text field");
            } else {
                String emailLogin = loginEmail.getText();
                String passwordLogin = loginPassword.getText();
                Connection LoginConnect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                PreparedStatement P1 = LoginConnect.prepareStatement("select email,password from HR.EMPLOYEE WHERE EMAIL IN ?");
                P1.setString(1, emailLogin);
                ResultSet loginCheck = P1.executeQuery();
                PreparedStatement P3 = LoginConnect.prepareStatement("select password from employee where empid in(select empid from administrator where empid in (select empid from employee where email=?))");
                P3.setString(1, emailLogin);
                ResultSet loginCheck3 = P3.executeQuery();

                if (loginCheck.next() == false || loginCheck3.next() == false) {
                    AlertBox("Login Error", "No matched data");
                    loginStatus.setText("New User? Register now");
                } else {
                    if (loginCheck.getString(2).equals(passwordLogin)) {
                        Parent tableViewParent = FXMLLoader.load(getClass().getResource("homeAdmin.fxml"));
                        Scene tableViewScene = new Scene(tableViewParent);
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(tableViewScene);
                        window.show();
                    } else {
                        AlertBox("Login Error", "Invalid Password");
                        loginStatus.setText("Invalid Login Details");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ASSIGN COURSES//
    public void CourseAssignLoad() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
            this.dataInstructor = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("select employee.empid,courses.crsid,employee.name from employee,teacher,courses where teacher.empid=employee.empid");
            while (rs.next()) {
                this.dataInstructor.add(new CourseAssign(rs.getString(1), rs.getString(2),
                        rs.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.instructorId.setCellValueFactory(new PropertyValueFactory<CourseAssign, String>("instructorId"));
        this.instructorName.setCellValueFactory(new PropertyValueFactory<CourseAssign, String>("instructorName"));
        this.coursesAvailable.setCellValueFactory(new PropertyValueFactory<CourseAssign, String>("coursesAvailable"));
        this.tableInst.setItems(this.dataInstructor);
    }

    public void CourseAssign() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("CoursesAssign.fxml"));
        Controller controller = loader.getController();
        if (tableInst.getSelectionModel().getSelectedItem() == null)
            AlertBox("Error", "Kindly Select a row");
        else {
            try {
                String instructor = tableInst.getSelectionModel().getSelectedItem().getInstructorId();
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                PreparedStatement countCourses = conn.prepareStatement("select count(teaches.teacher_empid) from teaches where teacher_empid=?");
                countCourses.setString(1, instructor);
                ResultSet cc = countCourses.executeQuery();
                String crsID = tableInst.getSelectionModel().getSelectedItem().getInstructorName();
                System.out.println(crsID);
                PreparedStatement checkCourse = conn.prepareStatement("select courses.crsid from courses where courses.crsid not in (select teaches.courses_crsid from teaches where teacher_empid=?)");
                checkCourse.setString(1,instructor);
                ResultSet cc2 = checkCourse.executeQuery();
                boolean check=true;
                while (cc2.next()) {
                    if (cc2.getString(1).equals(crsID)) {
                        System.out.println("reg");
                        check=false;
                        break;
                    }
                }
                int count = 1;
                while (cc.next()) {
                    count = cc.getInt(1);
                }
                if (count <= 3 && check==false) {
                    PreparedStatement assCourse = conn.prepareStatement("INSERT INTO TEACHES VALUES(?,?)");
                    assCourse.setString(1, instructor);
                    assCourse.setString(2, crsID);
                    assCourse.executeQuery();
                    AlertBox("Alert", "Course Assigned");
                } else {
                    AlertBox("Alert", "Course Assignment not possible.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}