package com.example.imdmarket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListarProdutosActivity : AppCompatActivity() {
    var listaLivros = mutableListOf<Livro>();
    lateinit var listViewLivros: ListView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_produtos)

        val voltarBtn = findViewById<Button>(R.id.voltarBtn);

        listaLivros = carregarListaLivros();
        listViewLivros = findViewById<ListView>(R.id.lista_livros);

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,
            listaLivros.map { it.toString()});

        listViewLivros.adapter = adapter;

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

    private fun irParaTelaDeMenu(){
        val telaMenu = Intent(this, MenuActivity::class.java);
        startActivity(telaMenu);
    }
}