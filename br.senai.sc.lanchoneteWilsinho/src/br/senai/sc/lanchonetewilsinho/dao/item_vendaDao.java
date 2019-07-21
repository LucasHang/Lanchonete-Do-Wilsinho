/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.dao;

import br.senai.sc.lanchonetewilsinho.model.Item_Venda;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Bratva
 */
public interface item_vendaDao {
    
    public void save(Item_Venda conta)throws SQLException;
    
    public void update(Item_Venda conta)throws SQLException;
    
    public void delete(Item_Venda conta)throws SQLException;
    
    public List<Item_Venda> getAll() throws SQLException;
    
    public List<Item_Venda> getItemVendaByCodigoVenda(Integer codigoVend)throws SQLException;
    
    public Item_Venda getItemVendaByCodVendaAndProduto(Item_Venda item)throws SQLException;
    
    
}
