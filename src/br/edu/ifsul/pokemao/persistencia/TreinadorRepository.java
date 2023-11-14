package br.edu.ifsul.pokemao.persistencia;

import br.edu.ifsul.pokemao.model.Treinador;

public class TreinadorRepository {
    // métodos para login, cadastro, logout
    // conexao com o banco de dados

    Treinador treinadorLogado;

    public Treinador getTreinadorLogado() {
        return treinadorLogado;
    }

    public void setTreinadorLogado(Treinador treinadorLogado) {
        this.treinadorLogado = treinadorLogado;
    }
}
