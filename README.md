Colecione e treine criaturas feitas de emojis. Interaja com outros treinadores em todo mundo através de batalhas e trocas.

![Status](https://img.shields.io/badge/Status-Em_desenvolvimento-yellow)

## Sobre o projeto
O projeto foi desenvolvido para a disciplina de Programação Orientada a Objetos, do curso de Análise e Desenvolvimento de Sistemas do IFSul Sapucaia do Sul, com o objetivo de colocar em prática os conceitos aprendidos em aula.

## Implementação
O armazenamento de dados foi migrado de MySQL para arquivos JSON locais. Os repositórios agora usam um utilitário simples (`JsonDB`) para ler/escrever objetos em arquivos no diretório `data/` criado automaticamente na raiz do projeto.

Arquivos de dados utilizados:
- `data/treinadores.json`
- `data/pokemaos.json`
- `data/pokemaos_treinadores.json`
- `data/trocas.json`
- `data/batalhas.json`

Cada arquivo contém um array de objetos JSON. Relações são representadas por IDs (ex.: `id_treinador`, `id_pokemao_catalogo`).

## Dependências
Os JARs necessários já estão em `lib/`. Não é necessário configurar banco de dados.

Observação: As classes antigas de conexão JDBC foram mantidas apenas como legado, mas não são utilizadas na execução atual.

## Como rodar
Compile os fontes Java para a pasta `bin/` e execute a aplicação a partir da classe `Main` (UI Swing). Os arquivos JSON serão criados em `data/` conforme o uso dos repositórios.