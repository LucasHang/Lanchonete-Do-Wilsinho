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
        
        super.preparedStatementInitialize("insert into cliente values(?,?,?,?)");
        super.prepared.setInt(1,cliente.getCpf());
        super.prepared.setString(2,cliente.getNome());
        super.prepared.setString(3,cliente.getTelefoneContato());
        super.prepared.setBoolean(4,cliente.getColaborador());
        super.prepared.execute();
        ResultSet results = super.prepared.getResultSet();
        
        results.close();
        super.closeAll();
        
    }

    @Override
    public void update(Cliente cliente) throws SQLException {
        
        super.preparedStatementInitialize("update cliente set cpf = ?,"
                + "nome = ?, telefoneContato = ?, colaborador = ? where cpf = ?");
        super.prepared.setInt(1,cliente.getCpf());
        super.prepared.setString(2,cliente.getNome());
        super.prepared.setString(3,cliente.getTelefoneContato());
        super.prepared.setBoolean(4,cliente.getColaborador());
        super.prepared.setInt(5,cliente.getCpf());
        super.prepared.execute();

        super.closeAll();
        
    }

    @Override
    public void delete(Cliente cliente) throws SQLException{
        
        super.preparedStatementInitialize("delete from cliente where cpf = ?");
        super.prepared.setInt(1,cliente.getCpf());
        super.prepared.execute();

        super.closeAll();
        
    }

    @Override
    public List<Cliente> getAll() throws SQLException{
        
        List<Cliente> clientes = new ArrayList();
        
        super.preparedStatementInitialize("select * from cliente");
        super.prepared.execute();
        ResultSet results = super.prepared.getResultSet();
        
        while(results.next()){
            clientes.add(new Cliente(results.getString("nome"),
            results.getInt("cpf"),
            results.getString("telefoneContato"),
            results.getBoolean("colaborador")));
        }
        results.close();
        super.closeAll();
        
        return clientes;
    }
    
}
