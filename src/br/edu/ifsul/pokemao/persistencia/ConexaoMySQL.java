package br.edu.ifsul.pokemao.persistencia;

public class ConexaoMySQL {
    private String host;
    private String porta;
    private String usuario;
    private String senha;
    private String banco;

    public ConexaoMySQL(String host, String porta, String usuario, String senha, String banco) {
        this.host = host;
        this.porta = porta;
        this.usuario = usuario;
        this.senha = senha;
        this.banco = banco;
    }

    public String getHost() {
        return host;
    }

    public String getPorta() {
        return porta;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    public String getBanco() {
        return banco;
    }
}
