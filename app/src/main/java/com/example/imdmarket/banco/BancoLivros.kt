package com.example.imdmarket.banco

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.imdmarket.Livro

class BancoLivros(contexto: Context) : SQLiteOpenHelper(contexto, NOME, null, VERSAO){
    companion object{
        private const val NOME = "dblivro"
        private const val VERSAO = 1;
    }

    override fun onCreate(db: SQLiteDatabase){
        db.execSQL(
            """
        CREATE TABLE livros (
            isbn TEXT PRIMARY KEY,
            titulo TEXT NOT NULL,
            autor TEXT NOT NULL,
            editora TEXT NOT NULL,
            descricao TEXT NOT NULL,
            imagem TEXT NOT NULL
        )
        """
        )
    }

    fun save(isbn: String, titulo: String, autor: String, editora: String, descricao: String, imagem: String){
        val banco = this.writableDatabase;
        val container = ContentValues();

        container.put("isbn", isbn);
        container.put("titulo", titulo);
        container.put("autor", autor);
        container.put("editora", editora);
        container.put("descricao", descricao)
        container.put("imagem", imagem);

        banco.insert("livros", null, container);
        banco.close();
    }

    fun update(isbn: String, titulo: String, autor: String, editora: String, descricao: String, imagem: String){
        val banco = this.writableDatabase;
        val container = ContentValues();

        container.put("isbn", isbn);
        container.put("titulo", titulo);
        container.put("autor", autor);
        container.put("editora", editora);
        container.put("descricao", descricao)
        container.put("imagem", imagem);

        banco.update("livros", container, "isbn=" + isbn, null);
        banco.close();
    }

    fun delete(isbn: String){
        val banco = this.writableDatabase;
        banco.delete("livros", "isbn=" + isbn, null);
        banco.close();
    }


    fun findByIsbn(isbn: String): Livro{
        val banco = this.readableDatabase;
        var cursor = banco.rawQuery("SELECT * FROM livros WHERE isbn=" + isbn, null);
        var livro = Livro();

        if(cursor.count > 0){
            cursor.moveToFirst();
            do {
                livro.isbn = cursor.getString(0);
                livro.tituloLivro = cursor.getString(1);
                livro.autorLivro = cursor.getString(2);
                livro.editoraLivro = cursor.getString(3);
                livro.descricaoLivro = cursor.getString(4);
                livro.urlImageLivro = cursor.getString(5);
            }while(cursor.moveToNext());
        }

        return livro;
    }

    fun findAll(): ArrayList<Livro>{
        val banco = this.readableDatabase;
        var cursor = banco.rawQuery("SELECT * FROM livros", null);
        var livros = ArrayList<Livro>();

        if(cursor.count > 0){
            cursor.moveToFirst();
            do {
                var isbn = cursor.getString(0);
                var tituloLivro = cursor.getString(1);
                var autorLivro = cursor.getString(2);
                var editoraLivro = cursor.getString(3);
                var descricaoLivro = cursor.getString(4);
                var urlImageLivro = cursor.getString(5);

                livros.add(Livro(isbn, tituloLivro, autorLivro, editoraLivro, descricaoLivro, urlImageLivro));
            }while(cursor.moveToNext());
        }

        return livros;
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}