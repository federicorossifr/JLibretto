//////////////////////////////////////////////////////
package jlibretto.backend;

import jlibretto.middleware.Esame;
import jlibretto.frontend.GestoreParametriConfigurazioneXML;
import java.sql.*;
import javafx.collections.*;

public class GestoreArchiviazioneEsami{
    private final String queryInserimentoEsame = "INSERT INTO esame(codiceEsame,valutazione,data) VALUES(?,?,?)";
    private final String queryModificaEsame = "UPDATE esame SET valutazione=?,data=? WHERE id = ?";
    private final String queryLetturaEsamiSvolti = "SELECT * FROM esame NATURAL JOIN esami;";
    private final String queryLetturaEsamiDisponibili = "SELECT * FROM esami";
    private final String queryRimozioneEsame = "DELETE FROM esame WHERE id = ?";
    private final String URIConnessioneDB;
    private final String utenteDB;
    private final String passwordDB;
    
    public GestoreArchiviazioneEsami() {
        String hDB = GestoreParametriConfigurazioneXML.ottieniParametriConfigurazione().HostnameDatabase;
        int pDB = GestoreParametriConfigurazioneXML.ottieniParametriConfigurazione().PortaDatabase;
        String nDB = "prg";
        
        utenteDB = GestoreParametriConfigurazioneXML.ottieniParametriConfigurazione().UtenteDatabase;
        passwordDB = GestoreParametriConfigurazioneXML.ottieniParametriConfigurazione().PasswordDatabase;   
        URIConnessioneDB = "jdbc:mysql://"+hDB+":"+pDB+"/"+nDB;
    }

    public int inserisciEsame(Esame e) {
        try(
            Connection connessioneDatabase = DriverManager.getConnection(URIConnessioneDB,utenteDB,passwordDB);
            PreparedStatement ips = connessioneDatabase.prepareStatement(queryInserimentoEsame,Statement.RETURN_GENERATED_KEYS);
        ) {
            ips.setInt(1, e.getCodiceEsame());
            ips.setInt(2,e.getValutazione());
            ips.setDate(3,Date.valueOf(e.getData()));
            ips.executeUpdate();
            ResultSet risID = ips.getGeneratedKeys();
            if(risID.next())
                return risID.getInt(1);
            return -1;
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return -1;
        }        
    }
    
    public void leggiEsamiSvolti(ObservableList<Esame> l) {
        try (
            Connection connessioneDatabase = DriverManager.getConnection(URIConnessioneDB,utenteDB,passwordDB);                
            PreparedStatement ips = connessioneDatabase.prepareStatement(queryLetturaEsamiSvolti);
            ResultSet ers = ips.executeQuery();
        ) {
            while(ers.next())
                l.add(new Esame(ers.getInt("id"),
                              ers.getInt("codiceEsame"),
                              ers.getString("nome"),
                              ers.getInt("valutazione"),
                              ers.getInt("crediti"),
                              ers.getDate("data").toLocalDate(),
                              ers.getBoolean("caratterizzante"))); //rev2
        } catch(SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
    
    public void leggiEsamiDisponibili(ObservableList<Esame> l) {
        try (
            Connection connessioneDatabase = DriverManager.getConnection(URIConnessioneDB,utenteDB,passwordDB);                
            PreparedStatement ips = connessioneDatabase.prepareStatement(queryLetturaEsamiDisponibili);
            ResultSet ers = ips.executeQuery();
        ) {
            while(ers.next())
                l.add(new Esame(ers.getString("nome"),ers.getInt("crediti"),ers.getInt("codiceEsame"),ers.getBoolean("caratterizzante"))); //rev2
        } catch(SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
    
    public boolean modificaEsame(Esame e) {
        try (
            Connection connessioneDatabase = DriverManager.getConnection(URIConnessioneDB,utenteDB,passwordDB);                
            PreparedStatement eps = connessioneDatabase.prepareStatement(queryModificaEsame);
        ) {
            eps.setInt(1, e.getValutazione());
            eps.setDate(2,Date.valueOf(e.getData()));
            eps.setInt(3,e.getId());
            return eps.executeUpdate() > 0;
        } catch(SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return false;
        }
    }
    
    public boolean rimuoviEsame(int indice) {
        try (
            Connection connessioneDatabase = DriverManager.getConnection(URIConnessioneDB,utenteDB,passwordDB);                
            PreparedStatement rps = connessioneDatabase.prepareStatement(queryRimozioneEsame);
        ) {
            rps.setInt(1,indice);
            return rps.executeUpdate() > 0;
        } catch(SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return false;
        }
    }
}
