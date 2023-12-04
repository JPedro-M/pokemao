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

    public void abrirConexao(String origem){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + this.ip + ":" + this.porta + "/" + this.nomeBD;
            System.out.println("Executando " + url + " a partir de " + origem);
            this.conexao = DriverManager.getConnection(url, this.usuario, this.senha);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void fecharConexao(){
        try {
            this.conexao.close();
            System.out.println("Fechando conexão com o banco de dados");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConexao() {
        return conexao;
    }
}