/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.controller;

import br.senai.sc.lanchonetewilsinho.BrSenaiScLanchoneteWilsinho;
import br.senai.sc.lanchonetewilsinho.MeuAlerta;
import br.senai.sc.lanchonetewilsinho.dao.DAOFactory;
import br.senai.sc.lanchonetewilsinho.model.Funcionario;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
	bindFields(novoFuncionario);
        
    }    

    @FXML
    private void btnCadastrarOnAction(ActionEvent event) {
        unbindFields(novoFuncionario);
        
        try {
            DAOFactory.getFuncionarioDAO().save(novoFuncionario);
        } catch (SQLException ex) {
            Logger.getLogger(CadastroFuncionarioSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
        
        try {
            BrSenaiScLanchoneteWilsinho.mudarTela("funcionario");
        } catch (IOException ex) {
            Logger.getLogger(CadastroFuncionarioSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
        
    }
    
    
    private void bindFields(Funcionario funcionario){
        if(funcionario != null){
            txtFieldNome.textProperty().bindBidirectional(funcionario.nomeProperty());
            txtFieldTelefoneContato.textProperty().bindBidirectional(funcionario.telefoneContatoProperty());
            txtFieldCpf.textProperty().bindBidirectional(funcionario.CpfProperty(), new NumberStringConverter());
            checkBoxGerente.selectedProperty().bindBidirectional(funcionario.gerenteProperty());
        }
        
    }
    
    private void unbindFields(Funcionario funcionario){
        if(funcionario != null){
            txtFieldNome.textProperty().unbindBidirectional(funcionario.nomeProperty());
            txtFieldTelefoneContato.textProperty().unbindBidirectional(funcionario.telefoneContatoProperty());
            txtFieldCpf.textProperty().unbindBidirectional(funcionario.CpfProperty());
            checkBoxGerente.selectedProperty().unbindBidirectional(funcionario.gerenteProperty());
        }
    }
    
}
