package com.example.imdmarket

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imdmarket.adapter.LivroAdapter
import com.example.imdmarket.banco.BancoLivros

class ListarLivrosActivity : AppCompatActivity() {
    var listaLivros = mutableListOf<Livro>();
    lateinit var banco: BancoLivros;
    lateinit var recyclerViewLivros: RecyclerView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_livros)

        val voltarBtn = findViewById<Button>(R.id.voltarBtn);

        banco = BancoLivros(this);
        listaLivros = carregarListaLivros();

        initRecyclerView();

        voltarBtn.setOnClickListener({
            irParaTelaDeMenu();
        })
    }

    private fun carregarListaLivros(): MutableList<Livro>{
        return banco.findAll();
    }

    private fun initRecyclerView(){
        recyclerViewLivros = findViewById<RecyclerView>(R.id.livros_recyclerview);
        recyclerViewLivros.layoutManager = LinearLayoutManager(this);
        recyclerViewLivros.adapter = LivroAdapter(listaLivros)
    }

    private fun irParaTelaDeMenu(){
        val telaMenu = Intent(this, MenuActivity::class.java);
        startActivity(telaMenu);
    }
}