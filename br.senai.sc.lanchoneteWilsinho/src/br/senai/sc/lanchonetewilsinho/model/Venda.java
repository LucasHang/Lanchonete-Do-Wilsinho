/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.model;

import java.util.ArrayList;
import java.util.List;
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
public class Venda {
    private final IntegerProperty codigo = new SimpleIntegerProperty();
    
    private final IntegerProperty dataVenda = new SimpleIntegerProperty();

    private List<Item_Venda> itens = new ArrayList<>();
    
    private final DoubleProperty valorTotalCompra = new SimpleDoubleProperty();
    
    private final IntegerProperty cliente = new SimpleIntegerProperty();
    private final IntegerProperty funcionario = new SimpleIntegerProperty();

    

    
    
    
          
    public Venda(){
        
    }
    
    public Venda(Integer codigo, Integer codCliente, Integer codFuncionario,Double valor, List<Item_Venda> itens,Integer data){
        this.codigo.set(codigo);
        this.cliente.set(codCliente);
        this.funcionario.set(codFuncionario);
        this.valorTotalCompra.set(valor);
        this.dataVenda.set(data);
        this.itens = itens;
    }
    
    
    public Integer getFuncionario() {
        return this.funcionario.get();
    }

    public void setFuncionario(Integer value) {
        this.funcionario.set(value);
    }

    public IntegerProperty funcionarioProperty() {
        return this.funcionario;
    }
    
    public Integer getCliente() {
        return this.cliente.get();
    }

    public void setCliente(Integer value) {
        this.cliente.set(value);
    }

    public IntegerProperty clienteProperty() {
        return this.cliente;
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
    
    public Double getValorTotalCompra(){
        return this.valorTotalCompra.get();
    }
    
    public void setValorTotalCompra(){
        Double total = null;
        for(Item_Venda item : itens){
            total += item.getValorItem();
        }
        valorTotalCompra.set(total);
    }
    
    public DoubleProperty valorTotalCompraProperty(){
        return this.valorTotalCompra;
    } 
    
    
    public List<Item_Venda> getItens(){
        return this.itens;
    }
     
    public void setItens(List<Item_Venda> itens){
        this.itens = itens;
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
