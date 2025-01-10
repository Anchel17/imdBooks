package com.example.imdmarket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imdmarket.Livro
import com.example.imdmarket.R

class LivroAdapter(private val livros:List<Livro>): RecyclerView.Adapter<LivroViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context);

        return LivroViewHolder(layoutInflater.inflate(R.layout.item_livro, parent, false));
    }

    override fun onBindViewHolder(holder: LivroViewHolder, position: Int) {
        val item = livros[position];
        holder.render(item);
    }

    override fun getItemCount(): Int {
        return livros.size;
    }
}