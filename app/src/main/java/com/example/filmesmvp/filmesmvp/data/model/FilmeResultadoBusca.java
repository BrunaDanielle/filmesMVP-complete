package com.example.filmesmvp.filmesmvp.data.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

//recebe o resultado da busca dos filmes
public class FilmeResultadoBusca {

    @SerializedName("Search")
    public List<Filme> filmes;

    public FilmeResultadoBusca(){}

    public FilmeResultadoBusca(List<Filme> filmes){
        this.filmes = filmes;
    }
}