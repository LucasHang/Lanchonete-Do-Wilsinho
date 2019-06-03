/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.dao;

import br.senai.sc.lanchonetewilsinho.model.Item_Venda;
import br.senai.sc.lanchonetewilsinho.model.Venda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bratva
 */
public class vendaPostgressDao extends connectionFactory implements vendaDao{

    @Override
    public void save(Venda venda) throws SQLException {
        String[] codigoGerado = {"codigo"};
        super.preparedStatementInitialize(
                "insert into venda (codigoCli, codigoFunc, valorCompra, dataVenda) values (?,?,?,?)",
                codigoGerado);
        super.prepared.setInt(1, venda.getCliente().getCodigo());
        super.prepared.setInt(2, venda.getFuncionario().getCodigo());
        super.prepared.setDouble(3, venda.getValorCompra());
        super.prepared.setInt(4, venda.getDataVenda());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível cadastrar a nova venda");
        }
        
        ResultSet resultSetRows = super.prepared.getGeneratedKeys();
        if (resultSetRows.next()) {
            venda.setCodigo(resultSetRows.getInt("codigo"));
        }
        resultSetRows.close();
        super.closeAll();
    }

    @Override
    public void update(Venda venda) throws SQLException{
         super.preparedStatementInitialize(
                "update venda set codigoCli = ?, codigoFunc = ?, valorCompra = ?, dataVenda = ? where codigo = ?");
        super.prepared.setInt(1, venda.getCliente().getCodigo());
        super.prepared.setInt(2, venda.getFuncionario().getCodigo());
        super.prepared.setDouble(3, venda.getValorCompra());
        super.prepared.setInt(4, venda.getDataVenda());
        super.prepared.setInt(5, venda.getCodigo());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível aletrar as informações da venda");
        }
        super.closeAll();
    }

    @Override
    public void delete(Venda venda) throws SQLException{
        super.preparedStatementInitialize(
                "delete from venda where codigo = ?");
        super.prepared.setInt(1, venda.getCodigo());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível deletar a venda");
        }
        
        super.closeAll();
    }

    @Override
    public List<Venda> getAll() throws SQLException{
        List<Venda> rows = new ArrayList<>();
        super.preparedStatementInitialize("select * from venda");
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        while (resultSetRows.next()) {
            rows.add(new Venda(resultSetRows.getInt("codigo"),
                    DAOFactory.getClienteDAO().getClienteByCodigo(resultSetRows.getInt("codigoCli")),
                    DAOFactory.getFuncionarioDAO().getFuncionarioByCodigo(resultSetRows.getInt("codigoFunc")),
                    resultSetRows.getDouble("valorCompra")),
                    DAOFactory.getItem_vendaDAO().getItemVendaByCodigoVenda(resultSetRows.getInt("codigo")),
                    resultSetRows.getInt("dataVenda"));
        }
        resultSetRows.close();
        super.closeAll();

        return rows;
    }

}
