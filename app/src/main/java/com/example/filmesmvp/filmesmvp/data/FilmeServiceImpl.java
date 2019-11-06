package com.example.filmesmvp.filmesmvp.data;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.filmesmvp.filmesmvp.data.model.FilmeResultadoBusca;
import com.example.filmesmvp.filmesmvp.filmeDetalhes.model.FilmeDetalhes;
import com.example.filmesmvp.filmesmvp.filmes.FilmeFragment;
import com.example.filmesmvp.filmesmvp.filmes.MainActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
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

    @Override
    public void getFilmePesquisa(String nomeFilme, final FilmeServiceCallBack<FilmeResultadoBusca> callback) {
        Call<FilmeResultadoBusca> callFilme = mRetrofit.busca(nomeFilme.trim(), "json", "movie");
        callFilme.enqueue(new Callback<FilmeResultadoBusca>(){
            public void onResponse(Call<FilmeResultadoBusca> call, Response<FilmeResultadoBusca> response){
                   try {
                       if(response.code()==200){
                           FilmeResultadoBusca resultadoBusca = response.body();
                           callback.onLoaded(resultadoBusca);
                       }
                   }catch (Exception e){
                       e.printStackTrace();
                   }
            }
            public void onFailure(Call<FilmeResultadoBusca> call, Throwable t){
            }
        });
    }
}
