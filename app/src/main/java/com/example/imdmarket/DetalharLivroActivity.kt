package com.example.imdmarket

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class DetalharLivroActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhar_livro);

        var livro: LivroParcelize? = intent.getParcelableExtra("LIVRO");

        carregarInformacoesDoLivro(livro);
    }

    private fun carregarInformacoesDoLivro(livro: LivroParcelize?){
        var imageLivro = findViewById<ImageView>(R.id.detalhar_livro_image);

        findViewById<TextView>(R.id.detalhar_livro_titulo).text = livro?.tituloLivro;
        Glide.with(imageLivro.context).load(livro?.urlImageLivro).into(imageLivro);
        findViewById<TextView>(R.id.detalhar_livro_autor).text = livro?.autorLivro;
        findViewById<TextView>(R.id.detalhar_livro_editora).text = livro?.editoraLivro;
        findViewById<TextView>(R.id.detalhar_livro_isbn).text = livro?.isbn;
        findViewById<TextView>(R.id.detalhar_livro_descricao).text = livro?.descricaoLivro;
    }
}