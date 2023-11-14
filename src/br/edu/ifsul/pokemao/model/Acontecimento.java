package br.edu.ifsul.pokemao.model;
import java.time.LocalDateTime;

public class Acontecimento {
    // data, usuarioInicial, usuarioPassivo

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

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Treinador getUsuarioInicial() {
        return usuarioInicial;
    }

    public void setUsuarioInicial(Treinador usuarioInicial) {
        this.usuarioInicial = usuarioInicial;
    }

    public Treinador getUsuarioPassivo() {
        return usuarioPassivo;
    }

    public void setUsuarioPassivo(Treinador usuarioPassivo) {
        this.usuarioPassivo = usuarioPassivo;
    }

}
