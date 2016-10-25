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
    private Connection connessioneDatabase;
    private static GestoreArchiviazioneEsami _istanza;
    private final String queryInserimentoEsame = "INSERT INTO exam(name,credits,mark,date,usercode) VALUES(?,?,?,?,?)";
    private final String queryModificaEsame = "UPDATE exam SET name = ?,credits=?,mark=?,date=? WHERE id = ?";
    private final String queryLetturaEsami = "SELECT * FROM exam WHERE usercode=?";
    private GestoreArchiviazioneEsami() {
        try {
            connessioneDatabase = DriverManager.getConnection("jdbc:mysql://localhost:3306/prg", "root","");
        } catch(SQLException e) {
            System.out.println("Impossibile connettersi al database: "+e.getMessage());
            Platform.exit();
            System.exit(-1);
        }
    }
    
    public static  GestoreArchiviazioneEsami getIstanza() {
        if(_istanza == null)
            _istanza = new GestoreArchiviazioneEsami();
        return _istanza;
    }
    
    
    public int inserisciEsame(Esame e) {
        try {
            PreparedStatement ips = connessioneDatabase.prepareStatement(queryInserimentoEsame,Statement.RETURN_GENERATED_KEYS);
            ips.setString(1, e.getNome());
            ips.setInt(2,e.getCrediti());
            ips.setInt(3,e.getValutazione());
            ips.setDate(4,Date.valueOf(LocalDate.parse(e.getData())));
            ips.setString(5,e.getCodiceUtente());
            ips.executeUpdate();
            ResultSet idResult = ips.getGeneratedKeys();
            int id = -1;
            if(idResult.next()) {
                id = idResult.getInt(1);
            }
            return id;
        } catch (SQLException ex) {
            System.out.println("Impossibile inserire l\'esame: "+ex.getMessage());
            return -1;
        }        
    }
    
    public void leggiEsami(String codiceUtente) {
        try {
            PreparedStatement ips = connessioneDatabase.prepareStatement(queryLetturaEsami);
            ips.setString(1, codiceUtente);
            ResultSet ers = ips.executeQuery();
            Esame e;
            while(ers.next()) {
                e = new Esame(ers.getInt("id"),
                              ers.getString("name"),
                              ers.getInt("mark"),
                              ers.getInt("credits"),
                              ers.getDate("date").toLocalDate(),
                              ers.getString("usercode"));
                System.out.println(e.getNome());
                RisorsaListaEsami.getIstanza().aggiungiEsame(e);
            }
            
        } catch(SQLException ex) {
            System.out.println("Impossibile recuperare gli esami: "+ex.getMessage());
        }
    }
    
    public boolean modificaEsame(Esame e) {
        try {
            PreparedStatement eps = connessioneDatabase.prepareStatement(queryModificaEsame);
            eps.setString(1, e.getNome());
            eps.setInt(2, e.getCrediti());
            eps.setInt(3, e.getValutazione());
            eps.setDate(4,Date.valueOf(LocalDate.parse(e.getData())));
            eps.setInt(5,e.getId());
            int affectedRows = eps.executeUpdate();
            return affectedRows > 0;
        } catch(SQLException ex) {
            System.out.println("Impossibile modificare l\'esame: "+ex.getMessage());
            return false;
        }
    }
}
