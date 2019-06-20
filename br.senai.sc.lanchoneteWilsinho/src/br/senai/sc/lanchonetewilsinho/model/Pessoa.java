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
public class Pessoa {
    
    public final StringProperty nome = new SimpleStringProperty();
    public final IntegerProperty cpf = new SimpleIntegerProperty();
    public final StringProperty telefoneContato = new SimpleStringProperty();

    public String getTelefoneContato() {
        return this.telefoneContato.get();
    }

    public void setTelefoneContato(String value) {
        this.telefoneContato.set(value);
    }

    public StringProperty telefoneContatoProperty() {
        return this.telefoneContato;
    }
    

    public String getNome() {
        return this.nome.get();
    }

    public void setNome(String value) {
        this.nome.set(value);
    }

    public StringProperty nomeProperty() {
        return this.nome;
    }
    
      public Integer getCpf() {
        return this.cpf.get();
    }

    public void setCpf(Integer value) {
        this.cpf.set(value);
    }

    public IntegerProperty CpfProperty() {
        return this.cpf;
    }
    
}
