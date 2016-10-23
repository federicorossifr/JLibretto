/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librettoiface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author feder
 */
public class ExamObservableList  {
    private ObservableList<Exam> exams;
    private static ExamObservableList instance;
    
    private ExamObservableList() {
        exams = FXCollections.observableArrayList();
    }
    
    public static ExamObservableList getInstance() {
        if(instance == null)
            instance = new ExamObservableList();
        return instance;
    }
    
    public void addExam(Exam e) {
            exams.add(e);
    }
    
    public ObservableList<Exam> getExams() {
        return exams;
    }
    
    public Exam getExam(int index) {
        return exams.get(index);
    }
    
    public void notifyChangedExam(int index) {
        Exam toBeNotified = exams.get(index);
        exams.remove(index);
        exams.add(index,toBeNotified);
    }
}
