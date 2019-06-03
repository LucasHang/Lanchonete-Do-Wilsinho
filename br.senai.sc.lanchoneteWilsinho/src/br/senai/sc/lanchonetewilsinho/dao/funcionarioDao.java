/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.dao;

import br.senai.sc.lanchonetewilsinho.model.Funcionario;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Bratva
 */
public interface funcionarioDao {
    
    public void save(Funcionario funcionario)throws SQLException;
    
    public void update(Funcionario funcionario)throws SQLException;
    
    public void delete(Funcionario funcionario)throws SQLException;
    
    public List<Funcionario> getAll()throws SQLException;
}
