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
    private static Stage stageDos;
    private static Stage stageTres;
    
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
        
        stageDos = new Stage();
        stageTres = new Stage();
        
        Parent fxmlLogin = FXMLLoader.load(getClass().getResource("view/mainSceneWindow.fxml"));
        login = new Scene(fxmlLogin, 500, 550);
        
        
        
      //  Parent fxmlCliente = FXMLLoader.load(getClass().getResource("view/clienteSceneWindow.fxml"));
      //  cliente = new Scene(fxmlCliente, 660, 900);

        Parent fxmlFuncionario = FXMLLoader.load(getClass().getResource("view/funcionarioSceneWindow.fxml"));
        funcionario = new Scene(fxmlFuncionario, 660, 900);
        
        Parent fxmlCadastroFuncionario = FXMLLoader.load(getClass().getResource("view/cadastroFuncionarioSceneWindow.fxml"));
        cadastroFuncionario = new Scene(fxmlCadastroFuncionario, 700, 507);
        
        Parent fxmlProduto = FXMLLoader.load(getClass().getResource("view/produtoSceneWindow.fxml"));
        produto = new Scene(fxmlProduto, 660, 900);
        
        Parent fxmlCadastroProduto = FXMLLoader.load(getClass().getResource("view/cadastroProdutoSceneWindow.fxml"));
        cadastroProduto = new Scene(fxmlCadastroProduto, 700, 507);
        
        Parent fxmlVenda = FXMLLoader.load(getClass().getResource("view/vendaSceneWindow.fxml"));
        venda = new Scene(fxmlVenda, 1280, 720);
        
        Parent fxmlCadastroCliente = FXMLLoader.load(BrSenaiScLanchoneteWilsinho.class.getResource("view/cadastroClienteSceneWindow.fxml"));
        cadastroCliente = new Scene(fxmlCadastroCliente, 1280, 720);
        
        stageTres.initStyle(StageStyle.UNDECORATED);
        stageTres.setResizable(false);
        
        stage.initStyle(StageStyle.UNDECORATED);
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
                menu = new Scene(fxmlMenu, 1280, 350);
                stage.setX(320);
                stage.setY(0.0);
                stage.setAlwaysOnTop(true);
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
                stageDos.setScene(cadastroCliente);
                stageDos.show();
                break;
            case "produto":
                Parent fxmlProduto = FXMLLoader.load(BrSenaiScLanchoneteWilsinho.class.getResource("view/produtoSceneWindow.fxml"));
                produto = new Scene(fxmlProduto, 700, 807);
                stageTres.setScene(produto);
                break;
            case "cadastroProduto" :
                Parent fxmlCadastroProduto = FXMLLoader.load(BrSenaiScLanchoneteWilsinho.class.getResource("view/cadastroProdutoSceneWindow.fxml"));
                cadastroProduto = new Scene(fxmlCadastroProduto, 700, 507);
                stageDos.setScene(cadastroProduto);
                break;
            case "funcionario":
                Parent fxmlFuncionario = FXMLLoader.load(BrSenaiScLanchoneteWilsinho.class.getResource("view/funcionarioSceneWindow.fxml"));
                funcionario = new Scene(fxmlFuncionario, 680, 720);
                stageTres.setX(620);
                stageTres.setY(350);
                stageTres.setScene(funcionario);
                stageTres.show();
            break;
            case "cadastroFuncionario":
                Parent fxmlCadastroFuncionario = FXMLLoader.load(BrSenaiScLanchoneteWilsinho.class.getResource("view/cadastroFuncionarioSceneWindow.fxml"));
                cadastroFuncionario = new Scene(fxmlCadastroFuncionario, 700, 507);
                stageDos.setScene(cadastroFuncionario);
                break;
            case "venda":
                Parent fxmlVenda = FXMLLoader.load(BrSenaiScLanchoneteWilsinho.class.getResource("view/vendaSceneWindow.fxml"));
                venda = new Scene(fxmlVenda, 700, 807);
                stageTres.setScene(venda);
                break;
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
