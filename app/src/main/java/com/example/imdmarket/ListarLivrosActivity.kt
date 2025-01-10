package com.example.imdmarket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imdmarket.adapter.LivroAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListarLivrosActivity : AppCompatActivity() {
    var listaLivros = mutableListOf<Livro>();
    lateinit var recyclerViewLivros: RecyclerView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_livros)

        val voltarBtn = findViewById<Button>(R.id.voltarBtn);

        listaLivros = carregarListaLivros();

        initRecyclerView();

        voltarBtn.setOnClickListener({
            irParaTelaDeMenu();
        })
    }

    private fun carregarListaLivros(): MutableList<Livro>{
        val sharedPreferences = this.getSharedPreferences("livrosPreference", Context.MODE_PRIVATE);
        val gson = Gson();
        val json = sharedPreferences.getString("livros", null);

        val type = object : TypeToken<MutableList<Livro>>() {}.type;

        if(json.isNullOrEmpty()){
            return ArrayList<Livro>();
        }

        return gson.fromJson(json, type);
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