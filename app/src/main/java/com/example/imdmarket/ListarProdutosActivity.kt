package com.example.imdmarket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListarProdutosActivity : AppCompatActivity() {
    var listaProdutos = mutableListOf<Produto>();
    lateinit var listViewProdutos: ListView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_produtos)

        val voltarBtn = findViewById<Button>(R.id.voltarBtn);

        listaProdutos = carregarListaProdutos();
        listViewProdutos = findViewById<ListView>(R.id.lista_produtos);

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,
            listaProdutos.map { "Código: ${it.codigoProduto}\n" +
                    "Nome: ${it.nomeProduto}\n" +
                    "Descrição: ${it.descricaoProduto}\n" +
                    "Estoque: ${it.estoque}\n"});

        listViewProdutos.adapter = adapter;

        voltarBtn.setOnClickListener({
            irParaTelaDeMenu();
        })
    }

    private fun carregarListaProdutos(): MutableList<Produto>{
        val sharedPreferences = this.getSharedPreferences("produtosPreference", Context.MODE_PRIVATE);
        val gson = Gson();
        val json = sharedPreferences.getString("produtos", null);

        val type = object : TypeToken<MutableList<Produto>>() {}.type;

        if(json.isNullOrEmpty()){
            return ArrayList<Produto>();
        }

        return gson.fromJson(json, type);
    }

    private fun irParaTelaDeMenu(){
        val telaMenu = Intent(this, MenuActivity::class.java);
        startActivity(telaMenu);
    }
}