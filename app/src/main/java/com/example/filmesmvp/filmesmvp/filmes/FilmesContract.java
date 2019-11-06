package com.example.filmesmvp.filmesmvp.filmes;

import com.example.filmesmvp.filmesmvp.data.model.Filme;
import com.example.filmesmvp.filmesmvp.filmeDetalhes.model.FilmeDetalhes;

import java.util.List;

//controla o que pode ser exibido e oq o user pode fazer
public class FilmesContract {

    interface View{
        void setCarregando(boolean isAtivo);
        void exibirFilmes(List<Filme> filme);
        void exibirDetalhesUI (FilmeDetalhes filme);
        void mostraErro();
    }

    interface UserActionsListener{
        void carregarFilmes(String nomeFilme);
        void abrirDetalhes(Filme filme);
    }
}
