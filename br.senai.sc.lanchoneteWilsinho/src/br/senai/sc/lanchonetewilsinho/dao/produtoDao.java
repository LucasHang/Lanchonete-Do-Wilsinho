/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.dao;

import br.senai.sc.lanchonetewilsinho.model.Produto;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Bratva
 */
public interface produtoDao {
    
    public void save(Produto conta)throws SQLException;
    
    public void update(Produto conta)throws SQLException;
    
    public void delete(Produto conta)throws SQLException;
    
    public List<Produto> getAll()throws SQLException;
    
    public Produto getProdutoByCodigo(Integer codigo)throws SQLException;
}
