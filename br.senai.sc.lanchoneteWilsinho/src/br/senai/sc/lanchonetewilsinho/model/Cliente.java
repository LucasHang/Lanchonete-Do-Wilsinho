/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Bratva
 */
public class Cliente extends Pessoa {
    
    
    private final BooleanProperty colaborador = new SimpleBooleanProperty();
    private final IntegerProperty codigo = new SimpleIntegerProperty();

     public Cliente(){
        
    }
    
    public Cliente(Integer codigo, String nome, Integer cpf,Integer telefoneContato,Boolean colaborador){
        this.codigo.set(codigo);
        this.nome.set(nome);
        this.cpf.set(cpf);
        this.telefoneContato.set(telefoneContato);
        this.colaborador.set(colaborador);
        
    }
    
    
    public int getCodigo() {
        return this.codigo.get();
    }

    public void setCodigo(int value) {
        this.codigo.set(value);
    }

    public IntegerProperty codigoProperty() {
        return this.codigo;
    }
    
    public Boolean getColaborador() {
        return this.colaborador.get();
    }

    public void setColaborador(Boolean value) {
        this.colaborador.set(value);
    }

    public BooleanProperty colaboradorProperty() {
        return this.colaborador;
    }    
    
}
