package com.example.filmesmvp.filmesmvp.filmes;
//irá implementar a classe contract para capturar interações do usuario, e terá API e Contract cm variaveis
//locais para alimentar a view.

import com.example.filmesmvp.filmesmvp.data.FilmeServiceAPI;
import com.example.filmesmvp.filmesmvp.data.model.Filme;
import com.example.filmesmvp.filmesmvp.data.model.FilmeResultadoBusca;
import com.example.filmesmvp.filmesmvp.filmeDetalhes.model.FilmeDetalhes;

import java.io.IOException;
import java.util.logging.Handler;

public class FilmesPresenter implements FilmesContract.UserActionsListener {
    private final FilmeServiceAPI mApi;
    private final FilmesContract.View mFilmesView;


    public FilmesPresenter(FilmeServiceAPI api, FilmesContract.View filmesView){
        mApi = api;
        mFilmesView = filmesView;
    }

        @Override
    public void carregarFilmes(String nomeFilme) {
        mFilmesView.setCarregando(true);

        if(nomeFilme == null){
            nomeFilme = "Hannibal";
        }
        mApi.getFilmePesquisa(nomeFilme, new FilmeServiceAPI.FilmeServiceCallBack<FilmeResultadoBusca>() {
            @Override
            public void onLoaded(FilmeResultadoBusca resultadoBusca){
                mFilmesView.setCarregando(false);
                if(resultadoBusca.filmes == null){
                    mFilmesView.mostraErro();
                }else {
                    mFilmesView.exibirFilmes(resultadoBusca.filmes);
                }
            }
        });
    }


    @Override
    public void abrirDetalhes(Filme filme) {
        mApi.getFilme(filme.id, new FilmeServiceAPI.FilmeServiceCallBack<FilmeDetalhes>(){
            @Override
            public void onLoaded(FilmeDetalhes filme) {
                mFilmesView.exibirDetalhesUI(filme);
            }
        });
    }
}
