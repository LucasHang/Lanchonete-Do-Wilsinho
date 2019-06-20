/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.model;

import br.senai.sc.lanchonetewilsinho.dao.DAOFactory;
import java.sql.SQLException;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Bratva
 */
public class Item_Venda {
    
    private final IntegerProperty codigoVenda = new SimpleIntegerProperty();
        
    private final IntegerProperty qtdComprada = new SimpleIntegerProperty();

    private final IntegerProperty produto = new SimpleIntegerProperty();
    private final DoubleProperty valorItem = new SimpleDoubleProperty();

  
    public Item_Venda(){
        
    }
    
    public Item_Venda(Integer codigoVenda, Integer codProduto, Integer qtdComprada, Double valorTotal){
        this.codigoVenda.set(codigoVenda);
        this.produto.set(codProduto);
        this.qtdComprada.set(qtdComprada);
        this.valorItem.set(valorTotal);
    }

    public double getValorItem() {
        return this.valorItem.get();
    }

    public void setValorItem() throws SQLException {
        this.valorItem.set(DAOFactory.getProdutoDAO().getProdutoByCodigo(produto.get()).getPrecoProd() * qtdComprada.get());
    }

    public DoubleProperty valorItemProperty() {
        return this.valorItem;
    }
    
    public int getProduto() {
        return this.produto.get();
    }

    public void setProduto(int value) {
        this.produto.set(value);
    }

    public IntegerProperty produtoProperty() {
        return this.produto;
    }
    
    public Integer getVenda(){
        return this.codigoVenda.get();
    }
    
    public void setVenda(Venda object){
        this.codigoVenda.set(object.getCodigo());
    }
    
    public IntegerProperty vendaProperty(){
        return this.codigoVenda;
    }

    public int getQtdComprada() {
        return this.qtdComprada.get();
    }

    public void setQtdComprada(Integer value) {
        this.qtdComprada.set(value);
    }

    public IntegerProperty qtdCompradaProperty() {
        return this.qtdComprada;
    }
    
}
