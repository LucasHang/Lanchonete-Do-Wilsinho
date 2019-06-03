/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.model;

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
    private Produto produto = null;
    private final IntegerProperty qtdComprada = new SimpleIntegerProperty();
    private Double valorTotal = (produto.getPrecoProd() * qtdComprada.get());
    
    public Item_Venda(){
        
    }
    
    public Item_Venda(Integer codigoVenda, Produto objectProduto, Integer qtdComprada, Double valorTotal){
        this.codigoVenda.set(codigoVenda);
        this.produto = objectProduto;
        this.qtdComprada.set(qtdComprada);
        this.valorTotal = valorTotal;
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
    
     public Produto getProduto(){
        return this.produto;
    }
    
    public void setProduto(Produto object){
        this.produto = object;
    }
    
    
    public double getValorTotal() {
        return this.valorTotal;
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
