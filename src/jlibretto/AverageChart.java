package jlibretto;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

class AverageChart extends LineChart {
    
    public AverageChart(NumberAxis n) {
        super(new CategoryAxis(),n);
        setLegendVisible(false);
        setTitle("Average Plot");
        ExamObservableList.getInstance().getExams().addListener((ListChangeListener.Change<? extends Exam> c) -> {
            Double avg = update((ObservableList<Exam>) c.getList());
            setTitle("Current average "+avg);
            
        });
        setAnimated(false);
    }
    
    public double update(ObservableList<Exam> exams) {
        Double sum = 0.0;
        Integer count = 0;
        Double tmp = 0.0;
        Series<String,Double> mobileAvg;        
        mobileAvg = new Series<>();
        for(Exam e:exams) {
            sum+=e.getMark();
            count++;
            tmp = sum/count;
            mobileAvg.getData().add(new XYChart.Data(e.getName(),tmp));
        }
        setData(FXCollections.observableArrayList(mobileAvg));
        return tmp;
    }
 }
