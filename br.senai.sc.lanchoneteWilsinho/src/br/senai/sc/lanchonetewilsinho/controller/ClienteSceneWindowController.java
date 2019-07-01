/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.controller;


import br.senai.sc.lanchonetewilsinho.MeuAlerta;
import br.senai.sc.lanchonetewilsinho.BrSenaiScLanchoneteWilsinho;
import br.senai.sc.lanchonetewilsinho.MeuAlerta;
import br.senai.sc.lanchonetewilsinho.dao.DAOFactory;
import br.senai.sc.lanchonetewilsinho.model.Cliente;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
public class ClienteSceneWindowController implements Initializable {
    @FXML
    private Button btnCadastrarCliente;
    @FXML
    private TableView<Cliente> tableClientes;
    @FXML
    private TextField txtCarregar;
    @FXML
    private TableColumn<Cliente,Boolean> tblColumnColaborador;
    @FXML
    private Button btnCadastrar;
    @FXML
    private TextField txtFieldNome;
    @FXML
    private TextField txtFieldCpf;
    @FXML
    private TextField txtFieldTelefoneContato;
    @FXML
    private CheckBox checkBoxColaborador;

    /**
     * Initializes the controller class.
     */
    
    Cliente novoCliente = null;
    Cliente clienteSelecionado = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        btnCarregarOnAction(null);
        mascaraTabela();

        tableClientes.getSelectionModel().selectedItemProperty().addListener((observable,newValue,oldValue)->{
            disableFields(false);
            unbindFields(oldValue);
            bindFields(newValue);
            clienteSelecionado = newValue;
        });
        
    }    

    @FXML
    private void btnCadastrarClienteOnAction(ActionEvent event) throws IOException {
        disableFields(false);
        novoCliente = new Cliente();
        bindFields(novoCliente);
        
    }

    @FXML
    private void btnCarregarOnAction(ActionEvent event) {
        try {
            tableClientes.setItems(FXCollections.observableArrayList(DAOFactory.getClienteDAO().getAll()));
        } catch (SQLException ex) {
            Logger.getLogger(ClienteSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage());
        }
    }
    
    @FXML
    private void btnCadastrarOnAction(ActionEvent event) {
        unbindFields(novoCliente);
        unbindFields(clienteSelecionado);
        
        try {
            if(novoCliente != null){
                DAOFactory.getClienteDAO().save(novoCliente);
            }else{
                if(clienteSelecionado != null){
                    DAOFactory.getClienteDAO().update(clienteSelecionado);
                }
            }
            clearFields();
            disableFields(true);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
        
    
    }
    
    
    private void mascaraTabela(){
        tblColumnColaborador.setCellFactory((TableColumn<Cliente, Boolean> param) -> {
            TableCell cell = new TableCell<Cliente, Boolean>() {

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
                              setText("Colaborador");  
                            }else{
                                setText("Aluno");
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
        checkBoxColaborador.setDisable(value);
    }
    
    private void bindFields(Cliente cliente){
        if(cliente != null){
            txtFieldNome.textProperty().bindBidirectional(cliente.nomeProperty());
            txtFieldTelefoneContato.textProperty().bindBidirectional(cliente.telefoneContatoProperty());
            txtFieldCpf.textProperty().bindBidirectional(cliente.CpfProperty());
            checkBoxColaborador.selectedProperty().bindBidirectional(cliente.colaboradorProperty());
        }
        
    }
    
    private void unbindFields(Cliente cliente){
        if(cliente != null){
            txtFieldNome.textProperty().unbindBidirectional(cliente.nomeProperty());
            txtFieldTelefoneContato.textProperty().unbindBidirectional(cliente.telefoneContatoProperty());
            txtFieldCpf.textProperty().unbindBidirectional(cliente.CpfProperty());
            checkBoxColaborador.selectedProperty().unbindBidirectional(cliente.colaboradorProperty());
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
        
        return invalido;
    }
    
    private void clearFields(){
        txtFieldCpf.clear();
        txtFieldNome.clear();
        txtFieldTelefoneContato.clear();
        checkBoxColaborador.setSelected(false);
    }
}
