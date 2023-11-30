package br.edu.ifsul.pokemao.persistencia;

import br.edu.ifsul.pokemao.utils.BDConfigs;
import br.edu.ifsul.pokemao.utils.ConexaoMySQL;

public class AcontecimentoRepository {
    // métodos para adicionar acontecimentos: trocas e batalhas
    // métodos para listar acontecimentos de um treinador ou todos em um periodo de tempo
    private ConexaoMySQL conexao;

    public AcontecimentoRepository() {
        this.conexao = new ConexaoMySQL(BDConfigs.IP, BDConfigs.PORTA, BDConfigs.USUARIO, BDConfigs.SENHA, BDConfigs.NOME_BD);
    }

    public void adicionarTroca() {
        
    }
}
