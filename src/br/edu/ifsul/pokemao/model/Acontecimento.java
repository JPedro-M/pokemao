package br.edu.ifsul.pokemao.model;
import java.time.LocalDateTime;

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
