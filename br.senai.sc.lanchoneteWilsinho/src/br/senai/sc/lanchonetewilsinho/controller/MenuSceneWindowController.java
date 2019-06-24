/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.controller;

import br.senai.sc.lanchonetewilsinho.BrSenaiScLanchoneteWilsinho;
import br.senai.sc.lanchonetewilsinho.MeuAlerta;
import static br.senai.sc.lanchonetewilsinho.MeuAlerta.alerta;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Bratva
 */
public class MenuSceneWindowController implements Initializable {
    @FXML
    private Button btnFuncionarios;
    @FXML
    private Button btnCliente;
    @FXML
    private Button btnVenda;
    @FXML
    private Button btnProdutos;
    @FXML
    private TabPane tabPanePrincipal;
    
    Tab abaUnica;
    AnchorPane anchorUnico;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
           
                FXMLLoader cargaDoScene =
                       new FXMLLoader(getClass().getResource("/br/senai/sc/lanchonetewilsinho/view/vendaSceneWindow.fxml"));
            
            abaUnica = new Tab("Vendas");
            
            anchorUnico = cargaDoScene.load();

            abaUnica.setContent(anchorUnico);
            abaUnica.setOnCloseRequest((eventClose) -> {
                if (MeuAlerta.alertaDeConfirmacao("Deseja realmente fechar a janela?").showAndWait().get() != ButtonType.YES){
                    eventClose.consume();
                }else{
                    tabPanePrincipal.getTabs().remove(abaUnica);
                    anchorUnico = null;
                }
            });
            if(!tabPanePrincipal.getTabs().contains(abaUnica)){
            tabPanePrincipal.getTabs().add(abaUnica);
            }
            tabPanePrincipal.getSelectionModel().select(abaUnica);
        } catch (IOException ex) {
            Logger.getLogger(MenuSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
    }    

    @FXML
    private void btnFuncionariosOnAction(ActionEvent event) throws IOException {
        
        if(abaUnica != null){
             abaUnica = null;
        }
        
        if(anchorUnico != null){
            anchorUnico = null;
        }
        
        try {
           
                FXMLLoader cargaDoScene =
                       new FXMLLoader(getClass().getResource("/br/senai/sc/lanchonetewilsinho/view/funcionarioSceneWindow.fxml"));
            
            abaUnica = new Tab("Funcionarios");
            
            anchorUnico = cargaDoScene.load();

            abaUnica.setContent(anchorUnico);
            abaUnica.setOnCloseRequest((eventClose) -> {
                if (MeuAlerta.alertaDeConfirmacao("Deseja realmente fechar a janela?").showAndWait().get() != ButtonType.YES){
                    eventClose.consume();
                }else{
                    tabPanePrincipal.getTabs().remove(abaUnica);
                    anchorUnico = null;
                }
            });
            if(!tabPanePrincipal.getTabs().contains(abaUnica)){
            tabPanePrincipal.getTabs().add(abaUnica);
            }
            tabPanePrincipal.getSelectionModel().select(abaUnica);
        } catch (IOException ex) {
            Logger.getLogger(MenuSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
    }

    @FXML
    private void btnClienteOnAction(ActionEvent event) throws IOException {
        if(abaUnica != null){
             abaUnica = null;
        }
        
        if(anchorUnico != null){
            anchorUnico = null;
        }
        
        try {
           
                FXMLLoader cargaDoScene =
                       new FXMLLoader(getClass().getResource("/br/senai/sc/lanchonetewilsinho/view/clienteSceneWindow.fxml"));
            
            abaUnica = new Tab("Clientes");
            
            anchorUnico = cargaDoScene.load();

            abaUnica.setContent(anchorUnico);
            abaUnica.setOnCloseRequest((eventClose) -> {
                if (MeuAlerta.alertaDeConfirmacao("Deseja realmente fechar a janela?").showAndWait().get() != ButtonType.YES){
                    eventClose.consume();
                }else{
                    tabPanePrincipal.getTabs().remove(abaUnica);
                    anchorUnico = null;
                }
            });
            if(!tabPanePrincipal.getTabs().contains(abaUnica)){
            tabPanePrincipal.getTabs().add(abaUnica);
            }
            tabPanePrincipal.getSelectionModel().select(abaUnica);
        } catch (IOException ex) {
            Logger.getLogger(MenuSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
    }

    @FXML
    private void btnVendaOnAction(ActionEvent event) throws IOException {
        if(abaUnica != null){
             abaUnica = null;
        }
        
        if(anchorUnico != null){
            anchorUnico = null;
        }
        
        try {
           
                FXMLLoader cargaDoScene =
                       new FXMLLoader(getClass().getResource("/br/senai/sc/lanchonetewilsinho/view/vendaSceneWindow.fxml"));
            
            abaUnica = new Tab("Vendas");
            
            anchorUnico = cargaDoScene.load();

            abaUnica.setContent(anchorUnico);
            abaUnica.setOnCloseRequest((eventClose) -> {
                if (MeuAlerta.alertaDeConfirmacao("Deseja realmente fechar a janela?").showAndWait().get() != ButtonType.YES){
                    eventClose.consume();
                }else{
                    tabPanePrincipal.getTabs().remove(abaUnica);
                    anchorUnico = null;
                }
            });
            if(!tabPanePrincipal.getTabs().contains(abaUnica)){
            tabPanePrincipal.getTabs().add(abaUnica);
            }
            tabPanePrincipal.getSelectionModel().select(abaUnica);
        } catch (IOException ex) {
            Logger.getLogger(MenuSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
    }

    @FXML
    private void btnProdutosOnAction(ActionEvent event) throws IOException {
       
        if(abaUnica != null){
             abaUnica = null;
        }
        
        if(anchorUnico != null){
            anchorUnico = null;
        }
        
        try {
           
                FXMLLoader cargaDoScene =
                       new FXMLLoader(getClass().getResource("/br/senai/sc/lanchonetewilsinho/view/produtoSceneWindow.fxml"));
            
            abaUnica = new Tab("Produtos");
            
            anchorUnico = cargaDoScene.load();

            abaUnica.setContent(anchorUnico);
            abaUnica.setOnCloseRequest((eventClose) -> {
                if (MeuAlerta.alertaDeConfirmacao("Deseja realmente fechar a janela?").showAndWait().get() != ButtonType.YES){
                    eventClose.consume();
                }else{
                    tabPanePrincipal.getTabs().remove(abaUnica);
                    anchorUnico = null;
                }
            });
            if(!tabPanePrincipal.getTabs().contains(abaUnica)){
            tabPanePrincipal.getTabs().add(abaUnica);
            }
            tabPanePrincipal.getSelectionModel().select(abaUnica);
        } catch (IOException ex) {
            Logger.getLogger(MenuSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
        
    }
    
}
