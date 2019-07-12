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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Bratva
 */
public class mainSceneWindowController implements Initializable {

    @FXML
    private Button btnEntrar;
    @FXML
    private PasswordField passFieldLogin;
    @FXML
    private TextField txtFieldLogin;

    /**
     * Initializes the controller class.
     */
    
    Funcionario funcionario;
    static Boolean gerente;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void bntEntrarOnAction(ActionEvent event) {
          try {
            if(loginVrified()){
                gerente = funcionario.getGerente();
                try {
                    BrSenaiScLanchoneteWilsinho.mudarTela("menu");
                } catch (IOException ex) {
                    Logger.getLogger(mainSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
                }
            }else{
                throw new SQLException("Senha Incorreta");
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
        
    }
    
    private Boolean loginVrified() throws SQLException{
            
            funcionario = DAOFactory.getFuncionarioDAO().getFuncionarioByLogin(txtFieldLogin.getText());
            
            if(funcionario != null){
                return funcionario.getSenha().equals(passFieldLogin.getText());
            }else{
                throw new SQLException("Funcionário não encontrado");
            }
            
        }
}
