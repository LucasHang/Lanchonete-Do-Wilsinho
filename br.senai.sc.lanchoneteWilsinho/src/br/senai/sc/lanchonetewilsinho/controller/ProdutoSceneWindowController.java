/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.controller;

import br.senai.sc.lanchonetewilsinho.BrSenaiScLanchoneteWilsinho;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.util.converter.NumberStringConverter;

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

    /**
     * Initializes the controller class.
     */
    
    Produto novoProduto= null;
    Produto produtoSelecionado = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCarregarOnAction(null);
        
        tableProdutos.getSelectionModel().selectedItemProperty().addListener((observable,newValue,oldValue)->{
            disableFields(false);
            unbindFields(oldValue);
            bindFields(newValue);
            produtoSelecionado = newValue;
            
        });
    }    

    @FXML
    private void btnCadastrarProdutoOnAction(ActionEvent event) throws IOException {
        
        disableFields(false);
        novoProduto = new Produto();
	bindFields(novoProduto);
    }
    
    @FXML
    private void btnCadastrarOnAction(ActionEvent event) {
        unbindFields(novoProduto);
        unbindFields(produtoSelecionado);
        
        try {
            if(novoProduto != null){
                DAOFactory.getProdutoDAO().save(novoProduto);
            }else{
                if(produtoSelecionado != null){
                    DAOFactory.getProdutoDAO().update(produtoSelecionado);
                }
            }
            clearFields();
            disableFields(true);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
  
    }
 

    @FXML
    private void btnCarregarOnAction(ActionEvent event) {
        
        try {
            tableProdutos.setItems(FXCollections.observableArrayList(DAOFactory.getProdutoDAO().getAll()));
        } catch (SQLException ex) {
            Logger.getLogger(ClienteSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage());
        }
    }
    
    private void mascaraTabela(){
        tblColumnQtdEstoque.setCellFactory((TableColumn<Produto, Integer> param) -> {
            TableCell cell = new TableCell<Produto, Integer>() {

                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(null);
                    setGraphic(null);
                    if (!empty) {
                        if (item == null) {
                            setText("");
                        } else {
                            if(item == 0){
                                tblColumnQtdEstoque.getStyleClass().add("sem-estoque");
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
        txtFieldDescricao.setDisable(value);
        txtFieldPrecoUnitario.setDisable(value);
        txtFieldQuantidade.setDisable(value);

    }
    
    private void bindFields(Produto produto){
        if(produto != null){
            txtFieldDescricao.textProperty().bindBidirectional(produto.descricaoProdProperty());
            txtFieldPrecoUnitario.textProperty().bindBidirectional(produto.precoProdProperty(), new NumberStringConverter());
            txtFieldQuantidade.textProperty().bindBidirectional(produto.quantidadeProdProperty(), new NumberStringConverter());
        }
        
    }
    
    private void unbindFields(Produto produto){
        if(produto != null){
            txtFieldDescricao.textProperty().unbindBidirectional(produto.descricaoProdProperty());
            txtFieldPrecoUnitario.textProperty().unbindBidirectional(produto.precoProdProperty());
            txtFieldQuantidade.textProperty().unbindBidirectional(produto.quantidadeProdProperty());
        }
    }
    
    public Boolean validationForm(){
        Boolean invalido = false;

        if(txtFieldDescricao.textProperty().isNull().get()){
            txtFieldDescricao.getStyleClass().add("invalido");
            invalido = true;     
        }else{
            txtFieldDescricao.getStyleClass().remove("invalido");
        }
        
        if(txtFieldPrecoUnitario.textProperty().isNull().get()){
            txtFieldPrecoUnitario.getStyleClass().add("invalido");
            invalido = true;
        }else{
            txtFieldPrecoUnitario.getStyleClass().remove("invalido");
        }
        
        if(txtFieldQuantidade.textProperty().isNull().get()){
            txtFieldQuantidade.getStyleClass().add("invalido");
            invalido = true;
        }else{
            txtFieldQuantidade.getStyleClass().remove("invalido");
        }
       
        return invalido;
    }
    
    private void clearFields(){
        txtFieldDescricao.clear();
        txtFieldPrecoUnitario.clear();
        txtFieldQuantidade.clear();
        
    }
    
}
