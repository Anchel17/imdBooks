package com.example.imdmarket

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnCadastrar = findViewById<Button>(R.id.btnCadastrar);
        val btnAlterar = findViewById<Button>(R.id.btnAlterar);
        val btnDeletar = findViewById<Button>(R.id.btnDeletar);
        val btnListar = findViewById<Button>(R.id.btnListar);

        btnCadastrar.setOnClickListener({
           irParaTelaDeCadastrar();
        });

        btnAlterar.setOnClickListener({
            irParaTelaDeAlterar();
        })

        btnDeletar.setOnClickListener({
            irParaTelaDeDeletar();
        })

        btnListar.setOnClickListener({
            irParaTelaDeListar();
        })
    }

    private fun irParaTelaDeCadastrar(){
        val telaCadastrar = Intent(this, CadastrarActivity::class.java);
        startActivity(telaCadastrar);
    }

    private fun irParaTelaDeAlterar(){
        val telaAlterar = Intent(this, AlterarActivity::class.java);
        startActivity(telaAlterar);
    }

    private fun irParaTelaDeDeletar(){
        val telaDeletar = Intent(this, DeletarActivity::class.java);
        startActivity(telaDeletar);
    }

    private fun irParaTelaDeListar(){
        val telaListar = Intent(this, ListarProdutosActivity::class.java)
        startActivity(telaListar);
    }
}