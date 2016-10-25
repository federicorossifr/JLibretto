/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlibretto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author feder
 */
public class RisorsaListaEsami  {
    private ObservableList<Esame> exams;
    private static RisorsaListaEsami instance;
    
    private RisorsaListaEsami() {
        exams = FXCollections.observableArrayList();
    }
    
    public static RisorsaListaEsami getInstance() {
        if(instance == null)
            instance = new RisorsaListaEsami();
        return instance;
    }
    
    public void addExam(Esame e) {
            exams.add(e);
    }
    
    public ObservableList<Esame> getExams() {
        return exams;
    }
    
    public Esame getExam(int index) {
        return exams.get(index);
    }
    
    public void notifyChangedExam(int index) {
        Esame toBeNotified = exams.get(index);
        exams.remove(index);
        exams.add(index,toBeNotified);
    }
}
