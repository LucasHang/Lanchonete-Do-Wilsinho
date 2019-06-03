/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho;

import br.senai.sc.lanchonetewilsinho.model.Cliente;
import br.senai.sc.lanchonetewilsinho.model.Funcionario;
import br.senai.sc.lanchonetewilsinho.model.Item_Venda;
import br.senai.sc.lanchonetewilsinho.model.Produto;
import br.senai.sc.lanchonetewilsinho.model.Venda;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bratva
 */
public class BancoDeDados {
    
     public static List<Cliente> clientes = new ArrayList();
     
     public static List<Funcionario> funcionarios = new ArrayList();
     
     public static List<Item_Venda> itens_venda = new ArrayList();
     
     public static List<Produto> produtos = new ArrayList();
     
     public static List<Venda> vendas = new ArrayList();
     
}
