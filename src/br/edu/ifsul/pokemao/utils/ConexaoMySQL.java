package br.edu.ifsul.pokemao.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {

    private String ip;
    private String porta;
    private String usuario;
    private String senha;
    private String nomeBD;

    private Connection conexao;

    public ConexaoMySQL(String ip, String porta, String usuario, String senha, String nomeBD) {
        this.ip = ip;
        this.porta = porta;
        this.usuario = usuario;
        this.senha = senha;
        this.nomeBD = nomeBD;
    }

    public void abrirConexao(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + this.ip + ":" + this.porta + "/" + this.nomeBD;
            System.out.println(url);
            this.conexao = DriverManager.getConnection(url, this.usuario, this.senha);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void fecharConexao(){
        try {
            this.conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConexao() {
        return conexao;
    }
}