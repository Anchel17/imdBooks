package com.example.imdmarket

class Livro (var isbn: String, var tituloLivro: String, var autorLivro: String, var editoraLivro: String,
             var descricaoLivro: String, var urlImageLivro: String) {
    override fun toString(): String {
        return "ISBN do livro: $isbn\nTítulo do Livro: $tituloLivro" +
                "\nAutor do Livro: $autorLivro\nEditora do Livro: $editoraLivro" +
                "\nDescrição do Livro: $descricaoLivro\nURL da imagem: $urlImageLivro";
    }
}