package br.edu.ifsul.pokemao.model;

import java.time.LocalDateTime;

/**
 * Esta classe existe como classe mãe das classes {@code Batalha} e
 * {@code Troca}, ou seja, ela serve para indicar os dois treinadores que
 * participarão de algum desses eventos.
 * <p>
 * Os atributos {@code usuarioInicial} e {@code usuarioEscolhido} armazenam a
 * informação dos dois treinadores que irão participar dos eventos, sendo o
 * primeiro o que representa o usuário que está acessando o programa no momento.
 * {@code usuarioEscolhido} usa os dados de algum outro usuário guardado no
 * banco de dados.
 */
public class Acontecimento {
    private long id;
    private LocalDateTime data;
    private Treinador usuarioInicial;
    private Treinador usuarioEscolhido;

    public Acontecimento(Treinador usuarioInicial, Treinador usuarioEscolhido) {
        this.data = LocalDateTime.now();
        this.usuarioInicial = usuarioInicial;
        this.usuarioEscolhido = usuarioEscolhido;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Treinador getTreinadorInicial() {
        return usuarioInicial;
    }

    public void setTreinadorInicial(Treinador usuarioInicial) {
        this.usuarioInicial = usuarioInicial;
    }

    public Treinador getTreinadorEscolhido() {
        return usuarioEscolhido;
    }

    public void setTreinadorEscolhido(Treinador usuarioEscolhido) {
        this.usuarioEscolhido = usuarioEscolhido;
    }

}
