package br.edu.ifsul.pokemao.model;

import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import java.util.Random;
import java.time.LocalDateTime;

public class PokemaoTreinador {
    public Pokemao pokemao;
    public long id;

    public Treinador treinador;
    public int velocidadeAtaque;
    public int ataque;
    public int defesa;
    public int hp;
    public boolean disponivelParaTroca;
    public double xp;
    public LocalDateTime dataCaptura;
    

    public PokemaoTreinador(Pokemao pokemao, Treinador treinador) {
        this.id = new PokemaoTreinadorRepository().getLenPokemaoTreinador() + 1;
        this.pokemao = pokemao;
        this.treinador = treinador;
        this.velocidadeAtaque = pokemao.getVelocidade() + new Random().nextInt(0, 15);
        this.ataque = pokemao.getAtaque() + new Random().nextInt(-5, 5);
        this.defesa = pokemao.getDefesa() + new Random().nextInt(-5, 5);
        this.hp = pokemao.getHp() + new Random().nextInt(-5, 5);
        this.xp = 100;
        this.dataCaptura = LocalDateTime.now();
        this.disponivelParaTroca = false;
    }

    public PokemaoTreinador(long id, Pokemao pokemao, Treinador treinador, int velocidadeAtaque, int ataque, int defesa, int hp, boolean disponivelParaTroca, double xp, LocalDateTime dataCaptura) {
        this.id = id;
        this.pokemao = pokemao;
        this.treinador = treinador;
        this.velocidadeAtaque = velocidadeAtaque;
        this.ataque = ataque;
        this.defesa = defesa;
        this.hp = hp;
        this.disponivelParaTroca = disponivelParaTroca;
        this.xp = xp;
        this.dataCaptura = dataCaptura;
    }

    public Pokemao getPokemao() {
        return pokemao;
    }

    public void setPokemao(Pokemao pokemao) {
        this.pokemao = pokemao;
    }

    public Treinador getTreinador() {
        return treinador;
    }

    public void setTreinador(Treinador treinador) {
        this.treinador = treinador;
    }

    public int getVelocidadeAtaque() {
        return velocidadeAtaque;
    }

    public void setVelocidadeAtaque(int velocidadeAtaque) {
        this.velocidadeAtaque = velocidadeAtaque;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void fullHp() {
        this.hp = pokemao.getHp();
    }

    public double getXp() {
        return xp;
    }

    public void setXp(double xp) {
        this.xp = xp;
    }

    public void addXp(double xp) {
        this.xp += xp;
    }

    public LocalDateTime getDataCaptura() {
        return dataCaptura;
    }

    public void setDataCaptura(LocalDateTime dataCaptura) {
        this.dataCaptura = dataCaptura;
    }

    public boolean isDisponivelParaTroca() {
        return disponivelParaTroca;
    }

    public void setDisponivelParaTroca(boolean disponivelParaTroca) {
        this.disponivelParaTroca = disponivelParaTroca;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
