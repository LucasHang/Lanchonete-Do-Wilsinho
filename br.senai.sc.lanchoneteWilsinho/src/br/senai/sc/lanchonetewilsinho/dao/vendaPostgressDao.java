/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.dao;

import br.senai.sc.lanchonetewilsinho.BrSenaiScLanchoneteWilsinho;
import br.senai.sc.lanchonetewilsinho.MeuAlerta;
import br.senai.sc.lanchonetewilsinho.controller.VendaSceneWindowController;
import br.senai.sc.lanchonetewilsinho.model.Item_Venda;
import br.senai.sc.lanchonetewilsinho.model.Venda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bratva
 */
public class vendaPostgressDao extends connectionFactory implements vendaDao {

    @Override
    public void save(Venda venda) throws SQLException {

        String[] codigoGerado = {"codigo"};
        super.preparedStatementInitialize(
                "insert into venda (codigoCli, codigoFunc, valorCompra, dataVenda,horaVenda) values (?,?,?,?,?)",
                codigoGerado);
        super.prepared.setInt(1, venda.getCliente());
        super.prepared.setInt(2, venda.getFuncionario());
        super.prepared.setDouble(3, venda.getValorTotalCompra());
        super.prepared.setInt(4, venda.getDataVenda());
        super.prepared.setInt(5, venda.getHoraVenda());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0) {
            throw new SQLException("Não foi possível cadastrar a nova venda");
        }

        ResultSet resultSetRows = super.prepared.getGeneratedKeys();
        if (resultSetRows.next()) {
            venda.setCodigo(resultSetRows.getInt("codigo"));
        }
        resultSetRows.close();
        super.closeAll();

