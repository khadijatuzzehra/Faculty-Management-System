package FacultyManagement;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class Connect {
    public static void main(String[] args) {
        try {
            Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","HR","HR");
            /*Create Statement//
            /*Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("select first_name,last_name, employees.salary from hr.employees");
            while (rs.next()){
                System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getInt(3));
            }*/

            //Prepare Statement//
            /*PreparedStatement pStat=conn.prepareStatement("select first_name,last_name, salary from hr.employees where salary > ? and first_name like ?");
            int salary=15000;
            pStat.setInt(1,salary);
            pStat.setString(2,"S%");
            ResultSet resultSet=pStat.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getString(1)+" "+resultSet.getString(2)+" "+resultSet.getInt(3));
            }*/

            /*Statement statement1=conn.createStatement();
            String myQuery="select first_name,last_name,salary from hr.employees where first_name = ' ";
            String userString="Steven";
            String userString1="Steven' or '1'='1";
            myQuery=myQuery+userString1+"' ";
            ResultSet resultSet1=statement1.executeQuery(myQuery);
            while (resultSet1.next()){
                System.out.println(resultSet1.getString(1)+" "+resultSet1.getString(2)+" "+resultSet1.getInt(3));
            }

            PreparedStatement pStat=conn.prepareStatement("select EMPID from administrator");
            ResultSet resultSet=pStat.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getString(1));
            }*/

            /*PreparedStatement P1=conn.prepareStatement("select EMPID from HR.EMPLOYEE WHERE EMAIL IN ?" );
            P1.setString(1,"noah@gmail.com");
            ResultSet resultSet=P1.executeQuery();
            if (resultSet.next()==false) {
                System.out.println("This is how it works");
            }
            else{
                System.out.println("User Already Exists");
            }*/

            PreparedStatement P2=conn.prepareStatement("SELECT COUNT(EMPID) FROM EMPLOYEE" );
            ResultSet resultSet2=P2.executeQuery();
            while (resultSet2.next()) {
                int a=resultSet2.getInt(1)+1;
                System.out.println(a);
            }
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }
}


