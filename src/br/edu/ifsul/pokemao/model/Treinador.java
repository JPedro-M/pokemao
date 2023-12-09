package br.edu.ifsul.pokemao.model;

import java.time.LocalDateTime;

/**
 * Esta classe é a abstração que representa o usuário permitindo que este possa
 * interagir com o resto do programa.
 * <p>
 * Os atríbutos {@code id}, {@code nome}, {@code user} e {@code senha} identificam o usuário. 
 * <p>
 * {@code nascimento} indica a idade do usuaŕio.
 * {@code moedas} indica quantas moedas estão associadas a conta do usuário.
 */
public class Treinador {
    private long id;

    private String nome;
    private String user;
    private String senha;
    private LocalDateTime nascimento;
    private int moedas;

    public Treinador(String user, String senha, String nome, LocalDateTime nascimento) {
        this.user = user;
        this.senha = senha;
        this.nome = nome;
        this.nascimento = nascimento;
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

    public LocalDateTime getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDateTime nascimento) {
        this.nascimento = nascimento;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
