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

class AlterarActivity : AppCompatActivity() {

    var listaLivros = mutableListOf<Livro>();

    lateinit var isbnLivro: EditText;
    lateinit var tituloLivro: EditText;
    lateinit var autorLivro: EditText;
    lateinit var editoraLivro: EditText;
    lateinit var descricaoLivro: EditText;
    lateinit var urlImageLivro: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alterar)

        var btnSaveAlterar = findViewById<Button>(R.id.btnSaveAlteracao);
        listaLivros = carregarListaLivros();

        btnSaveAlterar.setOnClickListener({
            if(isIsbnLivroValido()) {
                salvarLivro();
                irParaTelaDeMenu();
            }
            else{
                showInvalidFieldsToast();
            }
        })
    }

    private fun isIsbnLivroValido(): Boolean{
        var isbnLivroValido = isFieldValido(R.id.update_isbn_livro_field);

        return isbnLivroValido && existeLivroComMesmoCodigo(R.id.update_isbn_livro_field);
    }

    private fun salvarLivro(){
        isbnLivro = findViewById<EditText>(R.id.update_isbn_livro_field);
        tituloLivro = findViewById<EditText>(R.id.update_titulo_livro_field);
        autorLivro = findViewById<EditText>(R.id.update_autor_livro_field);
        editoraLivro = findViewById<EditText>(R.id.update_editora_livro_field);
        descricaoLivro = findViewById<EditText>(R.id.update_desc_livro_field);
        urlImageLivro = findViewById<EditText>(R.id.update_urlimage_field);


        var livro = Livro(isbnLivro.text.toString(), tituloLivro.text.toString(),
            autorLivro.text.toString(), editoraLivro.text.toString(),
            descricaoLivro.text.toString(), urlImageLivro.text.toString());

        listaLivros.forEach {
            if(it.isbn == isbnLivro.text.toString()){
                it.isbn = livro.isbn;
                it.tituloLivro = livro.tituloLivro;
                it.autorLivro = livro.autorLivro;
                it.editoraLivro = livro.editoraLivro;
                it.descricaoLivro = livro.descricaoLivro;
                it.urlImageLivro = livro.urlImageLivro;
            }
        }

        Toast.makeText(this, "Livro Alterado com sucesso.", Toast.LENGTH_LONG).show();
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

    private fun existeLivroComMesmoCodigo(idField: Int): Boolean{
        var isbn = findViewById<EditText>(idField);

        var livroJaCadastrado = listaLivros.find {
                livro: Livro -> livro.isbn == isbn.text.toString() }

        if(livroJaCadastrado != null){
            return true;
        }

        Toast.makeText(this, "Nenhum Livro cadastrado com esse código.", Toast.LENGTH_LONG).show();
        return false;
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