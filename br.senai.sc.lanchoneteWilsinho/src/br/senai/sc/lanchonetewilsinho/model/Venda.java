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
    private final IntegerProperty dataVenda = new SimpleIntegerProperty();

    private List<Item_Venda> itens = null;
    private Double valorTotalCompra = calculaTotal();
    
    private Double calculaTotal(){
        Double total = null;
        for(Item_Venda item : itens){
            total += item.getValorTotal();
        }
        return total;
    }
          
    public Venda(){
        
    }
    
    public Venda(Integer codigo, Cliente objtCliente, Funcionario objtFuncionario,Double valor, List<Item_Venda> itens,Integer data){
        this.codigo.set(codigo);
        this.cliente = objtCliente;
        this.funcionario = objtFuncionario;
        this.valorTotalCompra = valor;
        this.dataVenda.set(data);
        this.itens = itens;
    }
    
    public Integer getDataVenda() {
        return this.dataVenda.get();
    }

    public void setDataVenda(Integer value) {
        this.dataVenda.set(value);
    }

    public IntegerProperty dataVendaProperty() {
        return this.dataVenda;
    }
    
    public Double getValorCompra(){
        return this.valorTotalCompra;
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

    public Integer getCodigo() {
        return this.codigo.get();
    }

    public void setCodigo(Integer value) {
        this.codigo.set(value);
    }

    public IntegerProperty codigoProperty() {
        return this.codigo;
    }
    
}
