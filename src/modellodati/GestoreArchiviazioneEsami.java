package modellodati;

import configurazione.*;
import java.sql.*;
import java.time.LocalDate;
import javafx.application.Platform;
import javafx.collections.ObservableList;

class GestoreArchiviazioneEsami{
    private Connection connessioneDatabase;
    private static GestoreArchiviazioneEsami _istanza;
    private final String queryInserimentoEsame = "INSERT INTO esame(nome,crediti,valutazione,data,codiceUtente) VALUES(?,?,?,?,?)";
    private final String queryModificaEsame = "UPDATE esame SET nome = ?,crediti=?,valutazione=?,data=? WHERE id = ?";
    private final String queryLetturaEsami = "SELECT * FROM esame WHERE codiceUtente=?";
    private final String queryRimozioneEsame = "DELETE FROM esame WHERE id = ?";
    
    private GestoreArchiviazioneEsami() {
        int porta;
        String hostname;
        String utenteDatabase;
        String passwdDatabase;
        String nomeDatabase = "prg";
        try {
            porta = GestoreConfigurazioniXML.ottieni().Nucleo.PortaDatabase;
            hostname = GestoreConfigurazioniXML.ottieni().Nucleo.HostnameDatabase;
            utenteDatabase = GestoreConfigurazioniXML.ottieni().Nucleo.UtenteDatabase;
            passwdDatabase = GestoreConfigurazioniXML.ottieni().Nucleo.PasswordDatabase;       
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
            System.out.println("Impossibile inserire l\'esame: "+ex.getLocalizedMessage());
            return -1;
        }        
    }
    
    public void leggiEsami(String codiceUtente,ObservableList<Esame> l) {
        try (
            PreparedStatement ips = connessioneDatabase.prepareStatement(queryLetturaEsami);
        ) {
            ips.setString(1, codiceUtente);
            ResultSet ers = ips.executeQuery();
            Esame e;
            while(ers.next()) {
                e = new Esame(ers.getInt("id"),
                              ers.getString("nome"),
                              ers.getInt("valutazione"),
                              ers.getInt("crediti"),
                              ers.getDate("data").toLocalDate(),
                              ers.getString("codiceUtente"));
                System.out.println(e.getNome());
                l.add(e);
            }
        } catch(SQLException ex) {
            System.out.println("Impossibile recuperare gli esami: "+ex.getLocalizedMessage());
        }
    }
    
    public boolean modificaEsame(Esame e) {
        try (
            PreparedStatement eps = connessioneDatabase.prepareStatement(queryModificaEsame);
        ) {
            eps.setString(1, e.getNome());
            eps.setInt(2, e.getCrediti());
            eps.setInt(3, e.getValutazione());
            eps.setDate(4,Date.valueOf(LocalDate.parse(e.getData())));
            eps.setInt(5,e.getId());
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
