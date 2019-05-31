/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.dao;

import br.senai.sc.lanchonetewilsinho.BancoDeDados;
import br.senai.sc.lanchonetewilsinho.model.Item_Venda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bratva
 */
public class item_vendaPostgressDao extends connectionFactory implements item_vendaDao{

    @Override
    public void save(Item_Venda conta) {
        BancoDeDados.itens_venda.add(conta);
    }

    @Override
    public void update(Item_Venda conta) {
          for(Item_Venda contaDb : BancoDeDados.itens_venda){
            if(contaDb == conta){
                contaDb = conta;
                return;
            }
        }
    }

    @Override
    public void delete(Item_Venda conta) {
        BancoDeDados.itens_venda.remove(conta);
    }

    @Override
    public List<Item_Venda> getAll() throws SQLException {
        
    }

    @Override
    public List<Item_Venda> getItemVendaByCodigoVenda(Integer codigoVenda) throws SQLException {
        List<Item_Venda> rows = new ArrayList<>();
        super.preparedStatementInitialize("select * from Item_Venda where codigoVend = ?");
        super.prepared.setInt(1,codigoVenda);
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        while (resultSetRows.next()) {
            rows.add(new Item_Venda(resultSetRows.getInt("codigoVend"),
                    DAOFactory.getProdutoDAO().getProdutoByCodigo(resultSetRows.getString("codigoProd")),
                    resultSetRows.getInt("qtdComprada"),
                    resultSetRows.getDouble("valorTotal")));
        }
        resultSetRows.close();
        super.closeAll();

        return rows;
    }
    
}
