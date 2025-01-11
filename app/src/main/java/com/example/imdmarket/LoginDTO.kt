package com.example.imdmarket

class LoginDTO(var login: String, var senha: String) {
    override fun toString(): String {
        return "Login: $login\nSenha: $senha";
    }
}