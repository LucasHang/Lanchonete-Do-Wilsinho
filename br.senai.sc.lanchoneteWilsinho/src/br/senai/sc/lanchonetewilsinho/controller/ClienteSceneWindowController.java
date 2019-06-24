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
    private TableColumn<Cliente, Boolean> tblColumnColaborador;
    @FXML
    private TableColumn<?, ?> tblColumColaborador;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        btnCarregarOnAction(null);
        mascaraTabela();
        
    }    

    @FXML
    private void btnCadastrarClienteOnAction(ActionEvent event) throws IOException {
       
        
        
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
                                setText("Estudante");
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

    @FXML
    private void btnCadastrarOnAction(ActionEvent event) {
    }
}
