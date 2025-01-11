package com.example.imdmarket

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class LivroParcelize(
    var isbn: String,
    var tituloLivro: String,
    var autorLivro: String,
    var editoraLivro: String,
    var descricaoLivro: String,
    var urlImageLivro: String
):Parcelable