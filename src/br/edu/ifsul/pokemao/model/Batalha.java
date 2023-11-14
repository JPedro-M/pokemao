package br.edu.ifsul.pokemao.model;

public class Batalha extends Acontecimento {
    private boolean vencedor;
    // true significa que o usuarioInicial venceu
    
    public Batalha(Treinador usuarioInicial, Treinador usuarioPassivo) {
        super(usuarioInicial, usuarioPassivo);
    }

    public boolean isInicialVencedor() {
        return vencedor;
    }
    
    public void setInicialStatus(boolean vencedor) {
        this.vencedor = vencedor;
    }

}
