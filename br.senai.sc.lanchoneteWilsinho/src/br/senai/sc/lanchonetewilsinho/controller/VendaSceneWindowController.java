/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.controller;

import br.senai.sc.lanchonetewilsinho.dao.DAOFactory;
import br.senai.sc.lanchonetewilsinho.model.Cliente;
import br.senai.sc.lanchonetewilsinho.model.Funcionario;
import br.senai.sc.lanchonetewilsinho.model.Produto;
import br.senai.sc.lanchonetewilsinho.model.Venda;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Bratva
 */
public class VendaSceneWindowController implements Initializable {

    @FXML
    private ComboBox<Funcionario> comboFuncionario;
    @FXML
    private ComboBox<Cliente> comboCliente;
    @FXML
    private ComboBox<Produto> comboProduto;
    @FXML
    private Button btnSalvarItem;
    @FXML
    private TextField txtQtdProduto;
    @FXML
    private Label labelValorTotal;
    @FXML
    private Button btnFinalizarCompra;
    @FXML
    private TableView<Venda> tableFuncionarios;
    @FXML
    private TextField txtCarregar;
    @FXML
    private Button btnCadastrarVenda;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fillComboBoxes();
        } catch (SQLException ex) {
            Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mascaraComboBox("Produto");
        mascaraComboBox("Funcionário");
        mascaraComboBox("Cliente");
    }

    private void fillComboBoxes() throws SQLException{
        comboProduto.setItems(FXCollections.observableArrayList(DAOFactory.getProdutoDAO().getAll()));
        comboCliente.setItems(FXCollections.observableArrayList(DAOFactory.getClienteDAO().getAll()));
        comboFuncionario.setItems(FXCollections.observableArrayList(DAOFactory.getFuncionarioDAO().getAll()));
    }
    
    private void mascaraComboBox(String tipo) {

        switch (tipo) {
            case "Produto":
                comboProduto.setCellFactory(new Callback<ListView<Produto>, ListCell<Produto>>() {
                    @Override
                    public ListCell<Produto> call(ListView<Produto> l) {
                        return new ListCell<Produto>() {
                            @Override
                            protected void updateItem(Produto item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item == null || empty) {
                                    setGraphic(null);
                                } else {
                                    setText(item.getDescricaoProd());
                                }
                            }
                        };
                    }
                });
                break;
            case "Funcionário":
                comboFuncionario.setCellFactory(new Callback<ListView<Funcionario>, ListCell<Funcionario>>() {
                    @Override
                    public ListCell<Funcionario> call(ListView<Funcionario> l) {
                        return new ListCell<Funcionario>() {
                            @Override
                            protected void updateItem(Funcionario item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item == null || empty) {
                                    setGraphic(null);
                                } else {
                                    setText(item.getNome());
                                }
                            }
                        };
                    }
                });
                break;
            case "Cliente":
                comboCliente.setCellFactory(new Callback<ListView<Cliente>, ListCell<Cliente>>() {
                    @Override
                    public ListCell<Cliente> call(ListView<Cliente> l) {
                        return new ListCell<Cliente>() {
                            @Override
                            protected void updateItem(Cliente item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item == null || empty) {
                                    setGraphic(null);
                                } else {
                                    setText(item.getNome());
                                }
                            }
                        };
                    }
                });
                break;
        }

    }

    @FXML
    private void btnSalvarItemOnAction(ActionEvent event) {
    }

    @FXML
    private void btnFinalizarCompraOnAction(ActionEvent event) {
    }

    @FXML
    private void btnCarregarOnAction(ActionEvent event) {
    }

    @FXML
    private void btnCadastrarVendaOnAction(ActionEvent event) {
    }

}
