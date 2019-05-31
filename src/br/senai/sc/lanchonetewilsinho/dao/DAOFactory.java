/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.dao;

/**
 *
 * @author Bratva
 */
public class DAOFactory {
    
     public static clienteDao getClienteDAO(){
        return new clientePostgressDao();
    }
      public static funcionarioDao getFuncionarioDAO(){
        return new funcionarioPostgressDao();
    }
       public static item_vendaDao getItem_vendaDAO(){
        return new item_vendaPostgressDao();
    }
        public static produtoDao getProdutoDAO(){
        return new produtoPostgressDao();
    }
         public static vendaDao getVendaDAO(){
        return new vendaPostgressDao();
    }
         
}

