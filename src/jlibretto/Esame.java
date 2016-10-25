package jlibretto;

import java.time.LocalDate;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Esame {
    private int id;
    private String codiceUtente;
    private SimpleStringProperty nome;
    private SimpleIntegerProperty valutazione;
    private SimpleIntegerProperty crediti;
    private SimpleStringProperty data;
    public static final ObservableList<Integer> listaVotiStandard;
    static {
        listaVotiStandard = FXCollections.observableArrayList();
        for(int mm = 18; mm<=33; ++mm)
            listaVotiStandard.add(mm);
    }
    
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
        LocalDate tmpDate = LocalDate.parse(d);
        data = new SimpleStringProperty(tmpDate.toString());
    }
    public int getId() {return id;}
    public void setId(int i) {id = i;}
    public String getCodiceUtente() {return codiceUtente;}
}
