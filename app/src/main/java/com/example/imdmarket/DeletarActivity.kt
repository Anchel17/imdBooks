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

class DeletarActivity : AppCompatActivity() {

    var listaLivros = mutableListOf<Livro>();

    lateinit var isbnLivro: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deletar)

        var btnDeletar = findViewById<Button>(R.id.btnDeletarSave);
        listaLivros = carregarListaLivros();

        btnDeletar.setOnClickListener({
            if(isIsbnLivroValido() &&
                existeLivroParaDeletar(R.id.delete_isbn_livro_field)) {
                deletarLivro(R.id.delete_isbn_livro_field);
                irParaTelaDeMenu();
            }
            else{
                showInvalidFieldsToast();
            }
        })
    }

    private fun isIsbnLivroValido(): Boolean{
        var field = findViewById<EditText>(R.id.delete_isbn_livro_field);

        if(field.text.toString().isNotEmpty()){
            return true;
        }

        field.setError("Campo obrigatório")
        return false;
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

    private fun existeLivroParaDeletar(idField: Int): Boolean{
        var isbn = findViewById<EditText>(idField);

        var livroCadastrado = listaLivros.find {
                livro: Livro -> livro.isbn == isbn.text.toString() }

        if(livroCadastrado != null){
            return true;
        }

        Toast.makeText(this, "Nenhum Livro cadastrado com esse código.", Toast.LENGTH_LONG).show();
        return false;
    }

    private fun showInvalidFieldsToast(){
        Toast.makeText(this, "Preencha os campos obrigatórios.", Toast.LENGTH_LONG).show();
    }

    private fun deletarLivro(idField: Int){
        var isbn = findViewById<EditText>(idField);
        var index = 0;

        for(livro: Livro in listaLivros){
            if(livro.isbn == isbn.text.toString()){
                break;
            }

            index++;
        }

        Toast.makeText(this, "Livro deletado com sucesso.", Toast.LENGTH_LONG).show();
        listaLivros.removeAt(index);
        salvarSharedPreferences();
    }

    private fun salvarSharedPreferences(){
        val sharedPreferences = this.getSharedPreferences("livrosPreference", Context.MODE_PRIVATE);
        val editor = sharedPreferences.edit();
        val gson = Gson();

        val json = gson.toJson(listaLivros);
        editor.putString("livros", json);
        editor.apply();
    }

    private fun irParaTelaDeMenu(){
        val telaMenu = Intent(this, MenuActivity::class.java);
        startActivity(telaMenu);
    }
}