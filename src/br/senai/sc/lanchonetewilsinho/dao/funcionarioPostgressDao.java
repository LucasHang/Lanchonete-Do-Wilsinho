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
public class funcionarioPostgressDao implements funcionarioDao {

    @Override
    public void save(Funcionario conta) {
        
    }

    @Override
    public void update(Funcionario conta) {
          for(Funcionario contaDb : BancoDeDados.funcionarios){
            if(contaDb == conta){
                contaDb = conta;
                return;
            }
        }
    }

    @Override
    public void delete(Funcionario conta) {
        BancoDeDados.funcionarios.remove(conta);
    }

    @Override
    public List<Funcionario> getAll() {
        return BancoDeDados.funcionarios;
    }
    
}
