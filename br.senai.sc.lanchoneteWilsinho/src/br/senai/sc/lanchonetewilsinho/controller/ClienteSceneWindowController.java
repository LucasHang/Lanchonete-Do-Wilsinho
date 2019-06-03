/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.controller;


import br.senai.sc.lanchonetewilsinho.BrSenaiScLanchoneteWilsinho;
import br.senai.sc.lanchonetewilsinho.dao.DAOFactory;
import br.senai.sc.lanchonetewilsinho.model.Cliente;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Bratva
 */
public class ClienteSceneWindowController implements Initializable {
    @FXML
    private Button btnCadastrarCliente;
    @FXML
    private TableView<Cliente> tableClientes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    

    @FXML
    private void btnCadastrarClienteOnAction(ActionEvent event) throws IOException {
       
        BrSenaiScLanchoneteWilsinho.mudarTela("cadastroCliente");
        
    }
    
}
