package com.example.filmesmvp.filmesmvp.data;
//contrato da API que será responsável por fornecer os dados utilizados no aplicativo

import com.example.filmesmvp.filmesmvp.data.model.Filme;
import com.example.filmesmvp.filmesmvp.data.model.FilmeResultadoBusca;

public interface FilmeServiceAPI {

    interface FilmeServiceCallBack<T>{

        void onLoaded(T filmes);
    }

    void getFilmes(FilmeServiceCallBack<FilmeResultadoBusca> callBack);

    void getFilme(String filmeId, FilmeServiceCallBack<Filme> callback);
}
