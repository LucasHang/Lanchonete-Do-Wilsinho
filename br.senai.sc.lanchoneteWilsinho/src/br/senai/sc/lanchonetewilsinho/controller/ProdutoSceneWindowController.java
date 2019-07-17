/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.controller;

import br.senai.sc.lanchonetewilsinho.BrSenaiScLanchoneteWilsinho;
import br.senai.sc.lanchonetewilsinho.DoWork;
import br.senai.sc.lanchonetewilsinho.MeuAlerta;
import br.senai.sc.lanchonetewilsinho.dao.DAOFactory;
import br.senai.sc.lanchonetewilsinho.model.Produto;
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
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import javax.xml.bind.Marshaller;

/**
 * FXML Controller class
 *
 * @author Bratva
 */
public class ProdutoSceneWindowController implements Initializable {

    @FXML
    private Button btnCadastrarProduto;
    @FXML
    private TableView<Produto> tableProdutos;
    @FXML
    private TextField txtCarregar;
    @FXML
    private TableColumn<Produto, Integer> tblColumnQtdEstoque;
    @FXML
    private Button btnCadastrar;
    @FXML
    private TextField txtFieldDescricao;
    @FXML
    private TextField txtFieldQuantidade;
    @FXML
    private TextField txtFieldPrecoUnitario;
    @FXML
    private Button btnCancelarAcao;
    @FXML
    private AnchorPane anchorProduto;
    @FXML
    private Button btnCarregar;
    @FXML
    private Label labelBloqueado;
    @FXML
    private Label lblDesc;
    @FXML
    private Label lblQtd;
    @FXML
    private Label lblPreco;
    /**
     * Initializes the controller class.
     */
    Produto novoProduto = null;
    Produto produtoSelecionado = null;

