package FrontEndFMS;
import FacultyManagement.Registers;
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
import javafx.scene.control.*;

import java.sql.*;

import static FrontEndFMS.Controller.AlertBox;
public class StudentController {

    //LOGIN FXML FIELDS//
    @FXML private Label loginStatus;
    @FXML private PasswordField loginPassword;
    @FXML private TextField loginReg;
    @FXML private Button schButton;
    //SCHOLARSHIP FXML FIELDS//
    @FXML private TextField scholarReg;
    @FXML private TextField doubleCGPA;
    @FXML private TextField intIncome;
    @FXML private PasswordField scholarPass;
    @FXML private Label scholarshipStatus;

    ////COURSES FXML FIELDS//
    @FXML TableView<Registers> tableIdc ;@FXML TableColumn<Registers, String> crsId;
    @FXML TableColumn<Registers, String> crsTitle; @FXML TableColumn<Registers, String> crsIns;
    private ObservableList<Registers> data;
    private static String studentID="";
    //COURSE REGISTRATION//
    public void getCourseInfo(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
            this.data = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("select employee.name,courses.crsid,courses.title from employee, teaches, courses where teaches.teacher_empid=employee.empid and teaches.courses_crsid=courses.crsid");
            while (rs.next()) {
                this.data.add(new Registers(rs.getString(1), rs.getString(2),
                        rs.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.crsId.setCellValueFactory(new PropertyValueFactory<Registers, String>("cID"));
        this.crsTitle.setCellValueFactory(new PropertyValueFactory<Registers, String>("cTitle"));
        this.crsIns.setCellValueFactory(new PropertyValueFactory<Registers,String>("cInst"));
        this.tableIdc.setItems(this.data);
    }
    public void register(ActionEvent event){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("scholarshipApplication.fxml"));
            Controller controller = loader.getController();
            if(tableIdc.getSelectionModel().getSelectedItem()==null)
                AlertBox("Error","Kindly Select a row");
            else
            {
                try
                {
                    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                    PreparedStatement countCourses=conn.prepareStatement("select count(registers.student_sid) from registers where registers.student_sid=? group by registers.student_sid");
                    countCourses.setString(1,studentID);
                    ResultSet cc=countCourses.executeQuery();
                    String crsID=tableIdc.getSelectionModel().getSelectedItem().getcID();
                    PreparedStatement checkCourse = conn.prepareStatement("select courses.crsid from courses where courses.crsid not in (select registers.courses_crsid from registers where student_sid=?)");
                    boolean check=true;
                    checkCourse.setString(1,studentID);
                    ResultSet cc2 = checkCourse.executeQuery();
                    while (cc2.next()) {
                        if (cc2.getString(1).equals(crsID)) {
                            System.out.println("reg");
                            check=false;
                            break;
                        }
                    }
                    int count=1;
                    while (cc.next()){
                        count=cc.getInt(1);
                    }
                    if (count<=5 && check==false){
                        String instructor=tableIdc.getSelectionModel().getSelectedItem().getcInst();
                        PreparedStatement regCourse=conn.prepareStatement("INSERT INTO REGISTERS VALUES(?,?,0,0,?)");
                        regCourse.setString(1,studentID);
                        regCourse.setString(2,crsID);
                        regCourse.setString(3,instructor);
                        regCourse.executeQuery();
                        AlertBox("Alert", "Course Registered");
                    }
                    else {
                        AlertBox("Alert", "Registration not possible.");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
    }

    //DISPLAY WINDOWS//
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

    public void homeStudent(javafx.event.ActionEvent actionEvent) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("homeStudent.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //STUDENT LOGIN//
    public void LoginStudent(ActionEvent event){
        try {
            if (loginReg.getText().isEmpty() || loginPassword.getText().isEmpty()) {
                System.out.println("Empty Text Field");
                AlertBox("Registration Error", "Empty Text field");
                loginStatus.setText("Empty Text field");
            }
            else {
                String regLogin = loginReg.getText();
                String passwordLogin = loginPassword.getText();
                Connection LoginConnect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                PreparedStatement P1 = LoginConnect.prepareStatement("select REG,PASS,SID from HR.STUDENT WHERE REG IN ?");
                P1.setString(1, regLogin);
                ResultSet loginCheck = P1.executeQuery();

                System.out.println(studentID);

                if (loginCheck.next() == false) {
                    AlertBox("Login Error", "No matched data");
                    loginStatus.setText("New User? Register now");
                }
                if (loginCheck.getString(2).equals(passwordLogin)) {
                    studentID=loginCheck.getString(3);
                    Parent tableViewParent = FXMLLoader.load(getClass().getResource("homeStudent.fxml"));
                    Scene tableViewScene = new Scene(tableViewParent);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(tableViewScene);
                    window.show();
                }
                else {
                    AlertBox("Login Error", "Invalid Password");
                    System.out.println("Invalid Password");
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //SCHOLARSHIP APPLICATION//
    public void submitScholarship(ActionEvent event){
        if(scholarReg.getText().isEmpty() || doubleCGPA.getText().isEmpty() || intIncome.getText().isEmpty() || scholarPass.getText().isEmpty()){
            AlertBox("Submission Error","Empty Text Fields");
            scholarshipStatus.setText("Text fields can't be empty");
        }
        //get data from
        String reg = scholarReg.getText();
        String textCgpa = doubleCGPA.getText();
        String textIncome = intIncome.getText();
        double cgpa = Double.parseDouble(textCgpa);
        int income = Integer.parseInt(textIncome);
        String pass=scholarPass.getText();

        try{
            Connection ScholarshipConnect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
            PreparedStatement getCreds=ScholarshipConnect.prepareStatement("select pass from student where reg= ? ");
            PreparedStatement getStatus=ScholarshipConnect.prepareStatement("select SCHOLARSHIP_SCHID from student where reg= ? ");
            getCreds.setString(1,reg);
            getStatus.setString(1,reg);
            ResultSet getCredentials=getCreds.executeQuery();
            ResultSet getSta=getStatus.executeQuery();
            if (getCredentials.next()!=false && (getSta.next()==false)){
                if (getCredentials.getString(1).equals(pass)){
                    String schID=countScholarshipId();
                    PreparedStatement statementInsert1=ScholarshipConnect.prepareStatement("INSERT INTO SCHOLARSHIP VALUES (?,?,?,'2')");
                    statementInsert1.setString(1,schID);
                    statementInsert1.setDouble(2,cgpa);
                    statementInsert1.setInt(3,income);
                    statementInsert1.executeQuery();
                    PreparedStatement statementUpdate1=ScholarshipConnect.prepareStatement("UPDATE STUDENT SET SCHOLARSHIP_SCHID=? WHERE REG=?");
                    statementUpdate1.setString(1,schID);
                    statementUpdate1.setString(2,reg);
                    statementUpdate1.executeQuery();
                    Parent tableViewParent = FXMLLoader.load(getClass().getResource("homeStudent.fxml"));
                    Scene tableViewScene = new Scene(tableViewParent);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(tableViewScene);
                    window.show();
                }
                else{
                AlertBox("Submission Error","Incorrect Password");
                scholarshipStatus.setText("Password not matched");
                }
            }
            else {
                AlertBox("Submission Error","Submission Error Can't Register");
            }

        }catch (Exception e){
            e.printStackTrace();
        }




    }
    public String countScholarshipId(){
        String LeaveIdCount="";
        try {
            Connection conn= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","HR","HR");
            PreparedStatement LeaveId=conn.prepareStatement("SELECT MAX(to_number(SCHID)) FROM SCHOLARSHIP" );
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
}