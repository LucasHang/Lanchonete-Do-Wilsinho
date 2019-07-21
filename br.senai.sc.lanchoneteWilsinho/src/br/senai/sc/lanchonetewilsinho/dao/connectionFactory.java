/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bratva
 */
public class connectionFactory {
    
    protected Connection connection = null;
    protected PreparedStatement prepared = null;
    
    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(connectionFactory.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
    private Connection getConnetion(String url, String user, String password) throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }
    
    private Connection getConnectionOracle(){
        return null;
    }
    
    private Connection getConnectionMySQL(){
        return null;
    }
    
    private Connection getConnetionPostgres() throws SQLException{
       return getConnetion("jdbc:postgresql://localhost/wilsinho", "postgres", "senai");
    }
    
    protected void preparedStatementInitialize(String sql) throws SQLException{
        connection = getConnetionPostgres();
        if(connection == null){
            throw new SQLException("Não foi possível realizar a conexão");
        }
        prepared = connection.prepareStatement(sql);
        if(prepared == null){
            throw new SQLException("Não foi possível realizar o prepared");
        }
        
    }
    
    protected void preparedStatementInitialize(String sql, String[] colunasRetorno) throws SQLException{
        connection = getConnetionPostgres();
        if(connection == null){
            throw new SQLException("Não foi possível gerar a conexão");
        }
        
        prepared = connection.prepareStatement(sql, colunasRetorno);
        if(prepared == null){
            throw new SQLException("Não foi possível gerar o prepared");
        }
    }
    
    protected void closeAll() throws SQLException{
        connection.close();
        prepared.close();
    }
    
}