    DoWork task;
    Thread tredi;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (permissaoFuncionario()) {
            return;
        }
        btnCarregarOnAction(null);
        mascaraTabela();
        addListenner();
    }

    @FXML
    private void btnCadastrarProdutoOnAction(ActionEvent event) throws IOException {
        disableFields(false);
        unbindFields(produtoSelecionado);
        produtoSelecionado = null;
        if(novoProduto == null){
            novoProduto = new Produto();
        }
        bindFields(novoProduto);
    }

    @FXML
    private void btnCadastrarOnAction(ActionEvent event) {
        
        if(validationForm()){
            return;
        }
        
        txtFieldDescricao.getStyleClass().remove("invalido");
        txtFieldPrecoUnitario.getStyleClass().remove("invalido");
        txtFieldQuantidade.getStyleClass().remove("invalido");
        
        unbindFields(novoProduto);
        unbindFields(produtoSelecionado);

        task = new DoWork();
        tredi = new Thread(task);
        tredi.start();

        try {
            if (novoProduto != null) {
                DAOFactory.getProdutoDAO().save(novoProduto);
            } else {
                if (produtoSelecionado != null) {
                    DAOFactory.getProdutoDAO().update(produtoSelecionado);
                }else{
                    task.cancel();
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }

        task.setOnRunning(ev -> {
            anchorProduto.setCursor(Cursor.WAIT);

        });

        task.setOnSucceeded(ev -> {
            btnCarregarOnAction(null);
            btnCancelarAcaoOnAction(null);

            anchorProduto.setCursor(Cursor.DEFAULT);
        });

    }

    @FXML
    private void btnCarregarOnAction(ActionEvent event) {

        try {
            if (txtCarregar.getText().isEmpty()) {
                tableProdutos.setItems(FXCollections.observableArrayList(DAOFactory.getProdutoDAO().getAll()));
            } else {
                tableProdutos.setItems(FXCollections.observableArrayList(DAOFactory.getProdutoDAO().getProdutoByDescricao(txtCarregar.getText())));

            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage());
        }
    }

    @FXML
    private void btnCancelarAcaoOnAction(ActionEvent event) {
        unbindFields(novoProduto);
        unbindFields(produtoSelecionado);
        clearFields();
        disableFields(true);
        novoProduto = null;
        produtoSelecionado = null;
        txtFieldDescricao.getStyleClass().remove("invalido");
        txtFieldPrecoUnitario.getStyleClass().remove("invalido");
        txtFieldQuantidade.getStyleClass().remove("invalido");
    }

    private void mascaraTabela() {
        tblColumnQtdEstoque.setCellFactory(new Callback<TableColumn<Produto, Integer>, TableCell<Produto, Integer>>() {
            @Override
            public TableCell<Produto, Integer> call(TableColumn<Produto, Integer> param) {
                return new TableCell<Produto, Integer>() {

                    @Override
                    public void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(null);
                        setGraphic(null);
                        getStyleClass().remove("sem-estoque");
                        if (!empty && item != null) {
                            setText(item.toString());
                            if (item == 0) {
                                getStyleClass().add("sem-estoque");
                            }

                        }
                    }

                };
            }
        });

    }

    private Boolean permissaoFuncionario() {
        if (!mainSceneWindowController.funcLogado.getGerente()) {
            anchorProduto.setDisable(true);
            btnCadastrar.setDisable(true);
            btnCadastrarProduto.setDisable(true);
            btnCancelarAcao.setDisable(true);
            btnCarregar.setDisable(true);
            tableProdutos.setDisable(true);
            txtCarregar.setDisable(true);

            btnCadastrar.setOpacity(0.20);
            btnCadastrarProduto.setOpacity(0.20);
            btnCancelarAcao.setOpacity(0.20);
            btnCarregar.setOpacity(0.20);
            tableProdutos.setOpacity(0.20);
            txtCarregar.setOpacity(0.20);
            lblDesc.setOpacity(0.20);
            lblPreco.setOpacity(0.20);
            lblQtd.setOpacity(0.20);

            labelBloqueado.setVisible(true);
            return true;
        }
        return false;
    }

    private void disableFields(Boolean value) {
        txtFieldDescricao.setDisable(value);
        txtFieldPrecoUnitario.setDisable(value);
        txtFieldQuantidade.setDisable(value);

    }

    private void bindFields(Produto produto) {
        if (produto != null) {
            txtFieldDescricao.textProperty().bindBidirectional(produto.descricaoProdProperty());
            txtFieldPrecoUnitario.textProperty().bindBidirectional(produto.precoProdProperty(), new NumberStringConverter());
            txtFieldQuantidade.textProperty().bindBidirectional(produto.quantidadeProdProperty(), new NumberStringConverter());
        }

    }

    private void unbindFields(Produto produto) {
        if (produto != null) {
            txtFieldDescricao.textProperty().unbindBidirectional(produto.descricaoProdProperty());
            txtFieldPrecoUnitario.textProperty().unbindBidirectional(produto.precoProdProperty());
            txtFieldQuantidade.textProperty().unbindBidirectional(produto.quantidadeProdProperty());
        }
    }

    public Boolean validationForm() {
        Boolean invalido = false;

        if (txtFieldDescricao.textProperty().isNull().get()) {
            txtFieldDescricao.getStyleClass().add("invalido");
            invalido = true;
        } else {
            txtFieldDescricao.getStyleClass().remove("invalido");
        }

        if (txtFieldPrecoUnitario.textProperty().isNull().get()) {
            txtFieldPrecoUnitario.getStyleClass().add("invalido");
            invalido = true;
        } else {
            txtFieldPrecoUnitario.getStyleClass().remove("invalido");
        }

        if (txtFieldQuantidade.textProperty().isNull().get()) {
            txtFieldQuantidade.getStyleClass().add("invalido");
            invalido = true;
        } else {
            txtFieldQuantidade.getStyleClass().remove("invalido");
        }

        return invalido;
    }

    private void clearFields() {
        txtFieldDescricao.clear();
        txtFieldPrecoUnitario.clear();
        txtFieldQuantidade.clear();

    }

    private void addListenner() {
        tableProdutos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            disableFields(false);
            novoProduto = null;
            unbindFields(oldValue);
            bindFields(newValue);
            produtoSelecionado = newValue;
        });

    }


    @FXML
    private void ENTER(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            task = new DoWork();
            tredi = new Thread(task);
            tredi.start();

            task.setOnRunning(ev -> {
                anchorProduto.setCursor(Cursor.WAIT);
            });

            task.setOnSucceeded(ev -> {

                btnCarregarOnAction(null);
                anchorProduto.setCursor(Cursor.DEFAULT);
            });

        }
    }

}
