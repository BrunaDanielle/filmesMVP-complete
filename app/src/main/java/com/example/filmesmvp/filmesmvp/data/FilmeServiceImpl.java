package com.example.filmesmvp.filmesmvp.data;


import com.example.filmesmvp.filmesmvp.data.model.FilmeResultadoBusca;
import com.example.filmesmvp.filmesmvp.filmeDetalhes.model.FilmeDetalhes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//classe concreta (FilmeServiceImpl.java), que irá “assinar” o contrato proposto pela FilmeServiceAPI.

public class FilmeServiceImpl implements FilmeServiceAPI {
    RetrofitEndpoint mRetrofit;

    public FilmeServiceImpl(){
        mRetrofit = RetrofitClient.getClient().create(RetrofitEndpoint.class);
    }

    @Override
    public void getFilmes(final FilmeServiceCallBack<FilmeResultadoBusca> callBack) {
        Call<FilmeResultadoBusca> callFilme = mRetrofit.busca("batman", "json");
        callFilme.enqueue(new Callback<FilmeResultadoBusca>(){
            public void onResponse(Call<FilmeResultadoBusca> call, Response<FilmeResultadoBusca> response){
                if(response.code()==200){
                    FilmeResultadoBusca resultadoBusca = response.body();
                    callBack.onLoaded(resultadoBusca);
                }
            }
            public void onFailure(Call<FilmeResultadoBusca> call, Throwable t){
            }
        });
    }
    @Override
    public void getFilme(String filmeId, final FilmeServiceCallBack<FilmeDetalhes> callback) {
      Call<FilmeDetalhes> callFilme = mRetrofit.buscaDetalhes(filmeId, "json");
        callFilme.enqueue(new Callback<FilmeDetalhes>() {
            @Override
            public void onResponse(Call<FilmeDetalhes> call, Response<FilmeDetalhes> response) {
                if(response.code() ==200){
                    FilmeDetalhes filme = response.body();
                    callback.onLoaded(filme);
                }
            }

            @Override
            public void onFailure(Call<FilmeDetalhes> call, Throwable t) {
            } });
    }
}
