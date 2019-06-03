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
        login = new Scene(fxmlLogin, 500, 507);
        
        Parent fxmlMenu = FXMLLoader.load(getClass().getResource("view/menuSceneWindow.fxml"));
        menu = new Scene(fxmlMenu, 700, 507);
        
        Parent fxmlCliente = FXMLLoader.load(getClass().getResource("view/clienteSceneWindow.fxml"));
        cliente = new Scene(fxmlCliente, 660, 900);
        
        
        
        Parent fxmlFuncionario = FXMLLoader.load(getClass().getResource("view/funcionarioSceneWindow.fxml"));
        funcionario = new Scene(fxmlFuncionario, 660, 900);
        
        Parent fxmlCadastroFuncionario = FXMLLoader.load(getClass().getResource("view/cadastroFuncionarioSceneWindow.fxml"));
        cadastroFuncionario = new Scene(fxmlCadastroFuncionario, 700, 507);
        
        Parent fxmlProduto = FXMLLoader.load(getClass().getResource("view/produtoSceneWindow.fxml"));
        produto = new Scene(fxmlProduto, 660, 900);
        
        Parent fxmlCadastroProduto = FXMLLoader.load(getClass().getResource("view/cadastroProdutoSceneWindow.fxml"));
        cadastroProduto = new Scene(fxmlCadastroProduto, 700, 507);
        
        Parent fxmlVenda = FXMLLoader.load(getClass().getResource("view/vendaSceneWindow.fxml"));
        venda = new Scene(fxmlVenda, 700, 607);
        
        stage.setScene(login);
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
