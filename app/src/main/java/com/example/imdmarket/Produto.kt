package com.example.imdmarket

class Produto (var codigoProduto: String, var nomeProduto: String, var descricaoProduto: String, var estoque: Int) {
    override fun toString(): String {
        return "Código do Produto: $codigoProduto\nNome do Produto: $nomeProduto" +
                "\nDescrição do Produto: $descricaoProduto\nEstoque: $estoque";
    }
}