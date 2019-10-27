package com.example.filmesmvp.filmesmvp.filmes;
//irá implementar a classe contract para capturar interações do usuario, e terá API e Contract cm variaveis
//locais para alimentar a view.

import android.util.Log;
import android.widget.Toast;

import com.example.filmesmvp.filmesmvp.data.FilmeServiceAPI;
import com.example.filmesmvp.filmesmvp.data.model.Filme;
import com.example.filmesmvp.filmesmvp.data.model.FilmeResultadoBusca;

public class FilmesPresenter implements FilmesContract.UserActionsListener {
    private final FilmeServiceAPI mApi;
    private final FilmesContract.View mFilmesView;

    public FilmesPresenter(FilmeServiceAPI api, FilmesContract.View filmesView){
        mApi = api;
        mFilmesView = filmesView;
    }

    @Override
    public void carregarFilmes() {
        mFilmesView.setCarregando(true);

        mApi.getFilmes(new FilmeServiceAPI.FilmeServiceCallBack<FilmeResultadoBusca>() {
            @Override
            public void onLoaded(FilmeResultadoBusca resultadoBusca) {
                mFilmesView.setCarregando(false);
                mFilmesView.exibirFilmes(resultadoBusca.filmes);
            }
        });
    }

    @Override
    public void abrirDetalhes(final Filme filme) {
        Log.i("detelhes", filme.id);
        mApi.getFilme(filme.id, new FilmeServiceAPI.FilmeServiceCallBack<Filme>(){
            @Override
            public void onLoaded(Filme filme) {
                mFilmesView.exibirDetalhesUI(filme.id);
            }
        });
    }
}
