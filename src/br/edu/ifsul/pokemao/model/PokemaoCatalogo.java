package br.edu.ifsul.pokemao.model;

import java.awt.Color;

/**
 * Esta classe gera os pokemaos que poderão se capturados pelos usuários e após
 * isso usados em trocas e batalhas.
 * <p>
 * {@code id} serve para que cada pokemao seja único no sistema, fazendo que não
 * existam dois pokemaos iguais;
 * <p>
 * Os atríbutos {@code nome}, {@code raridade}, {@code foto} e {@code descricao}
 * são características que servem para informar ao usuário que pokemao ele
 * capturou.
 * <p>
 * {@code ataque}, {@code defesa} e {@code velocidade} são atríbutos usados
 * durante as batalhas.
 */
public class PokemaoCatalogo {
    private long id;

    private String emoji;
    private String nome;

    private int ataque;
    private int defesa;
    private int velocidade;
    private int raridade;

    private String foto;
    private String descricao;

    public PokemaoCatalogo(long id, String emoji, String nome, int ataque, int defesa, int raridade, String foto,
            String descricao) {
        this.id = id;
        this.emoji = emoji;
        this.nome = nome;
        this.ataque = ataque;
        this.defesa = defesa;
        this.velocidade = 20;
        this.raridade = raridade;
        this.foto = foto;
        this.descricao = descricao;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    public int getRaridade() {
        return raridade;
    }

    public String getRaridadeString() {
        switch (this.raridade) {
            case 1:
                return "Comum";
            case 2:
                return "Raro";
            case 3:
                return "Lendário";
        }
        return this.raridade + "";
    }

    public Color getRaridadeColor() {
        switch (this.raridade) {
            case 1:
                return new Color(211, 211, 211);
            case 2:
                return new Color(173, 216, 230);
            case 3:
                return new Color(144, 238, 144);
        }
        return null;
    }

    public void setRaridade(int raridade) {
        this.raridade = raridade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}