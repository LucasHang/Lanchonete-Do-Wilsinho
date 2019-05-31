/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.dao;

import br.senai.sc.lanchonetewilsinho.model.Produto;
import java.util.List;

/**
 *
 * @author Bratva
 */
public interface produtoDao {
    
    public void save(Produto conta);
    
    public void update(Produto conta);
    
    public void delete(Produto conta);
    
    public List<Produto> getAll();
}
