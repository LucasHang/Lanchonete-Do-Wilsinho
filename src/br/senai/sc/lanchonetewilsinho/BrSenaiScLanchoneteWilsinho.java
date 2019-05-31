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

/**
 *
 * @author Senai
 */
public class BrSenaiScLanchoneteWilsinho extends Application {
    
    private static Stage stage;
    
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
        
        Parent root = FXMLLoader.load(getClass().getResource("mainSceneWindow.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
    }
    public static void mudarTela(String tela) throws IOException{
        switch(tela){
            case "menu" :
                stage.setScene(menu);
                break;
            case "cliente":
                Parent fxmlCliente = FXMLLoader.load(BrSenaiScLanchoneteWilsinho.class.getResource("view/clienteSceneWindow.fxml"));
                cliente = new Scene(fxmlCliente, 700, 807);
                stage.setScene(cliente);
                stage.show();
                break;
            case "cadastroCliente" :
                Parent fxmlCadastroCliente = FXMLLoader.load(BrSenaiScLanchoneteWilsinho.class.getResource("view/cadastroClienteSceneWindow.fxml"));
                cadastroCliente = new Scene(fxmlCadastroCliente, 700, 507);
                stage.setScene(cadastroCliente);
                stage.show();
                break;
            case "produto":
                stage.setScene(produto);
                break;
            case "cadastroProduto" :
                stage.setScene(cadastroProduto);
                break;
            case "funcionario":
                stage.setScene(funcionario);
                break;
            case "cadastroFuncionario":
                stage.setScene(cadastroFuncionario);
                break;
            case "venda":
                stage.setScene(venda);
                break;
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
