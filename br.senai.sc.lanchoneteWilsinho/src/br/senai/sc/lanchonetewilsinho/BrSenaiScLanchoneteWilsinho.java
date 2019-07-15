/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho;

import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Senai
 */
public class BrSenaiScLanchoneteWilsinho extends Application {
    
    private static Stage stage;
    
    private static Scene login;
    private static Scene menu;

       
     
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        stage = primaryStage;
        
        Parent fxmlLogin = FXMLLoader.load(getClass().getResource("view/mainSceneWindow.fxml"));
        login = new Scene(fxmlLogin, 450, 550);
        
        login.getStylesheets().add("/view/stylesClass.css");
        
        stage.setResizable(false);
        stage.setX(710);
        stage.setY(265);
        stage.setScene(login);
        stage.show();
        
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void mudarTela(String tela) throws IOException{
        switch(tela){
            case "menu" :
                Parent fxmlMenu = FXMLLoader.load(BrSenaiScLanchoneteWilsinho.class.getResource("view/menuSceneWindow.fxml"));
                menu = new Scene(fxmlMenu, 1280, 720);
                stage.setX(320);
                stage.setY(180);
                stage.setScene(menu);
                break;

        }
    }
    
    public static boolean stringContainsNumber( String s ){
        return Pattern.compile( "[0-9]" ).matcher( s ).find();
    }
 
    
    public static Integer dateToIntegerConverter(String data){
        
        SimpleDateFormat dataFormatAux = new SimpleDateFormat("dd/MM/yyyy");
        Date dataaux = new Date();
        try {
            dataaux = dataFormatAux.parse(data);
            
        } catch (ParseException ex) {
            Logger.getLogger(BrSenaiScLanchoneteWilsinho.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
        System.out.println(dataaux);
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy/MM/dd");
        List<String> anoMesDia = Arrays.asList(dataFormat.format(dataaux).split("/"));
        System.out.println(anoMesDia);
        String dataNumeric = new String();
        for (String value : anoMesDia) {
            dataNumeric = dataNumeric + value;
        }
        Integer x;
        x = parseInt(dataNumeric);
        
        return x;
    }
    
}
