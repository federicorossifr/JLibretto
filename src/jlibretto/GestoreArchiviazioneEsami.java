package jlibretto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.application.Platform;

public class GestoreArchiviazioneEsami{
    private Connection dataConnection;
    private static GestoreArchiviazioneEsami instance;
    private final String insertExamQuery = "INSERT INTO exam(name,credits,mark,date) VALUES(?,?,?,?)";
    private final String editExamQuery = "UPDATE exam SET name = ?,credits=?,mark=?,date=? WHERE id = ?";
    private final String readExamsQuery = "SELECT * FROM exam";
    private GestoreArchiviazioneEsami() {
        try {
            dataConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/prg", "root","");
        } catch(SQLException e) {
            System.out.println("Impossibile connettersi al database: "+e.getMessage());
            Platform.exit();
            System.exit(-1);
        }
    }
    
    public static  GestoreArchiviazioneEsami getInstance() {
        if(instance == null)
            instance = new GestoreArchiviazioneEsami();
        return instance;
    }
    
    
    public int insertExam(Esame e) {
        try {
            PreparedStatement ips = dataConnection.prepareStatement(insertExamQuery,Statement.RETURN_GENERATED_KEYS);
            ips.setString(1, e.getNome());
            ips.setInt(2,e.getCrediti());
            ips.setInt(3,e.getValutazione());
            ips.setDate(4,Date.valueOf(LocalDate.parse(e.getData())));
            int inserted = ips.executeUpdate();
            System.out.println("Inserted: "+inserted);
            ResultSet idResult = ips.getGeneratedKeys();
            int id = -1;
            if(idResult.next()) {
                id = idResult.getInt(1);
                System.out.println("Inserted: "+id);
            }
            return id;
        } catch (SQLException ex) {
            System.out.println("Impossibile inserire l\'esame: "+ex.getMessage());
            return -1;
        }        
    }
    
    public void readExams() {
        try {
            System.out.println("Loading from database");
            PreparedStatement ips = dataConnection.prepareStatement(readExamsQuery);
            ResultSet ers = ips.executeQuery();
            Esame e;
            while(ers.next()) {
                e = new Esame(ers.getInt("id"),ers.getString("name"),ers.getInt("mark"),ers.getInt("credits"),ers.getDate("date").toLocalDate());
                System.out.println(e.getNome());
                RisorsaListaEsami.getInstance().addExam(e);
            }
            
        } catch(SQLException ex) {
            System.out.println("Impossibile recuperare gli esami: "+ex.getMessage());
        }
    }
    
    public boolean editExam(Esame e) {
        try {
            PreparedStatement eps = dataConnection.prepareStatement(editExamQuery);
            eps.setString(1, e.getNome());
            eps.setInt(2, e.getCrediti());
            eps.setInt(3, e.getValutazione());
            eps.setDate(4,Date.valueOf(LocalDate.parse(e.getData())));
            eps.setInt(5,e.getId());
            int affectedRows = eps.executeUpdate();
            System.out.println("Affected: " +affectedRows);
            return affectedRows > 0;
        } catch(SQLException ex) {
            System.out.println("Impossibile modificare l\'esame: "+ex.getMessage());
            return false;
        }
    }
}