        venda.getItens().forEach(item -> {
            item.setVenda(venda.getCodigo());
            try {
                DAOFactory.getProdutoDAO().updateQtdEstoque(item.getProduto(), item.getQtdComprada());
                DAOFactory.getItem_vendaDAO().save(item);
            } catch (SQLException ex) {
                Logger.getLogger(vendaPostgressDao.class.getName()).log(Level.SEVERE, null, ex);
                MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
            }
        });

    }

    @Override
    public void update(Venda venda) throws SQLException {
        super.preparedStatementInitialize(
                "update venda set codigoCli = ?, codigoFunc = ?, valorCompra = ? where codigo = ?");
        super.prepared.setInt(1, venda.getCliente());
        super.prepared.setInt(2, venda.getFuncionario());
        super.prepared.setDouble(3, venda.getValorTotalCompra());
        super.prepared.setInt(4, venda.getCodigo());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0) {
            throw new SQLException("Não foi possível aletrar as informações da venda");
        }
        super.closeAll();
        
        venda.getItens().forEach(item -> {
            
            try {
                DAOFactory.getProdutoDAO().updateQtdEstoque(item.getProduto(), item.getQtdComprada());
                if(DAOFactory.getItem_vendaDAO().getItemVendaByCodVendaAndProduto(item) != null){
                    DAOFactory.getItem_vendaDAO().update(item);
                }else{
                    item.setVenda(venda.getCodigo());
                    DAOFactory.getItem_vendaDAO().save(item);
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(vendaPostgressDao.class.getName()).log(Level.SEVERE, null, ex);
                MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
            }
        });
    }

    @Override
    public void delete(Venda venda) throws SQLException {
        super.preparedStatementInitialize(
                "delete from venda where codigo = ?");
        super.prepared.setInt(1, venda.getCodigo());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0) {
            throw new SQLException("Não foi possível deletar a venda");
        }

        super.closeAll();
    }

    @Override
    public List<Venda> getAll() throws SQLException {
        List<Venda> rows = new ArrayList<>();
        super.preparedStatementInitialize("select * from venda");
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        while (resultSetRows.next()) {
            rows.add(new Venda(resultSetRows.getInt("codigo"),
                    resultSetRows.getInt("codigoCli"),
                    resultSetRows.getInt("codigoFunc"),
                    resultSetRows.getDouble("valorCompra"),
                    DAOFactory.getItem_vendaDAO().getItemVendaByCodigoVenda(resultSetRows.getInt("codigo")),
                    resultSetRows.getInt("dataVenda"),
                    resultSetRows.getInt("horaVenda"),
                    DAOFactory.getClienteDAO().getClienteByCodigo(resultSetRows.getInt("codigoCli")).getNome() ,
                    DAOFactory.getFuncionarioDAO().getFuncionarioByCodigo(resultSetRows.getInt("codigoFunc")).getNome()));
        }
        resultSetRows.close();
        super.closeAll();

        return rows;
    }

    @Override
    public List<Venda> getVendaByData(String data) throws SQLException {
        List<Venda> rows = new ArrayList<>();
        super.preparedStatementInitialize("select * from venda where dataVenda = ?");
        super.prepared.setInt(1, BrSenaiScLanchoneteWilsinho.dateToIntegerConverter(data));
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        while (resultSetRows.next()) {
            rows.add(new Venda(resultSetRows.getInt("codigo"),
                    resultSetRows.getInt("codigoCli"),
                    resultSetRows.getInt("codigoFunc"),
                    resultSetRows.getDouble("valorCompra"),
                    DAOFactory.getItem_vendaDAO().getItemVendaByCodigoVenda(resultSetRows.getInt("codigo")),
                    resultSetRows.getInt("dataVenda"),
                    resultSetRows.getInt("horaVenda")));
        }
        resultSetRows.close();
        super.closeAll();

        return rows;
    }

    @Override
    public List<Venda> getVendaByNomeCliente(String nome) throws SQLException {
        List<Venda> rows = new ArrayList<>();
        super.preparedStatementInitialize("select a.* from venda a,cliente b where a.codigoCli = b.codigo and upper(b.nome) like ? ");
        super.prepared.setString(1, "%" + nome.toUpperCase() + "%");
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();

        while (resultSetRows.next()) {
            rows.add(new Venda(resultSetRows.getInt("codigo"),
                    resultSetRows.getInt("codigoCli"),
                    resultSetRows.getInt("codigoFunc"),
                    resultSetRows.getDouble("valorCompra"),
                    DAOFactory.getItem_vendaDAO().getItemVendaByCodigoVenda(resultSetRows.getInt("codigo")),
                    resultSetRows.getInt("dataVenda"),
                    resultSetRows.getInt("horaVenda")));
        }

        resultSetRows.close();
        super.closeAll();

        return rows;
    }

    @Override
    public List<Venda> getVendaByNomeFuncionario(String nome) throws SQLException {
        List<Venda> rows = new ArrayList<>();
        super.preparedStatementInitialize("select a.* from venda a,funcionario b where a.codigoFunc = b.codigo and upper(b.nome) like ? ");
        super.prepared.setString(1, "%" + nome.toUpperCase() + "%");
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();

        while (resultSetRows.next()) {
            rows.add(new Venda(resultSetRows.getInt("codigo"),
                    resultSetRows.getInt("codigoCli"),
                    resultSetRows.getInt("codigoFunc"),
                    resultSetRows.getDouble("valorCompra"),
                    DAOFactory.getItem_vendaDAO().getItemVendaByCodigoVenda(resultSetRows.getInt("codigo")),
                    resultSetRows.getInt("dataVenda"),
                    resultSetRows.getInt("horaVenda")));
        }

        resultSetRows.close();
        super.closeAll();

        return rows;
    }

    @Override
    public List<Venda> procurarVenda(String texto) throws SQLException {
        List<Venda> rows = new ArrayList<>();
        super.preparedStatementInitialize("select a.* from venda a ,funcionario b,cliente c where a.codigoFunc = b.codigo and a.codigoCli = c.codigo and (upper(b.nome) like ? or upper(c.nome) like ?)");
        super.prepared.setString(1, "%" + texto.toUpperCase() + "%");
        super.prepared.setString(2, "%" + texto.toUpperCase() + "%");
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();

        while (resultSetRows.next()) {
            rows.add(new Venda(resultSetRows.getInt("codigo"),
                    resultSetRows.getInt("codigoCli"),
                    resultSetRows.getInt("codigoFunc"),
                    resultSetRows.getDouble("valorCompra"),
                    DAOFactory.getItem_vendaDAO().getItemVendaByCodigoVenda(resultSetRows.getInt("codigo")),
                    resultSetRows.getInt("dataVenda"),
                    resultSetRows.getInt("horaVenda")));
        }

        resultSetRows.close();
        super.closeAll();

        return rows;
    }

}
