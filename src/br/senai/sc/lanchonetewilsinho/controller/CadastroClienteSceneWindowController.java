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
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

/**
 * FXML Controller class
 *
 * @author Bratva
 */
public class CadastroClienteSceneWindowController implements Initializable {
    @FXML
    private Button btnCadastrar;
    @FXML
    private TextField txtFieldNome;
    @FXML
    private TextField txtFieldCpf;
    @FXML
    private TextField txtFieldTelefoneContato;
    @FXML
    private TextField txtFieldDataNascimento;
    @FXML
    private CheckBox checkBoxColaborador;

    Cliente novoCliente = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        novoCliente = new Cliente();
        txtFieldNome.textProperty().bindBidirectional(novoCliente.nomeProperty());
	txtFieldTelefoneContato.textProperty().bindBidirectional(novoCliente.TelefoneContatoProperty());
	txtFieldCpf.textProperty().bindBidirectional(novoCliente.CpfProperty(), new NumberStringConverter());
	novoCliente.setColaborador(checkBoxColaborador.isSelected());
    }    

    @FXML
    private void btnCadastrarOnAction(ActionEvent event) {
        try {
            DAOFactory.getClienteDAO().save(novoCliente);
        } catch (SQLException ex) {
            Logger.getLogger(CadastroClienteSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alerta = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
        }
        txtFieldNome.textProperty().unbindBidirectional(novoCliente.nomeProperty());
	txtFieldTelefoneContato.textProperty().unbindBidirectional(novoCliente.TelefoneContatoProperty());
	txtFieldCpf.textProperty().unbindBidirectional(novoCliente.CpfProperty());
     
        try {
            BrSenaiScLanchoneteWilsinho.mudarTela("cliente");
        } catch (IOException ex) {
            Logger.getLogger(CadastroClienteSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alerta = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
        }

    }
    
}
