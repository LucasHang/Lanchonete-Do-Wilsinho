/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.dao;

import br.senai.sc.lanchonetewilsinho.model.Funcionario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bratva
 */
public class funcionarioPostgressDao extends connectionFactory implements funcionarioDao {

    @Override
    public void save(Funcionario funcionario) throws SQLException {
        String[] codigoGerado = {"codigo"};
        super.preparedStatementInitialize(
                "insert into funcionario (nome, cpf, telefoneContato, login, senha, gerente) values (?,?,?,?,?,?)",
                codigoGerado);
        super.prepared.setString(1, funcionario.getNome());
        super.prepared.setString(2, funcionario.getCpf());
        super.prepared.setString(3, funcionario.getTelefoneContato());
        super.prepared.setString(4, funcionario.getLogin());
        super.prepared.setString(5, funcionario.getSenha());
        super.prepared.setBoolean(6, funcionario.getGerente());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível cadastrar o novo funcionario");
        }
        
        ResultSet resultSetRows = super.prepared.getGeneratedKeys();
        if (resultSetRows.next()) {
            funcionario.setCodigo(resultSetRows.getInt("codigo"));
        }
        resultSetRows.close();
        super.closeAll();
    }

    @Override
    public void update(Funcionario funcionario) throws SQLException{
          super.preparedStatementInitialize(
                "update funcionario set nome = ?, cpf= ?, telefoneContato = ?, login= ?, senha = ?, gerente = ? where codigo = ?");
        super.prepared.setString(1, funcionario.getNome());
        super.prepared.setString(2, funcionario.getCpf());
        super.prepared.setString(3, funcionario.getTelefoneContato());
        super.prepared.setString(4, funcionario.getLogin());
        super.prepared.setString(5, funcionario.getSenha());
        super.prepared.setBoolean(6, funcionario.getGerente());
        super.prepared.setInt(7, funcionario.getCodigo());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível aletrar as informações do funcionario");
        }
        super.closeAll();
    }

    @Override
    public void delete(Funcionario funcionario)throws SQLException {
        super.preparedStatementInitialize(
                "delete from funcionario where codigo = ?");
        super.prepared.setInt(1, funcionario.getCodigo());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível deletar o funcionario");
        }
        
        super.closeAll();
    }

    @Override
    public List<Funcionario> getAll()throws SQLException {
        List<Funcionario> rows = new ArrayList<>();
        super.preparedStatementInitialize("select * from funcionario");
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        while (resultSetRows.next()) {
            rows.add(new Funcionario(resultSetRows.getInt("codigo"),
                    resultSetRows.getString("nome"),
                    resultSetRows.getString("cpf"),
                    resultSetRows.getString("telefoneContato"),
                    resultSetRows.getString("login"),
                    resultSetRows.getString("senha"),
                    resultSetRows.getBoolean("gerente")));
        }
        resultSetRows.close();
        super.closeAll();

        return rows;
    }

    @Override
    public Funcionario getFuncionarioByCodigo(Integer codigo) throws SQLException {
        Funcionario novoFuncionario = null;
        super.preparedStatementInitialize("select * from funcionario where codigo = ?");
        super.prepared.setInt(1,codigo);
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        if (resultSetRows.next()) {
            novoFuncionario = new Funcionario(resultSetRows.getInt("codigo"),
                    resultSetRows.getString("nome"),
                    resultSetRows.getString("cpf"),
                    resultSetRows.getString("telefoneContato"),
                    resultSetRows.getString("login"),
                    resultSetRows.getString("senha"),
                    resultSetRows.getBoolean("gerente"));
        }
        resultSetRows.close();
        super.closeAll();

        return novoFuncionario;
    }

    @Override
    public Funcionario getFuncionarioByLogin(String login) throws SQLException {
        Funcionario novoFuncionario = null;
        super.preparedStatementInitialize("select * from funcionario where upper(login) = ?");
        super.prepared.setString(1,login.toUpperCase());
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        if (resultSetRows.next()) {
            novoFuncionario = new Funcionario(resultSetRows.getInt("codigo"),
                    resultSetRows.getString("nome"),
                    resultSetRows.getString("cpf"),
                    resultSetRows.getString("telefoneContato"),
                    resultSetRows.getString("login"),
                    resultSetRows.getString("senha"),
                    resultSetRows.getBoolean("gerente"));
        }
        resultSetRows.close();
        super.closeAll();

        return novoFuncionario;
    }

    @Override
    public List<Funcionario> getFuncionarioByNome(String nome) throws SQLException {
        List<Funcionario> rows = new ArrayList<>();
        super.preparedStatementInitialize("select * from funcionario where upper(nome) like ?");
        super.prepared.setString(1, "%"+nome.toUpperCase()+"%");
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        while (resultSetRows.next()) {
            rows.add(new Funcionario(resultSetRows.getInt("codigo"),
                    resultSetRows.getString("nome"),
                    resultSetRows.getString("cpf"),
                    resultSetRows.getString("telefoneContato"),
                    resultSetRows.getString("login"),
                    resultSetRows.getString("senha"),
                    resultSetRows.getBoolean("gerente")));
        }
        resultSetRows.close();
        super.closeAll();

        return rows;
    }

    @Override
    public List<Funcionario> procurarFuncionario(String texto) throws SQLException {
        List<Funcionario> rows = new ArrayList<>();
        super.preparedStatementInitialize("select * from funcionario where upper(nome) like ? or upper(login) like ?");
        super.prepared.setString(1, "%"+texto.toUpperCase()+"%");
        super.prepared.setString(2, "%"+texto.toUpperCase()+"%");
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        while (resultSetRows.next()) {
            rows.add(new Funcionario(resultSetRows.getInt("codigo"),
                    resultSetRows.getString("nome"),
                    resultSetRows.getString("cpf"),
                    resultSetRows.getString("telefoneContato"),
                    resultSetRows.getString("login"),
                    resultSetRows.getString("senha"),
                    resultSetRows.getBoolean("gerente")));
        }
        resultSetRows.close();
        super.closeAll();

        return rows;
    }
    
}
