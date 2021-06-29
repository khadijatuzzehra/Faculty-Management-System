package FrontEndFMS;

//IMPORTS//
import FacultyManagement.*;
import javafx.application.Preloader;
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
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import static FrontEndFMS.Controller.AlertBox;

public class AccountantController {

    public Button FData;
    public Button FData1;
    public Button FData21;
    //REGISTRATION FIELD
    @FXML private PasswordField txtPassword;@FXML private TextField emailId;
    @FXML private TextField empName;@FXML private TextField UserAge;
    @FXML private TextField contactNum; @FXML private TextField empQual;
    @FXML private Label regStatus;@FXML private Label loginStatus;

    //LOGIN FIELDS
    @FXML private PasswordField loginPassword;
    @FXML private TextField loginEmail;

    //LEAVE APPLICATION FIELD
    @FXML private TextField leaveApplicantMail;@FXML private TextField leaveReason;
    @FXML private DatePicker leaveStartDate; @FXML private DatePicker leaveEndDate;
    @FXML private PasswordField leaveConfirmation; @FXML private Label applicationStatus;

    //FEE MANAGEMENT FIELDS//
    @FXML TableView<Student> tableIDs;@FXML TableColumn<Student, String> sID;
    @FXML TableColumn<Student, String> studentName; @FXML TableColumn<Student, String> studentReg;
    @FXML TableColumn<Student,Double> studentCGPA;@FXML TableColumn<Student, String> feeStatus;
    @FXML TableColumn<Student, String> schid;
    private ObservableList<Student> data;

