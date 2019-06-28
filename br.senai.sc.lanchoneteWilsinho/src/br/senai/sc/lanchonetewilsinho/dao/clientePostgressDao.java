/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.dao;


import br.senai.sc.lanchonetewilsinho.model.Cliente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bratva
 */
public class clientePostgressDao extends connectionFactory implements clienteDao {

    @Override
    public void save(Cliente cliente) throws SQLException {
        
        String[] codigoGerado = {"codigo"};
        super.preparedStatementInitialize(
                "insert into cliente (nome, cpf, telefoneContato, colaborador) values (?,?,?,?)",
                codigoGerado);
        super.prepared.setString(1, cliente.getNome());
        super.prepared.setInt(2, cliente.getCpf());
        super.prepared.setString(3, cliente.getTelefoneContato());
        super.prepared.setBoolean(4, cliente.getColaborador());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível cadastrar o novo cliente");
        }
        
        ResultSet resultSetRows = super.prepared.getGeneratedKeys();
        if (resultSetRows.next()) {
            cliente.setCodigo(resultSetRows.getInt("codigo"));
        }
        resultSetRows.close();
        super.closeAll();
        
    }

    @Override
    public void update(Cliente cliente) throws SQLException {
        
        super.preparedStatementInitialize(
                "update cliente set nome = ?, cpf = ?, telefoneContato= ?, colaborador = ? where codigo = ?");
        super.prepared.setString(1, cliente.getNome());
        super.prepared.setInt(2, cliente.getCpf());
        super.prepared.setString(3, cliente.getTelefoneContato());
        super.prepared.setBoolean(4, cliente.getColaborador());
        super.prepared.setInt(5, cliente.getCodigo());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível aletrar as informações do cliente");
        }
        super.closeAll();
        
    }

    @Override
    public void delete(Cliente cliente) throws SQLException{
        
        super.preparedStatementInitialize(
                "delete from cliente where codigo = ?");
        super.prepared.setInt(1, cliente.getCodigo());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível deletar o cliente");
        }
        
        super.closeAll();
        
    }

    @Override
    public List<Cliente> getAll() throws SQLException{
        
        List<Cliente> rows = new ArrayList<>();
        super.preparedStatementInitialize("select * from cliente");
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        while (resultSetRows.next()) {
            rows.add(new Cliente(resultSetRows.getInt("codigo"),
                    resultSetRows.getString("nome"),
                    resultSetRows.getInt("cpf"),
                    resultSetRows.getString("telefoneContato"),
                    resultSetRows.getBoolean("colaborador")));
        }
        resultSetRows.close();
        super.closeAll();

        return rows;
    }

    @Override
    public Cliente getClienteByCodigo(Integer codigo) throws SQLException {
        Cliente novoCliente = null;
        super.preparedStatementInitialize("select * from produto");
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        if (resultSetRows.next()) {
            novoCliente = new Cliente(resultSetRows.getInt("codigo"),
                    resultSetRows.getString("nome"),
                    resultSetRows.getInt("cpf"),
                    resultSetRows.getString("telefoneContato"),
                    resultSetRows.getBoolean("colaborador"));
        }
        resultSetRows.close();
        super.closeAll();

        return novoCliente;
    }

    @Override
    public List<Cliente> getClienteByNome(String nome) throws SQLException {
        List<Cliente> rows = new ArrayList<>();
        super.preparedStatementInitialize("select * from cliente where upper(nome) like ?");
        super.prepared.setString(1, "%"+nome.toUpperCase()+"%");
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        while (resultSetRows.next()) {
            rows.add(new Cliente(resultSetRows.getInt("codigo"),
                    resultSetRows.getString("nome"),
                    resultSetRows.getInt("cpf"),
                    resultSetRows.getString("telefoneContato"),
                    resultSetRows.getBoolean("colaborador")));
        }
        resultSetRows.close();
        super.closeAll();

        return rows;
    }
    
    
}
