/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.controller;

import br.senai.sc.lanchonetewilsinho.dao.DAOFactory;
import br.senai.sc.lanchonetewilsinho.model.Funcionario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

/**
 * FXML Controller class
 *
 * @author Bratva
 */
public class CadastroFuncionarioSceneWindowController implements Initializable {
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
    private CheckBox checkBoxGerente;
    @FXML
    private TextField txtFieldUsuario;
    @FXML
    private PasswordField passFieldSenha;

   Funcionario novoFuncionario= null;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          
	novoFuncionario = new Funcionario();
	txtFieldNome.textProperty().bindBidirectional(novoFuncionario.nomeProperty());
	txtFieldTelefoneContato.textProperty().bindBidirectional(novoFuncionario.TelefoneContatoProperty());
	txtFieldCpf.textProperty().bindBidirectional(novoFuncionario.CpfProperty(), new NumberStringConverter());
	txtFieldUsuario.textProperty().bindBidirectional(novoFuncionario.loginProperty());
	passFieldSenha.textProperty().bindBidirectional(novoFuncionario.senhaProperty());
	novoFuncionario.setGerente(checkBoxGerente.isSelected());
        
    }    

    @FXML
    private void btnCadastrarOnAction(ActionEvent event) {
        DAOFactory.getFuncionarioDAO().save(novoFuncionario);
        txtFieldNome.textProperty().unbindBidirectional(novoFuncionario.nomeProperty());
	txtFieldTelefoneContato.textProperty().unbindBidirectional(novoFuncionario.TelefoneContatoProperty());
	txtFieldCpf.textProperty().unbindBidirectional(novoFuncionario.CpfProperty());
	txtFieldUsuario.textProperty().unbindBidirectional(novoFuncionario.loginProperty());
	passFieldSenha.textProperty().unbindBidirectional(novoFuncionario.senhaProperty());
    }
    
}
