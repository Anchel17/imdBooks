package com.example.imdmarket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.imdmarket.R;


class MainActivity : ComponentActivity() {

    var listaLogins = mutableListOf<LoginDTO>();

    lateinit var loginText: EditText;
    lateinit var passwordText: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        carregarListaLogins();

        var btnEntrar = findViewById<Button>(R.id.btn_entrar);
        var btnCriarConta = findViewById<Button>(R.id.btn_criar_conta);
        var btnEsqueciSenha = findViewById<TextView>(R.id.btn_esqueci_senha);

        btnEntrar.setOnClickListener({
            if(isLoginAndPasswordValid()) {
                irParaTelaDeMenu();
            }
            else{
                showLoginInvalidoToast();
            }
        })

        btnCriarConta.setOnClickListener({
            irParaTelaDeCadastrarLogin();
        })

        btnEsqueciSenha.setOnClickListener({
           irParaTelaDeEsqueciSenha();
        });
    }

    private fun isLoginAndPasswordValid():Boolean{
        loginText = findViewById<EditText>(R.id.login_field);
        passwordText = findViewById<EditText>(R.id.password_field);

        for(login: LoginDTO in listaLogins){
            if(login.login == loginText.text.toString()
            && login.senha == passwordText.text.toString()){
                return true;
            }
        }

        return false;
    }

    private fun carregarListaLogins(){
        val sharedPreferences = this.getSharedPreferences("LOGIN_PREFERENCES", Context.MODE_PRIVATE);
        val gson = Gson();
        val json = sharedPreferences.getString("LOGIN_PREFERENCES", null);
        val type = object : TypeToken<MutableList<LoginDTO>>() {}.type;

        if(json.isNullOrEmpty()){
            listaLogins = ArrayList<LoginDTO>();
            return;
        }

        listaLogins = gson.fromJson<MutableList<LoginDTO>>(json, type);
    }

    private fun showLoginInvalidoToast(){
        Toast.makeText(this, "Login/Senha incorreto!", Toast.LENGTH_LONG).show();
    }

    private fun irParaTelaDeMenu(){
        val telaMenu = Intent(this, MenuActivity::class.java);
        startActivity(telaMenu);
    }

    private fun irParaTelaDeCadastrarLogin(){
        val telaCadastrarLogin = Intent(this, CadastrarLoginActivity::class.java);
        startActivity(telaCadastrarLogin);
    }

    private fun irParaTelaDeEsqueciSenha(){
        val telaEsqueciSenha = Intent(this, EsqueciSenhaActivity::class.java);
        startActivity(telaEsqueciSenha);
    }
}