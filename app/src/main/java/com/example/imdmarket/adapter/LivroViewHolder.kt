package com.example.imdmarket.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.imdmarket.Livro
import com.example.imdmarket.R

class LivroViewHolder(view: View): ViewHolder(view){

    var imageLivro = view.findViewById<ImageView>(R.id.image_livro);
    var tituloLivro = view.findViewById<TextView>(R.id.titulo_livro);
    var autorLivro = view.findViewById<TextView>(R.id.autor_livro);
    var editoraLivro = view.findViewById<TextView>(R.id.editora_livro);
    var descricaoLivro = view.findViewById<TextView>(R.id.desc_livro);
    var isbnLivro = view.findViewById<TextView>(R.id.isbn_livro);

    fun render(livro: Livro){
        Glide.with(imageLivro.context).load(livro.urlImageLivro).into(imageLivro);
        tituloLivro.text = livro.tituloLivro;
        autorLivro.text = livro.autorLivro;
        editoraLivro.text = livro.editoraLivro;
        descricaoLivro.text = livro.descricaoLivro;
        isbnLivro.text = livro.isbn;
    }
}