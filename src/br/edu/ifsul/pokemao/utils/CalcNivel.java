package br.edu.ifsul.pokemao.utils;

/**
 * Esta classe fornece métodos para calcular o nível de um pokemao a partir do
 * seu XP.
 * 
 * @deprecated Não foi utilizada no projeto final.
 */
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
