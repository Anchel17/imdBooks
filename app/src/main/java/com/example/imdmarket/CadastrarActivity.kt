package com.example.imdmarket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CadastrarActivity : AppCompatActivity() {

    var listaProdutos = mutableListOf<Produto>();

    lateinit var codigoProduto: EditText;
    lateinit var nomeProduto: EditText;
    lateinit var descProduto: EditText;
    lateinit var estoqueProduto: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cadastrar)

        var btnSalvar = findViewById<Button>(R.id.btnSaveAlteracao);
        listaProdutos = carregarListaProdutos();

        btnSalvar.setOnClickListener({
            if(isAllCamposValidos()) {
                salvarProduto();
                irParaTelaDeMenu();
            }
            else{
                showInvalidFieldsToast();
            }
        })
    }

    private fun isAllCamposValidos(): Boolean{
        var codigoProdutoValido = isFieldValido(R.id.create_cod_produto_field) &&
                !existeProdutoComMesmoCodigo(R.id.create_cod_produto_field);
        var nomeProdutoValido = isFieldValido(R.id.create_nome_prod_field);
        var descProdutoValido = isFieldValido(R.id.create_desc_prod_field);
        var estoqueProdutoValido = isFieldValido(R.id.create_estq_field);

        return  codigoProdutoValido && nomeProdutoValido
            &&  descProdutoValido && estoqueProdutoValido;
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

    private fun salvarProduto(){
        codigoProduto = findViewById<EditText>(R.id.create_cod_produto_field);
        nomeProduto = findViewById<EditText>(R.id.create_nome_prod_field);
        descProduto = findViewById<EditText>(R.id.create_desc_prod_field);
        estoqueProduto = findViewById<EditText>(R.id.create_estq_field);

        var produto = Produto(codigoProduto.text.toString(), nomeProduto.text.toString(),
                            descProduto.text.toString(), estoqueProduto.text.toString().toInt());


        listaProdutos.add(produto);
        Toast.makeText(this, "Produto cadastrado com sucesso.", Toast.LENGTH_LONG).show();
        salvarSharedPreferences();
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

    private fun salvarSharedPreferences(){
        val sharedPreferences = this.getSharedPreferences("produtosPreference", Context.MODE_PRIVATE);
        val editor = sharedPreferences.edit();
        val gson = Gson();

        val json = gson.toJson(listaProdutos);
        editor.putString("produtos", json);
        editor.apply();
    }

    private fun existeProdutoComMesmoCodigo(idField: Int): Boolean{
        var codigo = findViewById<EditText>(idField);

        var produtoJaCadastrado = listaProdutos.find {
            produto: Produto -> produto.codigoProduto == codigo.text.toString() }

        if(produtoJaCadastrado != null){
            Toast.makeText(this, "Já existe produto cadastrado com esse código.", Toast.LENGTH_LONG).show();
            return true;
        }

        return false;
    }

    private fun irParaTelaDeMenu(){
        val telaMenu = Intent(this, MenuActivity::class.java);
        startActivity(telaMenu);
    }
}