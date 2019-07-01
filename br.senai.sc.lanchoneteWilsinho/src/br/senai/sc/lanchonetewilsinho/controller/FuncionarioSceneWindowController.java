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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

/**
 * FXML Controller class
 *
 * @author Bratva
 */
public class FuncionarioSceneWindowController implements Initializable {
    @FXML
    private Button btnCadastrarFuncionario;
    @FXML
    private TableView<Funcionario> tableFuncionarios;
    @FXML
    private TextField txtCarregar;
    @FXML
    private TableColumn<Funcionario, Boolean> tblColumnGerente;
    @FXML
    private Button btnCadastrar;
    @FXML
    private TextField txtFieldNome;
    @FXML
    private TextField txtFieldCpf;
    @FXML
    private TextField txtFieldTelefoneContato;
    @FXML
    private CheckBox checkBoxGerente;
    @FXML
    private TextField txtFieldUsuario;
    @FXML
    private PasswordField passFieldSenha;

    /**
     * Initializes the controller class.
     */
    
    Funcionario novoFuncionario= null;
    Funcionario funcionarioSelecionado = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCarregarOnAction(null);
        mascaraTabela();
      
      tableFuncionarios.getSelectionModel().selectedItemProperty().addListener((observable,newValue,oldValue)->{
            unbindFields(oldValue);
            bindFields(newValue);
            funcionarioSelecionado = newValue;
        });
    }    

    @FXML
    private void btnCadastrarFuncionarioOnAction(ActionEvent event) throws IOException {
        disableFields(false);
        novoFuncionario = new Funcionario();
	bindFields(novoFuncionario);
        
    }

    @FXML
    private void btnCarregarOnAction(ActionEvent event) {
        try {
            tableFuncionarios.setItems(FXCollections.observableArrayList(DAOFactory.getFuncionarioDAO().getAll()));
        } catch (SQLException ex) {
            Logger.getLogger(ClienteSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage());
        }
    }
    
    @FXML
    private void btnCadastrarOnAction(ActionEvent event) {
        
        unbindFields(novoFuncionario);
        unbindFields(funcionarioSelecionado);
        
        try {
            if(novoFuncionario != null){
                DAOFactory.getFuncionarioDAO().save(novoFuncionario);
            }else{
                if(funcionarioSelecionado != null){
                    DAOFactory.getFuncionarioDAO().update(funcionarioSelecionado);
                }
            }
            clearFields();
            disableFields(true);
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
        
        
    }
    
    private void mascaraTabela(){
        tblColumnGerente.setCellFactory((TableColumn<Funcionario, Boolean> param) -> {
            TableCell cell = new TableCell<Funcionario, Boolean>() {

                @Override
                public void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(null);
                    setGraphic(null);
                    if (!empty) {
                        if (item == null) {
                            setText("");
                        } else {
                            if(item){
                              setText("Gerente");  
                            }else{
                                setText("Vendedor");
                            }
                            
                        }

                    }
                }

                @Override
                public void updateSelected(boolean upd) {
                    super.updateSelected(upd);
                }

                private String getString() {
                    return getItem() == null ? "" : getItem().toString();
                }
            };
            return cell;
        });
    }

    private void disableFields(Boolean value){
        txtFieldCpf.setDisable(value);
        txtFieldNome.setDisable(value);
        txtFieldTelefoneContato.setDisable(value);
        txtFieldUsuario.setDisable(value);
        passFieldSenha.setDisable(value);
        checkBoxGerente.setDisable(value);
    }
    
    private void bindFields(Funcionario funcionario){
        if(funcionario != null){
            txtFieldNome.textProperty().bindBidirectional(funcionario.nomeProperty());
            txtFieldTelefoneContato.textProperty().bindBidirectional(funcionario.telefoneContatoProperty());
            txtFieldCpf.textProperty().bindBidirectional(funcionario.CpfProperty());
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
    
    public Boolean validationForm(){
        Boolean invalido = false;

        if(txtFieldCpf.textProperty().isNull().get()){
            txtFieldCpf.getStyleClass().add("invalido");
            invalido = true;     
        }else{
            txtFieldCpf.getStyleClass().remove("invalido");
        }
        
        if(txtFieldNome.textProperty().isNull().get()){
            txtFieldNome.getStyleClass().add("invalido");
            invalido = true;
        }else{
            txtFieldNome.getStyleClass().remove("invalido");
        }
        
        if(txtFieldTelefoneContato.textProperty().isNull().get()){
            txtFieldTelefoneContato.getStyleClass().add("invalido");
            invalido = true;
        }else{
            txtFieldTelefoneContato.getStyleClass().remove("invalido");
        }
        
        if(txtFieldUsuario.textProperty().isNull().get()){
            txtFieldUsuario.getStyleClass().add("invalido");
            invalido = true;
        }else{
            txtFieldUsuario.getStyleClass().remove("invalido");
        }
        
        if(passFieldSenha.textProperty().isNull().get()){
            passFieldSenha.getStyleClass().add("invalido");
            invalido = true;
        }else{
            passFieldSenha.getStyleClass().remove("invalido");
        }
       
        return invalido;
    }
    
    private void clearFields(){
        txtFieldCpf.clear();
        txtFieldNome.clear();
        txtFieldTelefoneContato.clear();
        txtFieldUsuario.clear();
        passFieldSenha.clear();
        checkBoxGerente.setSelected(false);
    }
}
