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

class EsqueciSenhaActivity : AppCompatActivity() {

    var listaLogins = mutableListOf<LoginDTO>();

    lateinit var loginText: EditText;
    lateinit var passwordText: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_esqueci_senha);

        var btnAlterarLogin = findViewById<Button>(R.id.alterar_senha_btn);
        var btnLimpar = findViewById<Button>(R.id.esqueci_limpar_btn);

        carregarListaLogins();

        btnAlterarLogin.setOnClickListener({
           if(loginJaCadastrado() && isAllCamposValidos()){
               alterarLogin();
               irParaMainActivity();
           }
        });

        btnLimpar.setOnClickListener({
           limparCampos();
        });
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

    private fun alterarLogin(){
        var loginField = findViewById<EditText>(R.id.esqueci_login_field).text.toString();
        var passwordField = findViewById<EditText>(R.id.esqueci_senha_field).text.toString();

        for(login: LoginDTO in listaLogins){
            if(login.login == loginField){
                login.senha = passwordField;
                break;
            }
        }

        salvarSharedPreferences();
    }

    private fun salvarSharedPreferences(){
        val sharedPreferences = this.getSharedPreferences("LOGIN_PREFERENCES", Context.MODE_PRIVATE);
        val editor = sharedPreferences.edit();
        val gson = Gson();

        val json = gson.toJson(listaLogins);
        editor.putString("LOGIN_PREFERENCES", json);
        editor.apply();

        Toast.makeText(this, "Alteração de senha realizada com sucesso!", Toast.LENGTH_LONG).show();
    }

    private fun loginJaCadastrado(): Boolean{
        loginText = findViewById<EditText>(R.id.esqueci_login_field);
        passwordText = findViewById<EditText>(R.id.esqueci_senha_field);

        for(login: LoginDTO in listaLogins){
            if(login.login == loginText.text.toString()){
                return true;
            }
        }

        Toast.makeText(this, "Nenhum usuário cadastrado com o login fornecido.", Toast.LENGTH_LONG).show();
        return false;
    }

    private fun isAllCamposValidos(): Boolean{
        var loginFieldValido = isFieldValido(R.id.esqueci_login_field);
        var senhaFieldValido = isFieldValido(R.id.esqueci_senha_field);

        return loginFieldValido && senhaFieldValido;
    }

    private fun isFieldValido(idField: Int): Boolean{
        var field = findViewById<EditText>(idField);

        if(field.text.toString().isNotEmpty()){
            return true;
        }

        field.setError("Campo obrigatório")
        return false;
    }
    private fun limparCampos(){
        findViewById<EditText>(R.id.esqueci_login_field).text.clear();
        findViewById<EditText>(R.id.esqueci_senha_field).text.clear();
    }

    private fun irParaMainActivity(){
        val telaMainActivity = Intent(this, MainActivity::class.java);
        startActivity(telaMainActivity);
    }

}