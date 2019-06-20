/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Senai
 */
public class MeuAlerta {
    
    public static Alert alerta = null;
    
    public static Alert alertaErro(String message){
        alerta = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        return alerta;
    }
    
    public static Alert alertaDeConfirmacao(String message){
        alerta = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES);
        alerta.getButtonTypes().add(ButtonType.NO);
        return alerta;
    }
}
