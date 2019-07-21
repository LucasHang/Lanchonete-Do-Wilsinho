/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.dao;

import br.senai.sc.lanchonetewilsinho.model.Item_Venda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bratva
 */
public class item_vendaPostgressDao extends connectionFactory implements item_vendaDao{

    @Override
    public void save(Item_Venda item_venda) throws SQLException {
        //String[] codigoGerado = {"codigo"};
        super.preparedStatementInitialize(
                "insert into ItemVenda (codigoVend, codigoProd, qtdComprada, valorTotal) values (?,?,?,?)"/*,
                codigoGerado*/);
        super.prepared.setInt(1, item_venda.getVenda());
        super.prepared.setInt(2, item_venda.getProduto());
        super.prepared.setInt(3, item_venda.getQtdComprada());
        super.prepared.setDouble(4, item_venda.getValorItem());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível cadastrar o novo item");
        }
        
       /* ResultSet resultSetRows = super.prepared.getGeneratedKeys();
        if (resultSetRows.next()) {
            item_venda.setCodigo(resultSetRows.getInt("codigo"));
        }
        resultSetRows.close();
               */
        super.closeAll();
    }

    @Override
    public void update(Item_Venda item_venda) throws SQLException{
          super.preparedStatementInitialize(
                "update ItemVenda set  qtdComprada= ?, valorTotal= ? where codigoVend = ? and codigoProd = ?");   
        super.prepared.setInt(1, item_venda.getQtdComprada());
        super.prepared.setDouble(2, item_venda.getValorItem());
        super.prepared.setInt(3, item_venda.getVenda());
        super.prepared.setInt(4,item_venda.getProduto());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível aletrar as informações do item");
        }
        super.closeAll();
              
    }

    @Override
    public void delete(Item_Venda item_venda)throws SQLException {
        super.preparedStatementInitialize(
                "delete from ItemVenda where codigoVend = ? and codigoProd = ?");
        super.prepared.setInt(1, item_venda.getVenda());
        super.prepared.setInt(2, item_venda.getProduto());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível deletar o item");
        }
        
        super.closeAll();
    }

    @Override
    public List<Item_Venda> getAll() throws SQLException {
        List<Item_Venda> rows = new ArrayList<>();
        super.preparedStatementInitialize("select * from ItemVenda");
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        while (resultSetRows.next()) {
            rows.add(new Item_Venda(resultSetRows.getInt("codigoVend"),
                    resultSetRows.getInt("codigoProd"),
                    resultSetRows.getInt("qtdComprada"),
                    resultSetRows.getDouble("valorTotal")));
        }
        resultSetRows.close();
        super.closeAll();

        return rows;
    }

    @Override
    public List<Item_Venda> getItemVendaByCodigoVenda(Integer codigoVenda) throws SQLException {
        List<Item_Venda> rows = new ArrayList<>();
        super.preparedStatementInitialize("select * from ItemVenda where codigoVend = ?");
        super.prepared.setInt(1,codigoVenda);
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        while (resultSetRows.next()) {
            rows.add(new Item_Venda(resultSetRows.getInt("codigoVend"),
                    resultSetRows.getInt("codigoProd"),
                    resultSetRows.getInt("qtdComprada"),
                    resultSetRows.getDouble("valorTotal")));
        }
        resultSetRows.close();
        super.closeAll();

        return rows;
    }

    @Override
    public Item_Venda getItemVendaByCodVendaAndProduto(Item_Venda item) throws SQLException {
        Item_Venda novoItem = new Item_Venda();
        super.preparedStatementInitialize("select * from ItemVenda where codigoVend = ? and codigoProd = ?");
        super.prepared.setInt(1,item.getVenda());
        super.prepared.setInt(2,item.getProduto());
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        while (resultSetRows.next()) {
            novoItem = (new Item_Venda(resultSetRows.getInt("codigoVend"),
                    resultSetRows.getInt("codigoProd"),
                    resultSetRows.getInt("qtdComprada"),
                    resultSetRows.getDouble("valorTotal")));
        }
        resultSetRows.close();
        super.closeAll();

        return novoItem;
    }
    
}
