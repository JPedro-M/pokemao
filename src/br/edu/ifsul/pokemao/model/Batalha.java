package br.edu.ifsul.pokemao.model;

/*
 * A classe Batalha faz com que o pokemao do usuário atual batalhe contra algum pokemao 
 * de outro usuário que está armazenado no banco.
 * 
 * pokemaoInicial corresponde ao pokemao do usuario e pokemaoEscolhido é um do pokemaos 
 * armazenados no banco correspondentes a outro usuário 
 * 
 * 
 */

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
