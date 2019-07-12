/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.dao;

import br.senai.sc.lanchonetewilsinho.model.Cliente;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Bratva
 */
public interface clienteDao {
    
    public void save(Cliente conta)throws SQLException;
    
    public void update(Cliente conta)throws SQLException;
    
    public void delete(Cliente conta)throws SQLException;
    
    public List<Cliente> getAll()throws SQLException;
    
    public Cliente getClienteByCodigo(Integer codigo)throws SQLException;
    
    public Cliente getClienteByCpf(String cpf)throws SQLException;
    
    public List<Cliente> getClienteByNome(String nome)throws SQLException;
    
}
