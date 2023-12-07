package br.edu.ifsul.pokemao.model;

public class Batalha extends Acontecimento {
    private boolean vencedor;
    
    private PokemaoTreinador pokemaoInicial;
    private PokemaoTreinador pokemaoEscolhido;
    // true significa que o pokemaoInicial venceu
    
    public Batalha(PokemaoTreinador pokemaoInicial, PokemaoTreinador pokemaoEscolhido) {
        super(pokemaoInicial.getTreinador(), pokemaoEscolhido.getTreinador());
        this.pokemaoInicial = pokemaoInicial;
        this.pokemaoEscolhido = pokemaoEscolhido;
    }

    public PokemaoTreinador getPokemaoInicial() {
        return pokemaoInicial;
    }

    public PokemaoTreinador getPokemaoEscolhido() {
        return pokemaoEscolhido;
    }

    public boolean isInicialVencedor() {
        return vencedor;
    }
    
    public void setInicialStatus(boolean vencedor) {
        this.vencedor = vencedor;
    }

    public void setVencedor(boolean vencedor) {
        this.vencedor = vencedor;
    }

    public void setVencedor(PokemaoTreinador vencedor) {
        if (vencedor.equals(pokemaoInicial)) {
            this.vencedor = true;
        } else {
            this.vencedor = false;
        }
    }

    public PokemaoTreinador getVencedor() {
        if (vencedor) {
            return pokemaoInicial;
        } else {
            return pokemaoEscolhido;
        }
    }

}
