/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.dao;

import br.senai.sc.lanchonetewilsinho.model.Funcionario;
import java.util.List;

/**
 *
 * @author Bratva
 */
public interface funcionarioDao {
    
    public void save(Funcionario conta);
    
    public void update(Funcionario conta);
    
    public void delete(Funcionario conta);
    
    public List<Funcionario> getAll();
}