    @FXML TableView<PayrollManagement> tableID;@FXML TableColumn<PayrollManagement, String> tID;
    @FXML TableColumn<PayrollManagement, String> tEmail; @FXML TableColumn<PayrollManagement, String> tName;
    @FXML TableColumn<PayrollManagement,Integer> tSalary;@FXML TableColumn<PayrollManagement, String> tRole;
    private ObservableList<PayrollManagement> teacherData;
    private static String accountant;
    public void loadTeachers(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
            this.teacherData = FXCollections.observableArrayList();
            ResultSet rs1 = conn.createStatement().executeQuery("select employee.empid,employee.email,employee.name,employee.salary,teacher.role from employee,teacher where teacher.empid=employee.empid");
            while (rs1.next()) {
                this.teacherData.add(new PayrollManagement(rs1.getString(1), rs1.getString(2), rs1.getString(3),rs1.getInt(4),rs1.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.tID.setCellValueFactory(new PropertyValueFactory<PayrollManagement, String>("teacherId"));
        this.tEmail.setCellValueFactory(new PropertyValueFactory<PayrollManagement, String>("teacherEmail"));
        this.tName.setCellValueFactory(new PropertyValueFactory<PayrollManagement,String>("teacherName"));
        this.tSalary.setCellValueFactory(new PropertyValueFactory<PayrollManagement, Integer>("teacherSalary"));
        this.tRole.setCellValueFactory(new PropertyValueFactory<PayrollManagement,String>("teacherRole"));
        this.tableID.setItems(this.teacherData);
    }

    public void payTeacher(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("scholarshipApplication.fxml"));
        Controller controller = loader.getController();
        if(tableID.getSelectionModel().getSelectedItem()==null)
            AlertBox("Error","Kindly Select a row");
        else
        {
            try
            {
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String dateInString = formatter.format(new Date());
                String split=dateInString.substring(0,2);
                System.out.println(split);
                if (split.equals("24") || split.equals("02")){
                    String tID=tableID.getSelectionModel().getSelectedItem().getTeacherId();
                    int tSal=tableID.getSelectionModel().getSelectedItem().getTeacherSalary();
                    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                    PreparedStatement checkIfPaid= conn.prepareStatement("select accountant_empid from pays where teacher_empid=?");
                    checkIfPaid.setString(1,tID);
                    ResultSet checkPaid= checkIfPaid.executeQuery();
                    if (checkPaid.next()!=false){
                        PreparedStatement removePrev=conn.prepareStatement("delete from pays");
                        removePrev.executeQuery();
                    }
                    else {
                        PreparedStatement removePrev = conn.prepareStatement("delete from pays where teacher_empid=?");
                        removePrev.setString(1,tID);
                        removePrev.executeQuery();
                        PreparedStatement getBalance = conn.prepareStatement("select salary from employee where empid='2'");
                        ResultSet getB = getBalance.executeQuery();
                        int currBalance = 0;
                        while (getB.next()) {
                            currBalance = getB.getInt(1);
                        }
                        currBalance = currBalance - tSal;
                        PreparedStatement setBalance = conn.prepareStatement("update employee set salary=? where empid='2'");
                        setBalance.setInt(1, currBalance);
                        setBalance.executeQuery();
                        PreparedStatement insertPays = conn.prepareStatement("insert into pays values(?,?)");
                        insertPays.setString(1, accountant);
                        insertPays.setString(2, tID);
                        insertPays.executeQuery();
                        AlertBox("Alert", "Teacher Paid");
                    }
                }
                else {
                    AlertBox("Alert", "You can pay on 1st and 2nd of every month");
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }











    public void showPays(javafx.event.ActionEvent actionEvent) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("AccountantPaysTeachers.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //STUDENT FEE MANAGEMENT//
    public void loadStudent(ActionEvent event){
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
            this.data = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("select student.sid,student.name,student.reg,scholarship.cgpa,student.fee_status,student.scholarship_schid from student,scholarship where student.scholarship_schid=scholarship.schid");
            while (rs.next()) {
                this.data.add(new Student(rs.getString(1), rs.getString(2),
                        rs.getString(3),rs.getDouble(4),rs.getString(5),rs.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.sID.setCellValueFactory(new PropertyValueFactory<Student, String>("studentId"));
        this.studentName.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        this.studentReg.setCellValueFactory(new PropertyValueFactory<Student,String>("regNum"));
        this.studentCGPA.setCellValueFactory(new PropertyValueFactory<Student, Double>("CGPA"));
        this.feeStatus.setCellValueFactory(new PropertyValueFactory<Student,String>("feeStatus"));
        this.schid.setCellValueFactory(new PropertyValueFactory<Student,String>("scholarshipId"));
        this.tableIDs.setItems(this.data);
    }
    public void removeStudent(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("scholarshipApplication.fxml"));
        Controller controller = loader.getController();
        if(tableIDs.getSelectionModel().getSelectedItem()==null)
            AlertBox("Error","Kindly Select a row");
        else
        {
            try
            {
                String sID=tableIDs.getSelectionModel().getSelectedItem().getStudentId();
                String fee =tableIDs.getSelectionModel().getSelectedItem().getFeeStatus();
                String sch =tableIDs.getSelectionModel().getSelectedItem().getScholarshipId();
                if(fee == "N"){
                    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                    PreparedStatement rejectStd=conn.prepareStatement("DELETE FROM STUDENT WHERE SID=?");
                    rejectStd.setString(1,sID);
                    rejectStd.executeQuery();
                    if (sch!=null){
                        PreparedStatement rejectSch=conn.prepareStatement("DELETE FROM SCHOLARSHIP WHERE SCID=?");
                        rejectSch.setString(1,sch);
                        rejectSch.executeQuery();
                    }
                    AlertBox("Alert", "Student Removed from Record");
                }
                else{
                    AlertBox("Alert", "Student can't be removed. Fee Paid");
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    //DISPLAYING FXML WINDOWS//
    public void close(javafx.event.ActionEvent event) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void Status(javafx.event.ActionEvent actionEvent) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("StatusWindow.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void showStudent(javafx.event.ActionEvent actionEvent) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("studentFeeMan.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void homeAccountant(ActionEvent actionEvent){
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("HomeAccountant.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //ACCOUNTANT APPLY AND LOGIN//
    public void applyAccountant(javafx.event.ActionEvent event) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("applyAccountant.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String countEmp(){
        String empCount="";
        try {
            Connection conn= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","HR","HR");
            PreparedStatement P2=conn.prepareStatement("SELECT MAX(to_number(EMPID)) FROM EMPLOYEE" );
            ResultSet resultSet2=P2.executeQuery();
            int a=0;
            while (resultSet2.next()) {
                a=resultSet2.getInt(1)+1;
            }
            empCount=Integer.toString(a);
        }catch (Exception e){
            e.printStackTrace();
        }
        return empCount;
    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public void RegisterUser(javafx.event.ActionEvent event){
        try {

            if (emailId.getText().isEmpty() || txtPassword.getText().isEmpty() || UserAge.getText().isEmpty() || empName.getText().isEmpty()){
                AlertBox("Registration Error","Empty Text field");
                regStatus.setText("Empty Text field");
            }
            boolean emailCheck=isValidEmailAddress(emailId.getText());
            if (!emailCheck || (txtPassword.getText().length() < 8) || (txtPassword.getText().length() > 10) || (UserAge.getText().matches("^[a-zA-Z ]*$")) || (contactNum.getText().matches("^[a-zA-Z ]*$")) || (!empQual.getText().matches("^[a-zA-Z ]*$")) || (!empName.getText().matches("^[a-zA-Z ]*$"))){
                AlertBox("Registration Error","Invalid Credentials");
                regStatus.setText("Improper email or password format");
            }
            else {
                String emailText = emailId.getText();
                String name = empName.getText();
                String age = UserAge.getText();
                int userAge = Integer.parseInt(age);
                String contact = contactNum.getText();
                String qualification = empQual.getText();
                String password = txtPassword.getText();
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                PreparedStatement P1 = conn.prepareStatement("select EMPID from HR.EMPLOYEE WHERE EMAIL IN ?");
                P1.setString(1, emailText);
                ResultSet resultSet = P1.executeQuery();
                if (resultSet.next() == false) {
                    int a = 0;
                    String empId = countEmp();
                    PreparedStatement insertEmp = conn.prepareStatement("INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                    insertEmp.setString(1, empId);
                    insertEmp.setString(2, emailText);
                    insertEmp.setString(3, name);
                    insertEmp.setInt(4, 1500000);
                    insertEmp.setString(5, contact);
                    insertEmp.setInt(6, userAge);
                    insertEmp.setString(7, qualification);
                    insertEmp.setString(8, password);
                    insertEmp.executeQuery();

                    PreparedStatement insertAccountant = conn.prepareStatement("insert into ACCOUNTANT values (? ,'N', null)");
                    insertAccountant.setString(1, empId);
                    ResultSet resultSet3 = insertAccountant.executeQuery();
                    Parent tableViewParent = FXMLLoader.load(getClass().getResource("loginAccountant.fxml"));
                    Scene tableViewScene = new Scene(tableViewParent);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(tableViewScene);
                    window.show();
                }
                else {
                    AlertBox("Registration Error", "User Already Exists");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
    public void LoginAccountant(ActionEvent event){
        try {
            if (loginEmail.getText().isEmpty() || loginPassword.getText().isEmpty()) {
                AlertBox("Registration Error", "Empty Text field");
                loginStatus.setText("Empty Text field");
            }
            else {
                String emailLogin=loginEmail.getText();
                String passwordLogin=loginPassword.getText();
                Connection LoginConnect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                PreparedStatement P1 = LoginConnect.prepareStatement("select email,password from HR.EMPLOYEE WHERE EMAIL IN ?");
                P1.setString(1, emailLogin);
                PreparedStatement P2 = LoginConnect.prepareStatement("select empid,hire_status from accountant where empid=(select empid from employee where employee.email= ?)");
                P2.setString(1, emailLogin);
                ResultSet loginCheck = P1.executeQuery();
                ResultSet loginCheck2 = P2.executeQuery();
                PreparedStatement P3 = LoginConnect.prepareStatement("select password from employee where empid in(select empid from accountant where empid in (select empid from employee where email=?))");
                P3.setString(1, emailLogin);
                ResultSet loginCheck3 = P3.executeQuery();

                if (loginCheck.next() == false || loginCheck3.next()== false) {
                        AlertBox("Login Error", "No matched data");
                        loginStatus.setText("New User? Register now");
                }
                while (loginCheck2.next()){
                    if (loginCheck2.getString(2).equals("Y")) {
                        if (loginCheck.getString(2).equals(passwordLogin)){
                            accountant=loginCheck2.getString(1);
                            Parent tableViewParent = FXMLLoader.load(getClass().getResource("HomeAccountant.fxml"));
                            Scene tableViewScene = new Scene(tableViewParent);
                            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            window.setScene(tableViewScene);
                            window.show();
                        }
                        else {
                            AlertBox("Login Error", "Invalid Password");
                            loginStatus.setText("Invalid Login Details");
                        }
                    } else {
                        AlertBox("Login Error", "No matched data");
                        loginStatus.setText("Sorry, You are not hired now.");
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    //LEAVE APPLICATION//
    public void showLeaveApplication(javafx.event.ActionEvent actionEvent) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("leaveApplication.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String countLeaveId(){
        String LeaveIdCount="";
        try {
            Connection conn= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","HR","HR");
            PreparedStatement LeaveId=conn.prepareStatement("SELECT MAX(to_number(LEAVEID)) FROM LEAVE" );
            ResultSet resultLeaveId= LeaveId.executeQuery();
            int a=0;
            while (resultLeaveId.next()) {
                a=resultLeaveId.getInt(1)+1;
            }
            LeaveIdCount=Integer.toString(a);
        }catch (Exception e){
            e.printStackTrace();
        }
        return LeaveIdCount;
    }
    public void submitLeaveApplication(ActionEvent event){
        if (leaveApplicantMail.getText().isEmpty() || leaveReason.getText().isEmpty() ||leaveStartDate.getValue()==null || leaveEndDate.getValue()==null){
            AlertBox("Login Error", "Empty Data Fields");
            applicationStatus.setText("Fill in the text fields");
        }
        else{
                LocalDate sDate = leaveStartDate.getValue();
                String formattedStartDate = sDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
                LocalDate eDate = leaveEndDate.getValue();
                String formattedEndDate = eDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
                String applicantMail=leaveApplicantMail.getText();
                String leaveRes=leaveReason.getText();
                String leaveAppEmpID="";

                try {
                    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                    PreparedStatement lempid=conn.prepareStatement("select empid from employee where email=?");
                    PreparedStatement lempid2=conn.prepareStatement("select password from employee where email=?");
                    lempid.setString(1,applicantMail);
                    lempid2.setString(1,applicantMail);
                    ResultSet getEmp=lempid.executeQuery();
                    ResultSet getPass=lempid2.executeQuery();
                    PreparedStatement check=conn.prepareStatement("select reason from leave_applicants where leave_applicants_id=?");
                    if ((getEmp.next() != false) && getPass.next()!=false) {
                        check.setString(1,getEmp.getString(1));
                        ResultSet checkRes=check.executeQuery();
                        if (getPass.getString(1).equals(leaveConfirmation.getText()) && (checkRes.next()==false)){
                        leaveAppEmpID = getEmp.getString(1);
                        PreparedStatement pStat2 = conn.prepareStatement("insert into LEAVE_APPLICANTS (LEAVE_APPLICANTS_ID,REASON,START_DATE,END_DATE,TEACHER_EMPID,ACCOUNTANT_EMPID) values (?, ?, TO_DATE(? , 'DD/MM/YYYY'),TO_DATE(? , 'DD/MM/YYYY'),null,null)");
                        pStat2.setString(1, leaveAppEmpID);
                        pStat2.setString(2,leaveRes);
                        pStat2.setString(3, formattedStartDate);
                        pStat2.setString(4, formattedEndDate);
                        pStat2.executeQuery();
                        String leaveId=countLeaveId();
                        PreparedStatement pStat3 = conn.prepareStatement("insert into LEAVE values (?, ?, ?, ?)");
                        pStat3.setString(1,leaveId);
                        pStat3.setString(2,"N");
                        pStat3.setString(3,null);
                        pStat3.setString(4,leaveAppEmpID);
                        pStat3.executeQuery();

                        Parent tableViewParent = FXMLLoader.load(getClass().getResource("HomeAccountant.fxml"));
                        Scene tableViewScene = new Scene(tableViewParent);
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(tableViewScene);
                        window.show();
                    }
                        else {
                            AlertBox("Submission Error", "Try again later");
                            applicationStatus.setText("Check your password again");
                        }
                    }
                    else {
                        AlertBox("Submission Error", "Matched Data Not Found");
                        applicationStatus.setText("Check your email again");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
