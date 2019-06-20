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
	bindFields(novoProduto);
    }    

    @FXML
    private void btnCadastrarOnAction(ActionEvent event) {
        unbindFields(novoProduto);
        try {
            DAOFactory.getProdutoDAO().save(novoProduto);
        } catch (SQLException ex) {
            Logger.getLogger(CadastroProdutoSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
        
        try {
            BrSenaiScLanchoneteWilsinho.mudarTela("produto");
        } catch (IOException ex) {
            Logger.getLogger(CadastroProdutoSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
        

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
    
}
