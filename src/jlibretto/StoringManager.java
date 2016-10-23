package jlibretto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.application.Platform;

public class StoringManager{
    private Connection dataConnection;
    private static StoringManager instance;
    private final String insertExamQuery = "INSERT INTO exam(name,credits,mark,date) VALUES(?,?,?,?)";
    private final String readExamsQuery = "SELECT * FROM exam";
    private StoringManager() {
        try {
            dataConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/prg", "root","");
        } catch(SQLException e) {
            System.out.println("Impossibile connettersi al database: "+e.getMessage());
            Platform.exit();
            System.exit(1);
        }
    }
    
    public static  StoringManager getInstance() {
        if(instance == null)
            instance = new StoringManager();
        return instance;
    }
    
    
    public boolean insertExam(Exam e) {
        try {
            PreparedStatement ips = dataConnection.prepareStatement(insertExamQuery);
            ips.setString(1, e.getName());
            ips.setInt(2,e.getCredits());
            ips.setInt(3,e.getMark());
            ips.setDate(4,Date.valueOf(LocalDate.parse(e.getDate())));
            int affectedRows = ips.executeUpdate();
            System.out.println(affectedRows);
            return affectedRows > 0;
        } catch (SQLException ex) {
            System.out.println("Impossibile inserire l\'esame: "+ex.getMessage());
            return false;
        }        
    }
    
    public void readExams() {
        try {
            System.out.println("Loading from database");
            PreparedStatement ips = dataConnection.prepareStatement(readExamsQuery);
            ResultSet ers = ips.executeQuery();
            Exam e;
            while(ers.next()) {
                e = new Exam(ers.getString("name"),ers.getInt("mark"),ers.getInt("credits"),ers.getDate("date").toLocalDate());
                System.out.println(e.getName());
                ExamObservableList.getInstance().addExam(e);
            }
            
        } catch(SQLException ex) {
            System.out.println("Impossibile recuperare gli esami: "+ex.getMessage());
            return;
        }
    }
}
