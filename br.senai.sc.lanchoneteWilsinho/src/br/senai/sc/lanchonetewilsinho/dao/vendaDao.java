/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.dao;

import br.senai.sc.lanchonetewilsinho.model.Item_Venda;
import br.senai.sc.lanchonetewilsinho.model.Venda;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Bratva
 */
public interface vendaDao {
    
    public void save(Venda conta)throws SQLException;
    
    public void update(Venda conta)throws SQLException;
    
    public void delete(Venda conta)throws SQLException;
    
    public List<Venda> getAll()throws SQLException;
    
}
