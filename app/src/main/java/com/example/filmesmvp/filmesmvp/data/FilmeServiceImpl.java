package com.example.filmesmvp.filmesmvp.data;


import com.example.filmesmvp.filmesmvp.data.model.Filme;
import com.example.filmesmvp.filmesmvp.data.model.FilmeResultadoBusca;

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
    public void getFilme(String filmeId, final FilmeServiceCallBack<Filme> callback) {
      Call<Filme> callFilme = mRetrofit.buscaDetalhes(filmeId, "json");
        callFilme.enqueue(new Callback<Filme>() {
            @Override
            public void onResponse(Call<Filme> call, Response<Filme> response) {
                if(response.code() ==200){
                    Filme filme = response.body();
                    callback.onLoaded(filme);
                }
            }

            @Override
            public void onFailure(Call<Filme> call, Throwable t) {
            } });
    }
}
