/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.controller;

import br.senai.sc.lanchonetewilsinho.dao.DAOFactory;
import br.senai.sc.lanchonetewilsinho.model.Produto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

/**
 * FXML Controller class
 *
 * @author Bratva
 */
public class CadastroProdutoSceneWindowController implements Initializable {
    @FXML
    private Button btnCadastrar;
    @FXML
    private TextField txtFieldDescricao;
    @FXML
    private TextField txtFieldQuantidade;
    @FXML
    private TextField txtFieldPrecoUnitario;

    Produto novoProduto= null;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        novoProduto = new Produto();
	txtFieldDescricao.textProperty().bindBidirectional(novoProduto.descricaoProdProperty());
	txtFieldPrecoUnitario.textProperty().bindBidirectional(novoProduto.precoProdProperty(), new NumberStringConverter());
	txtFieldQuantidade.textProperty().bindBidirectional(novoProduto.quantidadeProdProperty(), new NumberStringConverter());
    }    

    @FXML
    private void btnCadastrarOnAction(ActionEvent event) {
        DAOFactory.getProdutoDAO().save(novoProduto);
        txtFieldDescricao.textProperty().unbindBidirectional(novoProduto.descricaoProdProperty());
	txtFieldPrecoUnitario.textProperty().unbindBidirectional(novoProduto.precoProdProperty());
	txtFieldQuantidade.textProperty().unbindBidirectional(novoProduto.quantidadeProdProperty());

    }
    
}
