/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlibretto;

import javafx.scene.input.MouseEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author feder
 */
public class SettingsForm extends VBox {
    TextField userInput = new TextField();
    TextField departementInput = new TextField();
    RadioButton arithmeticAvgMode = new RadioButton("Arithmetic");
    RadioButton weightedAvgMode = new RadioButton("Weighted");
    
    public SettingsForm() {
        Label userLabel = new Label("User");
        Font bolder = Font.font(Font.getDefault().getFamily(),FontWeight.BOLD,Font.getDefault().getSize());
        userLabel.setFont(bolder);
        Label departementLabel = new Label("Departement");
        departementLabel.setFont(bolder);
        Label averageLabel = new Label("Average mode");
        averageLabel.setFont(bolder);
        ToggleGroup avgMode = new ToggleGroup();
        Button saveButton = new Button("Save");
        arithmeticAvgMode.setToggleGroup(avgMode);
        weightedAvgMode.setToggleGroup(avgMode);
        getChildren().addAll(userLabel,userInput,departementLabel,departementInput,averageLabel,arithmeticAvgMode,weightedAvgMode,saveButton);
        setPadding(new Insets(5,10,5,10));
        setAlignment(Pos.TOP_CENTER);
        setSpacing(5);
        saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> applySettings());
    }
    
    public void applySettings() {
        
    }
    
}