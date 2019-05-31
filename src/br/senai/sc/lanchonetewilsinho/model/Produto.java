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

    
    public void adcProduto(){
        
    }
    
    public void retirarProduto(){
        
    }
    
    public int getQuantidadeProd() {
        return quantidadeProd.get();
    }

    public void setQuantidadeProd(int value) {
        quantidadeProd.set(value);
    }

    public IntegerProperty quantidadeProdProperty() {
        return quantidadeProd;
    }
    

    public double getPrecoProd() {
        return precoProd.get();
    }

    public void setPrecoProd(double value) {
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

    public void setCodigo(int value) {
        codigo.set(value);
    }

    public IntegerProperty codigoProperty() {
        return codigo;
    }
    
}
