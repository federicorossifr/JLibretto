package modellodati;

import configurazione.*;
import java.sql.*;
import javafx.application.*;
import javafx.collections.*;

class GestoreArchiviazioneEsami{
    private Connection connessioneDatabase;
    private static GestoreArchiviazioneEsami _istanza;
    private final String queryInserimentoEsame = "INSERT INTO esame(codiceEsame,valutazione,data) VALUES(?,?,?)";
    private final String queryModificaEsame = "UPDATE esame SET valutazione=?,data=? WHERE id = ?";
    private final String queryLetturaEsamiSvolti = "SELECT * FROM esame NATURAL JOIN esami;";
    private final String queryLetturaEsamiDisponibili = "SELECT * FROM esami";
    private final String queryRimozioneEsame = "DELETE FROM esame WHERE id = ?";
    
    private GestoreArchiviazioneEsami() {
        int porta;
        String hostname;
        String utenteDatabase;
        String passwdDatabase;
        String nomeDatabase = "prg";
        try {
            porta = GestoreConfigurazioniXML.ottieni().PortaDatabase;
            hostname = GestoreConfigurazioniXML.ottieni().HostnameDatabase;
            utenteDatabase = GestoreConfigurazioniXML.ottieni().UtenteDatabase;
            passwdDatabase = GestoreConfigurazioniXML.ottieni().PasswordDatabase;       
            String URI = "jdbc:mysql://"+hostname+":"+porta+"/"+nomeDatabase;
            connessioneDatabase = DriverManager.getConnection(URI,utenteDatabase,passwdDatabase);
        } catch(Exception e) {
            System.out.println("Impossibile connettersi all'archivio esami: "+e.getLocalizedMessage());
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
        try(
            PreparedStatement ips = connessioneDatabase.prepareStatement(queryInserimentoEsame,Statement.RETURN_GENERATED_KEYS);
        ) {
            ips.setInt(1, e.getCodiceEsame());
            ips.setInt(2,e.getValutazione());
            ips.setDate(3,Date.valueOf(e.getData()));
            ips.executeUpdate();
            ResultSet idResult = ips.getGeneratedKeys();
            int id = -1;
            if(idResult.next()) {
                id = idResult.getInt(1);
            }
            return id;
        } catch (SQLException ex) {
            System.out.println("Impossibile inserire l\'esame: "+ex.getLocalizedMessage());
            return -1;
        }        
    }
    
    public void leggiEsamiSvolti(ObservableList<Esame> l) {
        try (
            PreparedStatement ips = connessioneDatabase.prepareStatement(queryLetturaEsamiSvolti);
        ) {
            ResultSet ers = ips.executeQuery();
            Esame e;
            while(ers.next()) {
                e = new Esame(ers.getInt("id"),
                              ers.getInt("codiceEsame"),
                              ers.getString("nome"),
                              ers.getInt("valutazione"),
                              ers.getInt("crediti"),
                              ers.getDate("data").toLocalDate());
                System.out.println(e.getNome());
                l.add(e);
            }
        } catch(SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
    
    public void leggiEsamiDisponibili(ObservableList<Esame> l) {
        try (
            PreparedStatement ips = connessioneDatabase.prepareStatement(queryLetturaEsamiDisponibili);
        ) {
            ResultSet ers = ips.executeQuery();
            Esame e;
            while(ers.next()) {
                e = new Esame(ers.getString("nome"),
                              ers.getInt("crediti"),
                              ers.getInt("codiceEsame"));
                l.add(e);
            }
        } catch(SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
    
    public boolean modificaEsame(Esame e) {
        try (
            PreparedStatement eps = connessioneDatabase.prepareStatement(queryModificaEsame);
        ) {
            eps.setInt(1, e.getValutazione());
            eps.setDate(2,Date.valueOf(e.getData()));
            eps.setInt(3,e.getId());
            int affectedRows = eps.executeUpdate();
            return affectedRows > 0;
        } catch(SQLException ex) {
            System.out.println("Impossibile modificare l\'esame: "+ex.getLocalizedMessage());
            return false;
        }
    }
    
    public boolean rimuoviEsame(int indice) {
        try (
            PreparedStatement rps = connessioneDatabase.prepareStatement(queryRimozioneEsame);
        ) {
            rps.setInt(1,indice);
            int righeRimosse = rps.executeUpdate();
            return righeRimosse > 0;
        } catch(SQLException ex) {
            System.out.println("Impossibile rimuovere l\'esame: "+ex.getLocalizedMessage());
            return false;
        }
    }
}
