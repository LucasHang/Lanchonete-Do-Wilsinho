/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.controller;

import br.senai.sc.lanchonetewilsinho.BrSenaiScLanchoneteWilsinho;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnFuncionariosOnAction(ActionEvent event) throws IOException {
        BrSenaiScLanchoneteWilsinho.mudarTela("funcionario");
    }

    @FXML
    private void btnClienteOnAction(ActionEvent event) throws IOException {
        BrSenaiScLanchoneteWilsinho.mudarTela("cliente");
    }

    @FXML
    private void btnVendaOnAction(ActionEvent event) throws IOException {
        BrSenaiScLanchoneteWilsinho.mudarTela("venda");
    }

    @FXML
    private void btnProdutosOnAction(ActionEvent event) throws IOException {
        BrSenaiScLanchoneteWilsinho.mudarTela("produto");
    }
    
}
