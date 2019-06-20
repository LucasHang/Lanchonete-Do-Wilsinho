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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      //  btnCarregarOnAction(null);
      //  mascaraTabela();
    }    

    @FXML
    private void btnCadastrarFuncionarioOnAction(ActionEvent event) throws IOException {
        
        BrSenaiScLanchoneteWilsinho.mudarTela("cadastroFuncionario");
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
    
}
