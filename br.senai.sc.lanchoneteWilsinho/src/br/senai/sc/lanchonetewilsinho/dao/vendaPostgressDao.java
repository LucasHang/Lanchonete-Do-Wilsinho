/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.dao;

import br.senai.sc.lanchonetewilsinho.model.Item_Venda;
import br.senai.sc.lanchonetewilsinho.model.Venda;
import java.util.List;

/**
 *
 * @author Bratva
 */
public class vendaPostgressDao implements vendaDao{

    @Override
    public void save(Venda conta) {
        BancoDeDados.vendas.add(conta);
    }

    @Override
    public void update(Venda conta) {
         for(Venda contaDb : BancoDeDados.vendas){
            if(contaDb == conta){
                contaDb = conta;
                return;
            }
        }
    }

    @Override
    public void delete(Venda conta) {
        BancoDeDados.vendas.remove(conta);
    }

    @Override
    public List<Venda> getAll() {
        return BancoDeDados.vendas;
    }

}
