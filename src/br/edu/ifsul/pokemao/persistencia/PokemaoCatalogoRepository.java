package br.edu.ifsul.pokemao.persistencia;

public class PokemaoCatalogoRepository {
    // métodos para listar, cadastrar, editar, excluir
    // conexao com o banco de dados
    
    // o método de exclusão também deve excluir os pokémaos de treinadores

    private ConexaoMySQL conexao;

    public PokemaoCatalogoRepository() {
        this.conexao = new ConexaoMySQL("localhost", "3306" "root", "root", "pokemao");
    }

}
