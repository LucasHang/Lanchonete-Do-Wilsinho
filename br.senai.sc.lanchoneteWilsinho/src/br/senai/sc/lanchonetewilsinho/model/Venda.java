/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.model;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Bratva
 */
public class Venda {
    private final IntegerProperty codigo = new SimpleIntegerProperty();
    private Cliente cliente = null;
    private Funcionario funcionario = null;
    private List<Item_Venda> itens = null;
          
    public Venda(){
        
    }
    
    public Venda(Integer codigo, Cliente objtCliente, Funcionario objtFuncionario, List<Item_Venda> itens ){
        this.codigo.set(codigo);
        this.cliente = objtCliente;
        this.funcionario = objtFuncionario;
        this.itens = itens;
    }
    
    public List<Item_Venda> getItens(){
        return this.itens;
    }
    
    
    public Funcionario getFuncionario() {
        return this.funcionario;
    }

    public void setFuncionario(Funcionario object) {
        this.funcionario = object;
    }  

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente object) {
        this.cliente = object;
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
    
}
