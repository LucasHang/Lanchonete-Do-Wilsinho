/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.dao;

import br.senai.sc.lanchonetewilsinho.BancoDeDados;
import br.senai.sc.lanchonetewilsinho.model.Produto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bratva
 */
public class produtoPostgressDao extends connectionFactory implements produtoDao {

    @Override
    public void save(Produto produto) throws SQLException {
        String[] codigoGerado = {"codigo"};
        super.preparedStatementInitialize(
                "insert into produto (descricao, preco, quantidade) values (?,?,?)",
                codigoGerado);
        super.prepared.setString(1, produto.getDescricaoProd());
        super.prepared.setDouble(2, produto.getPrecoProd());
        super.prepared.setInt(3, produto.getQuantidadeProd());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível cadastrar o novo produto");
        }
        
        ResultSet resultSetRows = super.prepared.getGeneratedKeys();
        if (resultSetRows.next()) {
            produto.setCodigo(resultSetRows.getInt("codigo"));
        }
        resultSetRows.close();
        super.closeAll();
    }

    @Override
    public void update(Produto produto) throws SQLException{
          super.preparedStatementInitialize(
                "update produto set descricao = ?, preco = ?, quantidade = ? where codigo = ?");
        super.prepared.setString(1, produto.getDescricaoProd());
        super.prepared.setDouble(2, produto.getPrecoProd());
        super.prepared.setInt(3, produto.getQuantidadeProd());
        super.prepared.setInt(4, produto.getCodigo());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível aletrar as informações do produto");
        }
        super.closeAll();
    }

    @Override
    public void delete(Produto produto) throws SQLException{
        super.preparedStatementInitialize(
                "delete from produto where codigo = ?");
        super.prepared.setInt(1, produto.getCodigo());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível deletar o produto");
        }
        
        super.closeAll();
    }

    @Override
    public List<Produto> getAll() throws SQLException{
        List<Produto> rows = new ArrayList<>();
        super.preparedStatementInitialize("select * from produto");
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        while (resultSetRows.next()) {
            rows.add(new Produto(resultSetRows.getInt("codigo"),
                    resultSetRows.getString("descricao"),
                    resultSetRows.getDouble("preco"),
                    resultSetRows.getInt("quantidade")));
        }
        resultSetRows.close();
        super.closeAll();

        return rows;
    }
    
}
