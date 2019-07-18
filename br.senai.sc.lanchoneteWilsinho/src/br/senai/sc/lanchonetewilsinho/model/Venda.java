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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Bratva
 */
public class Venda {
    private final IntegerProperty codigo = new SimpleIntegerProperty();
    
    private final IntegerProperty dataVenda = new SimpleIntegerProperty();
    private final IntegerProperty horaVenda = new SimpleIntegerProperty();

    private List<Item_Venda> itens = new ArrayList<>();
    Double total = 0.00;
    
    private final DoubleProperty valorTotalCompra = new SimpleDoubleProperty();
    
    private final ObjectProperty<Integer> cliente = new SimpleObjectProperty();
    private final StringProperty nomeCli = new SimpleStringProperty();
    
    private final ObjectProperty<Integer> funcionario = new SimpleObjectProperty();
    private final StringProperty nomeFun = new SimpleStringProperty();  

          
    public Venda(){
        
    }
    
    public Venda(Integer codigo, Integer codCliente, Integer codFuncionario,Double valor, List<Item_Venda> itens,Integer data,Integer hora,String nome1,String nome2){
        this.codigo.set(codigo);
        this.cliente.set(codCliente);
        this.funcionario.set(codFuncionario);
        this.valorTotalCompra.set(valor);
        this.dataVenda.set(data);
        this.itens = itens;
        this.horaVenda.set(hora);
        this.nomeCli.set(nome1);
        this.nomeFun.set(nome2);
    }
    
    public Venda(Integer codigo, Integer codCliente, Integer codFuncionario,Double valor, List<Item_Venda> itens,Integer data,Integer hora){
        this.codigo.set(codigo);
        this.cliente.set(codCliente);
        this.funcionario.set(codFuncionario);
        this.valorTotalCompra.set(valor);
        this.dataVenda.set(data);
        this.itens = itens;
        this.horaVenda.set(hora);
        
    }
    
    public String getNomeCli() {
        return nomeCli.get();
    }

    public void setNomeCli(String value) {
        nomeCli.set(value);
    }

    public StringProperty nomeCliProperty() {
        return nomeCli;
    }
    
    public String getNomeFun() {
        return nomeFun.get();
    }

    public void setNomeFun(String value) {
        nomeFun.set(value);
    }

    public StringProperty nomeFunProperty() {
        return nomeFun;
    }
    
    public Integer getHoraVenda() {
        return this.horaVenda.get();
    }

    public void setHoraVenda(Integer value) {
        this.horaVenda.set(value);
    }

    public IntegerProperty horaVendaProperty() {
        return this.horaVenda;
    }
    
    
    public Integer getFuncionario() {
        return this.funcionario.get();
    }

    public void setFuncionario(Integer value) {
        this.funcionario.set(value);
    }

    public ObjectProperty<Integer> funcionarioProperty() {
        return this.funcionario;
    }
    
    public Integer getCliente() {
        return this.cliente.get();
    }

    public void setCliente(Integer value) {
        this.cliente.set(value);
    }

    public ObjectProperty<Integer> clienteProperty() {
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
    
    public void setValorTotalCompra(Double value){
        this.valorTotalCompra.set(value);
        
    }
    
    public void calculaValorTotalCompra(){
        this.itens.forEach(item ->{
            total = total + item.getValorItem();
        });
        valorTotalCompra.set(total);
        total = 0.0;
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
