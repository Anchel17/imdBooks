package com.example.imdmarket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AlterarActivity : AppCompatActivity() {

    var listaProdutos = mutableListOf<Produto>();

    lateinit var codigoProduto: EditText;
    lateinit var nomeProduto: EditText;
    lateinit var descProduto: EditText;
    lateinit var estoqueProduto: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alterar)

        var btnSaveAlterar = findViewById<Button>(R.id.btnSaveAlteracao);
        listaProdutos = carregarListaProdutos();

        btnSaveAlterar.setOnClickListener({
            if(isCodigoProdutoValido()) {
                salvarProduto();
                irParaTelaDeMenu();
            }
            else{
                showInvalidFieldsToast();
            }
        })
    }

    private fun isCodigoProdutoValido(): Boolean{
        var codigoProdutoValido = isFieldValido(R.id.update_cod_produto_field);

        return codigoProdutoValido && existeProdutoComMesmoCodigo(R.id.update_cod_produto_field);
    }

    private fun salvarProduto(){
        codigoProduto = findViewById<EditText>(R.id.update_cod_produto_field);
        nomeProduto = findViewById<EditText>(R.id.update_nome_produto_field);
        descProduto = findViewById<EditText>(R.id.update_desc_produto_field);
        estoqueProduto = findViewById<EditText>(R.id.update_estq_field);

        var produto = Produto(codigoProduto.text.toString(), nomeProduto.text.toString(),
            descProduto.text.toString(), estoqueProduto.text.toString().toInt());

        listaProdutos.forEach {
            if(it.codigoProduto == codigoProduto.text.toString()){
                it.codigoProduto = produto.codigoProduto;
                it.nomeProduto = produto.nomeProduto;
                it.descricaoProduto = produto.descricaoProduto;
                it.estoque = produto.estoque;
            }
        }

        Toast.makeText(this, "Produto Alterado com sucesso.", Toast.LENGTH_LONG).show();
        salvarSharedPreferences();
    }

    private fun showInvalidFieldsToast(){
        Toast.makeText(this, "Preencha os campos obrigatórios.", Toast.LENGTH_LONG).show();
    }

    private fun isFieldValido(idField: Int): Boolean{
        var field = findViewById<EditText>(idField);

        if(field.text.toString().isNotEmpty()){
            return true;
        }

        field.setError("Campo obrigatório")
        return false;
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

    private fun existeProdutoComMesmoCodigo(idField: Int): Boolean{
        var codigo = findViewById<EditText>(idField);

        var produtoJaCadastrado = listaProdutos.find {
                produto: Produto -> produto.codigoProduto == codigo.text.toString() }

        if(produtoJaCadastrado != null){
            return true;
        }

        Toast.makeText(this, "Nenhum produto cadastrado com esse código.", Toast.LENGTH_LONG).show();
        return false;
    }

    private fun salvarSharedPreferences(){
        val sharedPreferences = this.getSharedPreferences("produtosPreference", Context.MODE_PRIVATE);
        val editor = sharedPreferences.edit();
        val gson = Gson();

        val json = gson.toJson(listaProdutos);
        editor.putString("produtos", json);
        editor.apply();
    }

    private fun irParaTelaDeMenu(){
        val telaMenu = Intent(this, MenuActivity::class.java);
        startActivity(telaMenu);
    }
}