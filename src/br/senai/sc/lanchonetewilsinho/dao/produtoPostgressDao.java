/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.dao;

import br.senai.sc.lanchonetewilsinho.BancoDeDados;
import br.senai.sc.lanchonetewilsinho.model.Produto;
import java.util.List;

/**
 *
 * @author Bratva
 */
public class produtoPostgressDao implements produtoDao {

    @Override
    public void save(Produto conta) {
        BancoDeDados.produtos.add(conta);
    }

    @Override
    public void update(Produto conta) {
          for(Produto contaDb : BancoDeDados.produtos){
            if(contaDb == conta){
                contaDb = conta;
                return;
            }
        }
    }

    @Override
    public void delete(Produto conta) {
        BancoDeDados.produtos.remove(conta);
    }

    @Override
    public List<Produto> getAll() {
        return BancoDeDados.produtos;
    }
    
}
