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
public class Funcionario extends Pessoa {
    
    private final StringProperty login = new SimpleStringProperty();
    private final StringProperty senha = new SimpleStringProperty();
    private final BooleanProperty gerente = new SimpleBooleanProperty();
    private final IntegerProperty codigo = new SimpleIntegerProperty();

    public Funcionario(){
        
    }
    
     public Funcionario(Integer codigo, String nome, Integer cpf,Integer telefoneContato,
             String login, String senha, Boolean gerente){
        
        this.codigo.set(codigo);
        this.nome.set(nome);
        this.cpf.set(cpf);
        this.telefoneContato.set(telefoneContato);
        this.gerente.set(gerente);
        this.senha.set(senha);
        this.login.set(login);
        
    }
    
    public int getCodigo() {
        return this.codigo.get();
    }

    public void setCodigo(Integer value) {
        this.codigo.set(value);
    }

    public IntegerProperty codigoProperty() {
        return this.codigo;
    }

    
    public boolean getGerente() {
        return this.gerente.get();
    }

    public void setGerente(Boolean value) {
        this.gerente.set(value);
    }

    public BooleanProperty gerenteProperty() {
        return this.gerente;
    }
    

    public String getSenha() {
        return senha.get();
    }

    public void setSenha(String value) {
        senha.set(value);
    }

    public StringProperty senhaProperty() {
        return senha;
    }
    

    public String getLogin() {
        return login.get();
    }

    public void setLogin(String value) {
        login.set(value);
    }

    public StringProperty loginProperty() {
        return login;
    }

    
}
