package jlibretto;

import java.time.LocalDate;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Exam {
    private SimpleStringProperty name;
    private SimpleIntegerProperty mark;
    private SimpleIntegerProperty credits;
    private SimpleStringProperty date;
    
    public Exam(String n,Integer m,Integer c,LocalDate d) {
        name = new SimpleStringProperty(n);
        mark = new SimpleIntegerProperty(m);
        credits = new SimpleIntegerProperty(c);
        date = new SimpleStringProperty(d.toString());
    }
    
    public Exam(String n,Integer m,Integer c) {
        this(n,m,c,LocalDate.now());
    }
    
    public String getName() {return name.get();}
    public void setName(String n) { name = new SimpleStringProperty(n); }
    public Integer getMark() {return mark.get();}
    public void setMark(Integer m) { mark = new SimpleIntegerProperty(m); }
    public Integer getCredits() {return credits.get();}
    public void setCredits(Integer c) { credits = new SimpleIntegerProperty(c); }    
    public String getDate() {return date.get();}
    public void setDate(String d) {
        LocalDate tmpDate = LocalDate.parse(d);
        date = new SimpleStringProperty(tmpDate.toString());
        
    }
}
