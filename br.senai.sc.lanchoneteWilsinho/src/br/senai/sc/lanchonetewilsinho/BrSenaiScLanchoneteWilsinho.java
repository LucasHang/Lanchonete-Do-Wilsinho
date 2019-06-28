/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho;

import java.io.IOException;
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
    private static Scene cliente;
    private static Scene cadastroCliente;
    private static Scene funcionario;
    private static Scene cadastroFuncionario;
    private static Scene produto;
    private static Scene cadastroProduto;
    private static Scene venda;
       
     
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        stage = primaryStage;
        
        Parent fxmlLogin = FXMLLoader.load(getClass().getResource("view/mainSceneWindow.fxml"));
        login = new Scene(fxmlLogin, 500, 550);
        
 
        stage.setResizable(false);
        stage.setX(710);
        stage.setY(265);
        stage.setScene(login);
        stage.show();
        
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
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
