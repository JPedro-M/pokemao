package br.edu.ifsul.pokemao.model;

public class Treinador {
    // nome, user, senha idade, moedas

    private String nome;
    private String user;
    private String senha;
    private int idade;
    private int moedas;

    public Treinador(String user, String senha, String nome, int idade) {
        this.user = user;
        this.senha = senha;
        this.nome = nome;
        this.idade = idade;
        this.moedas = 500;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getMoedas() {
        return moedas;
    }

    public void setMoedas(int moedas) {
        this.moedas = moedas;
    }

    public void addMoedas(int moedas) {
        this.moedas += moedas;
    }

}
