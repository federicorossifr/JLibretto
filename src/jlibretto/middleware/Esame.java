////////////////////////////////////////////////////
package jlibretto.middleware;
import java.time.LocalDate;
import javafx.beans.property.*;

public class Esame {
    private int id;
    private int codiceEsame;
    private SimpleStringProperty nome;
    private SimpleIntegerProperty valutazione;
    private SimpleIntegerProperty crediti;
    private SimpleStringProperty data;
    //COSTRUTTORE PER ESAMI SVOLTI LETTI DA ARCHIVIAZIONE
    public Esame(int i,Integer ce,String n,Integer v,Integer c,LocalDate d) {
        id = i;
        codiceEsame = ce;
        nome = new SimpleStringProperty(n);
        valutazione = new SimpleIntegerProperty(v);
        crediti = new SimpleIntegerProperty(c);
        data = new SimpleStringProperty(d.toString());
    }    

    //COSTRUTTORE PER ESAMI DISPONIBILI LETTI DA ARCHIVIAZIONE
    public Esame(String n,Integer c,Integer ce) {
        nome = new SimpleStringProperty(n);
        crediti = new SimpleIntegerProperty(c);
        codiceEsame = ce;
    }
    
    //COSTRUTTORE PER ESAME VUOTO -> ULTIMA RIGA TABELLA
    public Esame() {
        this(-1,-1,"",18,0,LocalDate.now());
    }
    
    @Override
    public String toString() {
        return getNome();
    }
    
    public String getNome() {return nome.get();}
    public void setNome(String n) {nome.set(n);}
    public Integer getValutazione() {return valutazione.get();}
    public void setValutazione(Integer m) {valutazione.set(m);}
    public Integer getCrediti() {return crediti.get();}
    public void setCrediti(Integer c) {crediti.set(c);}    
    public LocalDate getData() {return LocalDate.parse(data.get());}
    public void setData(LocalDate d) {data.set(d.toString());}
    public int getId() {return id;}
    public void setId(int i) {id = i;}
    public int getCodiceEsame() {return codiceEsame;}
    public void setCodiceEsame(int ce) {codiceEsame = ce;}
}
