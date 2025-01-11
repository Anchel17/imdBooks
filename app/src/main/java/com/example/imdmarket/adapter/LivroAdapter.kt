package com.example.imdmarket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imdmarket.Livro
import com.example.imdmarket.R

class LivroAdapter(private val livros:List<Livro>, private val onClickListener: (Livro) -> Unit)
    : RecyclerView.Adapter<LivroViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context);

        return LivroViewHolder(layoutInflater.inflate(R.layout.item_livro, parent, false));
    }

    override fun onBindViewHolder(holder: LivroViewHolder, position: Int) {
        val item = livros[position];
        holder.render(item, onClickListener);
    }

    override fun getItemCount(): Int {
        return livros.size;
    }
}