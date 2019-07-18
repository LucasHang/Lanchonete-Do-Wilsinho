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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Bratva
 */
public class Produto {
    private final IntegerProperty codigo = new SimpleIntegerProperty();
    private final StringProperty descricaoProd = new SimpleStringProperty();
    private final DoubleProperty precoProd = new SimpleDoubleProperty();
    private final IntegerProperty quantidadeProd = new SimpleIntegerProperty();
    
    
    public Produto(){
        
    }
    
    public Produto(Integer codigo, String descricao, Double preco, Integer quantidade){
        this.codigo.set(codigo);
        this.descricaoProd.set(descricao);
        this.precoProd.set(preco);
        this.quantidadeProd.set(quantidade);
    }
    
    public void adcProduto(Integer qtd){
        this.quantidadeProd.set(this.quantidadeProd.get() + qtd);
    }
    
    public void retirarProduto(Integer qtd){
         this.quantidadeProd.set(this.quantidadeProd.get() - qtd);
    }
    
    public Integer getQuantidadeProd() {
        return quantidadeProd.get();
    }

    public void setQuantidadeProd(Integer value) {
        quantidadeProd.set(value);
    }

    public IntegerProperty quantidadeProdProperty() {
        return quantidadeProd;
    }
    

    public double getPrecoProd() {
        return precoProd.get();
    }

    public void setPrecoProd(Double value) {
        precoProd.set(value);
    }

    public DoubleProperty precoProdProperty() {
        return precoProd;
    }
    
    
    public String getDescricaoProd() {
        return descricaoProd.get();
    }

    public void setDescricaoProd(String value) {
        descricaoProd.set(value);
    }

    public StringProperty descricaoProdProperty() {
        return descricaoProd;
    }
    

    public int getCodigo() {
        return codigo.get();
    }

    public void setCodigo(Integer value) {
        codigo.set(value);
    }

    public IntegerProperty codigoProperty() {
        return codigo;
    }
    
}
