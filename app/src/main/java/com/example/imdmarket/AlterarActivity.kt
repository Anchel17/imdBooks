package com.example.imdmarket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdmarket.banco.BancoLivros
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AlterarActivity : AppCompatActivity() {

    var listaLivros = mutableListOf<Livro>();

    lateinit var banco: BancoLivros;

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
        var btnLimpar = findViewById<Button>(R.id.btnLimpar);

        banco = BancoLivros(this);
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

        btnLimpar.setOnClickListener({
           limparCampos();
        });
    }

    private fun isIsbnLivroValido(): Boolean{
        var isbnLivroValido = isFieldValido(R.id.update_isbn_livro_field);

        return isbnLivroValido && existeLivroComMesmoIsbn(R.id.update_isbn_livro_field);
    }

    private fun salvarLivro(){
        isbnLivro = findViewById<EditText>(R.id.update_isbn_livro_field);
        tituloLivro = findViewById<EditText>(R.id.update_titulo_livro_field);
        autorLivro = findViewById<EditText>(R.id.update_autor_livro_field);
        editoraLivro = findViewById<EditText>(R.id.update_editora_livro_field);
        descricaoLivro = findViewById<EditText>(R.id.update_desc_livro_field);
        urlImageLivro = findViewById<EditText>(R.id.update_urlimage_field);

        banco.update(isbnLivro.text.toString(), tituloLivro.text.toString(),
            autorLivro.text.toString(), editoraLivro.text.toString(),
            descricaoLivro.text.toString(), urlImageLivro.text.toString());

        listaLivros.clear();
        listaLivros = banco.findAll();

        Toast.makeText(this, "Livro Alterado com sucesso.", Toast.LENGTH_LONG).show();
    }

    private fun limparCampos(){
        findViewById<EditText>(R.id.update_isbn_livro_field).text.clear();
        findViewById<EditText>(R.id.update_titulo_livro_field).text.clear();
        findViewById<EditText>(R.id.update_autor_livro_field).text.clear();
        findViewById<EditText>(R.id.update_editora_livro_field).text.clear();
        findViewById<EditText>(R.id.update_desc_livro_field).text.clear();
        findViewById<EditText>(R.id.update_urlimage_field).text.clear();
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
        return banco.findAll();
    }

    private fun existeLivroComMesmoIsbn(idField: Int): Boolean{
        var isbn = findViewById<EditText>(idField);

        var livroJaCadastrado = banco.findByIsbn(isbn.text.toString());

        if(livroJaCadastrado.isbn.isEmpty()){
            Toast.makeText(this, "livro com ISBN não encontrado.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private fun irParaTelaDeMenu(){
        val telaMenu = Intent(this, MenuActivity::class.java);
        startActivity(telaMenu);
    }
}