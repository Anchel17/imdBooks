package com.example.imdmarket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdmarket.R;
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CadastrarLoginActivity : AppCompatActivity() {

    var listaLogins = mutableListOf<LoginDTO>();

    lateinit var loginText: EditText;
    lateinit var passwordText: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_login);

        var btnCadastrarLogin = findViewById<Button>(R.id.cadastrar_login_btn);
        var btnLimparLogin = findViewById<Button>(R.id.limpar_cadastro_btn);

        carregarListaLogins();

        btnCadastrarLogin.setOnClickListener({
            if (!loginJaCadastrado() && isAllCamposValidos()) {
                cadastrarLogin();
                irParaMainActivity();
            }
        })

        btnLimparLogin.setOnClickListener({
            limparCampos();
        })
    }

    private fun cadastrarLogin(){
        var login = findViewById<EditText>(R.id.cadastrar_login_field).text.toString();
        var password = findViewById<EditText>(R.id.cadastrar_senha_field).text.toString();

        listaLogins.add(LoginDTO(login, password));

        salvarSharedPreferences();
    }

    private fun salvarSharedPreferences(){
        val sharedPreferences = this.getSharedPreferences("LOGIN_PREFERENCES", Context.MODE_PRIVATE);
        val editor = sharedPreferences.edit();
        val gson = Gson();

        val json = gson.toJson(listaLogins);
        editor.putString("LOGIN_PREFERENCES", json);
        editor.apply();

        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();
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

    private fun loginJaCadastrado(): Boolean{
        loginText = findViewById<EditText>(R.id.cadastrar_login_field);
        passwordText = findViewById<EditText>(R.id.cadastrar_senha_field);

        for(login: LoginDTO in listaLogins){
            if(login.login == loginText.text.toString()){
                Toast.makeText(this, "Já existe usuário cadastrado com esse login.", Toast.LENGTH_LONG).show();
                return true;
            }
        }

        return false;
    }

    private fun isAllCamposValidos(): Boolean{
        var loginFieldValido = isFieldValido(R.id.cadastrar_login_field);
        var senhaFieldValido = isFieldValido(R.id.cadastrar_senha_field);

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
        findViewById<EditText>(R.id.cadastrar_login_field).text.clear();
        findViewById<EditText>(R.id.cadastrar_senha_field).text.clear();
    }

    private fun irParaMainActivity(){
        val telaMainActivity = Intent(this, MainActivity::class.java);
        startActivity(telaMainActivity);
    }
}