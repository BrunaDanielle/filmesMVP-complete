package com.example.filmesmvp.filmesmvp.filmeDetalhes.model;

import com.google.gson.annotations.SerializedName;

public class FilmeDetalhesBusca {

    @SerializedName("Search")
    public FilmeDetalhes detalhes;

    public FilmeDetalhesBusca(){
    }

    public FilmeDetalhesBusca(FilmeDetalhes detalhes){
        this.detalhes = detalhes;
    }
}
