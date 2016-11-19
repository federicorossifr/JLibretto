package modellodati;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javafx.beans.property.*;

public class Esame {
    private int id;
    private String codiceUtente;
    private SimpleStringProperty nome;
    private SimpleIntegerProperty valutazione;
    private SimpleIntegerProperty crediti;
    private SimpleStringProperty data;

    public Esame(int i,String n,Integer m,Integer c,LocalDate d,String cu) {
        id = i;
        nome = new SimpleStringProperty(n);
        valutazione = new SimpleIntegerProperty(m);
        crediti = new SimpleIntegerProperty(c);
        data = new SimpleStringProperty(d.toString());
        codiceUtente = cu;
    }
    
    public Esame(String n,Integer m,Integer c,LocalDate d,String cu) {
        this(-1,n,m,c,d,cu);
    }
    
    public String getNome() {return nome.get();}
    public void setNome(String n) { nome = new SimpleStringProperty(n); }
    public Integer getValutazione() {return valutazione.get();}
    public void setValutazione(Integer m) { valutazione = new SimpleIntegerProperty(m); }
    public Integer getCrediti() {return crediti.get();}
    public void setCrediti(Integer c) { crediti = new SimpleIntegerProperty(c); }    
    public String getData() {return data.get();}
    public void setData(String d) {
        String vecchiaData = getData();
        try {
            LocalDate tmpDate = LocalDate.parse(d);
            data = new SimpleStringProperty(tmpDate.toString());
        } catch(DateTimeParseException e) {
            data = new SimpleStringProperty(vecchiaData);
        }
    }
    public int getId() {return id;}
    public void setId(int i) {id = i;}
    public String getCodiceUtente() {return codiceUtente;}
}
