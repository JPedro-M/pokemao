package br.edu.ifsul.pokemao.persistencia;

public class CalcNivel {
    // método para calcular o nível do pokémao a partir do seu XP
    public static int calcNivel(int xp) {
        // parte inteira da divisão por 1000
        return xp / 1000;
    }

    public static int novoXP(int xp, int nivel) {
        return xp + 100 * nivel;
    }
}
