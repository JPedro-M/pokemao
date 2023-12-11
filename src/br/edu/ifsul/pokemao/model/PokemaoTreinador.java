package br.edu.ifsul.pokemao.model;

import java.util.Random;
import java.time.LocalDateTime;

/**
 * Esta classe define um pokemao que foi capturado e agora está associado a
 * um treinador.
 * <p>
 * O pokemao agora associado a um treinador recebe novos valores derivados de
 * seus valores básicos originais ({@code ataque}, {@code defesa},
 * {@code velocidadeAtaque} e {@code hp}).
 * <p>
 * O atributo {@code disponivelParaTroca} é um booleano que indica se o pokemao
 * está ou não apto a ser trocado por pokemaos de outros treinadores.
 * <p>
 * {@code dataCaptura} guarda o momento em que o pokemao foi adquirido ou obtido
 * por um treinador
 * <p>
 * {@code nome} possiblita que o usuário possa trocar o nome do pokemão
 * capturado, substituindo o nome "original" da criatura por algo da preferência
 * do treinador
 */
public class PokemaoTreinador {
    private PokemaoCatalogo pokemao;
    private long id;

    private Treinador treinador;
    private int velocidadeAtaque;
    private int ataque;
    private int defesa;
    private int hp;
    private boolean disponivelParaTroca;
    private double xp;
    private LocalDateTime dataCaptura;

    private String nome;

    public PokemaoTreinador(PokemaoCatalogo pokemao, Treinador treinador) {
        this.pokemao = pokemao;
        this.treinador = treinador;
        this.velocidadeAtaque = pokemao.getVelocidade() + new Random().nextInt(5);
        this.ataque = pokemao.getAtaque() + new Random().nextInt(5);
        this.defesa = pokemao.getDefesa() + new Random().nextInt(5);
        this.hp = 100;
        this.xp = 100;
        this.dataCaptura = LocalDateTime.now();
        this.disponivelParaTroca = false;
    }

    public PokemaoTreinador(PokemaoCatalogo pokemao, Treinador treinador, int velocidadeAtaque, int ataque, int defesa, int hp,
            boolean disponivelParaTroca, double xp, LocalDateTime dataCaptura) {
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

    public PokemaoCatalogo getPokemao() {
        return pokemao;
    }

    public void setPokemao(PokemaoCatalogo pokemao) {
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
        System.out.println("HP de " + this.nome + " alterado de " + this.hp + " para " + hp);
        this.hp = hp;
    }

    public void fullHp() {
        this.hp = 100;
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

    public String getNome() {
        return nome == null ? pokemao.getNome() : nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
