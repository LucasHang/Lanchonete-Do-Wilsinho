/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author Bratva
 */
public class DoWork extends Task<Integer>{
    @Override
    protected Integer call() throws Exception {
        for (int i = 0; i < 2; i++) {
            updateProgress(i + 1, 10);
            Thread.sleep(500);
            if (isCancelled()) {
                return i;
            }

        }
        return 10;
    }
    
    public static void createButtonEffectEvent(Button botao, String classe){
        botao.setOnMouseEntered(ev ->{
            botao.getStyleClass().add(classe);
        });
        botao.setOnMouseExited(ev ->{
            botao.getStyleClass().remove(classe);
        });
    }
    
    public static void createFieldEffectEvent(TextField field, String classe){
        field.setOnMouseEntered(ev ->{
            field.getStyleClass().add(classe);
        });
        field.setOnMouseExited(ev ->{
            field.getStyleClass().remove(classe);
        });
        
        field.focusedProperty().addListener((Obervable, oldValue, newValue) ->{
           if(oldValue){
                field.getStyleClass().remove(classe);
            }
            if(newValue){
                field.getStyleClass().add(classe);
            }
        });
        
        
    }
    
    public static void createComboEffectEvent(ComboBox combo, String classe){
        combo.setOnMouseEntered(ev ->{
            combo.getStyleClass().add(classe);
        });
        combo.setOnMouseExited(ev ->{
            combo.getStyleClass().remove(classe);
        });
        
        combo.focusedProperty().addListener((Obervable, oldValue, newValue) ->{
           if(oldValue){
                combo.getStyleClass().remove(classe);
            }
            if(newValue){
                combo.getStyleClass().add(classe);
            }
        });
        
        
    }
}
