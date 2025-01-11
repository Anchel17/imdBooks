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

class CadastrarActivity : AppCompatActivity() {

    var listaLivros = mutableListOf<Livro>();

    lateinit var banco: BancoLivros;

    lateinit var isbnLivro: EditText;
    lateinit var tituloLivro: EditText;
    lateinit var autorLivro: EditText;
    lateinit var editoraLivro: EditText;
    lateinit var descLivro: EditText;
    lateinit var urlImageLivro: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cadastrar)

        var btnSalvar = findViewById<Button>(R.id.btnSaveAlteracao);

        banco = BancoLivros(this);
        listaLivros = carregarListaLivros();

        btnSalvar.setOnClickListener({
            if(isAllCamposValidos()) {
                salvarLivro();
                irParaTelaDeMenu();
            }
            else{
                showInvalidFieldsToast();
            }
        })
    }

    private fun isAllCamposValidos(): Boolean{
        var isbnLivroValido = isFieldValido(R.id.create_isbn_livro_field) &&
                !existeLivroComMesmoIsbn(R.id.create_isbn_livro_field);
        var tituloLivroValido = isFieldValido(R.id.create_titulo_livro_field);
        var autorLivroValido = isFieldValido(R.id.create_autor_livro_field);
        var editoraLivroValido = isFieldValido(R.id.create_editora_livro_field);
        var descricaoLivroValida = isFieldValido(R.id.create_desc_livro_field);
        var urlImageLivroValida = isFieldValido(R.id.create_desc_livro_field);

        return  isbnLivroValido && tituloLivroValido
            &&  autorLivroValido && editoraLivroValido
            &&  descricaoLivroValida && urlImageLivroValida;
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

    private fun salvarLivro(){
        isbnLivro = findViewById<EditText>(R.id.create_isbn_livro_field);
        tituloLivro = findViewById<EditText>(R.id.create_titulo_livro_field);
        autorLivro = findViewById<EditText>(R.id.create_autor_livro_field);
        editoraLivro = findViewById<EditText>(R.id.create_editora_livro_field);
        descLivro = findViewById<EditText>(R.id.create_desc_livro_field);
        urlImageLivro = findViewById<EditText>(R.id.create_urlimg_livro_field);

        var livro = Livro(isbnLivro.text.toString(), tituloLivro.text.toString(),
            autorLivro.text.toString(), editoraLivro.text.toString(),
            descLivro.text.toString(), urlImageLivro.text.toString());

        banco.save(isbnLivro.text.toString(), tituloLivro.text.toString(),
            autorLivro.text.toString(), editoraLivro.text.toString(),
            descLivro.text.toString(), urlImageLivro.text.toString());

        listaLivros.add(livro);

        Toast.makeText(this, "Livro cadastrado com sucesso.", Toast.LENGTH_LONG).show();
    }

    private fun carregarListaLivros(): MutableList<Livro>{
        return banco.findAll();
    }

    private fun existeLivroComMesmoIsbn(idField: Int): Boolean{
        var isbn = findViewById<EditText>(idField);

        var livroJaCadastrado = banco.findByIsbn(isbn.text.toString());

        if(livroJaCadastrado.isbn.isNotEmpty()){
            Toast.makeText(this, "Já existe livro cadastrado com esse ISBN.", Toast.LENGTH_LONG).show();
            return true;
        }

        return false;
    }

    private fun irParaTelaDeMenu(){
        val telaMenu = Intent(this, MenuActivity::class.java);
        startActivity(telaMenu);
    }
}