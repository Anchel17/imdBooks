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

class DeletarActivity : AppCompatActivity() {
    lateinit var banco: BancoLivros;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deletar)

        var btnDeletar = findViewById<Button>(R.id.btnDeletarSave);
        banco = BancoLivros(this);

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

    private fun existeLivroParaDeletar(idField: Int): Boolean{
        var isbn = findViewById<EditText>(idField);

        var livroCadastrado = banco.findByIsbn(isbn.text.toString());

        if(livroCadastrado.isbn.isNotEmpty()){
            return true;
        }

        Toast.makeText(this, "Nenhum Livro cadastrado com esse ISBN.", Toast.LENGTH_LONG).show();
        return false;
    }

    private fun showInvalidFieldsToast(){
        Toast.makeText(this, "Preencha os campos obrigatórios.", Toast.LENGTH_LONG).show();
    }

    private fun deletarLivro(idField: Int){
        var isbn = findViewById<EditText>(idField);

        banco.delete(isbn.text.toString());

        Toast.makeText(this, "Livro deletado com sucesso.", Toast.LENGTH_LONG).show();
    }

    private fun irParaTelaDeMenu(){
        val telaMenu = Intent(this, MenuActivity::class.java);
        startActivity(telaMenu);
    }
}