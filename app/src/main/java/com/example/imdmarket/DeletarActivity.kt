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

class DeletarActivity : AppCompatActivity() {

    var listaProdutos = mutableListOf<Produto>();

    lateinit var codigoProduto: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deletar)

        var btnDeletar = findViewById<Button>(R.id.btnDeletarSave);
        listaProdutos = carregarListaProdutos();

        btnDeletar.setOnClickListener({
            if(isCodigoProdutoValido() &&
                existeProdutoParaDeletar(R.id.delete_cod_produto_field)) {
                deletarProduto(R.id.delete_cod_produto_field);
                irParaTelaDeMenu();
            }
            else{
                showInvalidFieldsToast();
            }
        })
    }

    private fun isCodigoProdutoValido(): Boolean{
        var field = findViewById<EditText>(R.id.delete_cod_produto_field);

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

    private fun existeProdutoParaDeletar(idField: Int): Boolean{
        var codigo = findViewById<EditText>(idField);

        var produtoCadastrado = listaProdutos.find {
                produto: Produto -> produto.codigoProduto == codigo.text.toString() }

        if(produtoCadastrado != null){
            return true;
        }

        Toast.makeText(this, "Nenhum produto cadastrado com esse código.", Toast.LENGTH_LONG).show();
        return false;
    }

    private fun showInvalidFieldsToast(){
        Toast.makeText(this, "Preencha os campos obrigatórios.", Toast.LENGTH_LONG).show();
    }

    private fun deletarProduto(idField: Int){
        var codigo = findViewById<EditText>(idField);
        var index = 0;

        for(produto: Produto in listaProdutos){
            if(produto.codigoProduto == codigo.text.toString()){
                break;
            }

            index++;
        }

        Toast.makeText(this, "Produto deletado com sucesso.", Toast.LENGTH_LONG).show();
        listaProdutos.removeAt(index);
        salvarSharedPreferences();
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