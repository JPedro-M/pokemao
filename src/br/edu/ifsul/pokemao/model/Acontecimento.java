package br.edu.ifsul.pokemao.model;
import java.time.LocalDateTime;

public class Acontecimento {
    private LocalDateTime data;
    private Treinador usuarioInicial;
    private Treinador usuarioPassivo;

    public Acontecimento(Treinador usuarioInicial, Treinador usuarioPassivo) {
        this.data = LocalDateTime.now();
        this.usuarioInicial = usuarioInicial;
        this.usuarioPassivo = usuarioPassivo;
    }

    public LocalDateTime getData() {
        return data;
    }

    public Treinador getUsuarioInicial() {
        return usuarioInicial;
    }

    public Treinador getUsuarioPassivo() {
        return usuarioPassivo;
    }
}
