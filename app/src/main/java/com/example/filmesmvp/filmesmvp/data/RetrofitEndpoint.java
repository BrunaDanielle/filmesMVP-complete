package com.example.filmesmvp.filmesmvp.data;

import com.example.filmesmvp.filmesmvp.data.model.Filme;
import com.example.filmesmvp.filmesmvp.data.model.FilmeResultadoBusca;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitEndpoint {
    @GET("./?&apikey=e5f3720f")
    Call<FilmeResultadoBusca> busca(@Query("s") String q, @Query("r") String format);

    @GET("./?&apikey=e5f3720f")
    Call<Filme> buscaDetalhes(@Query("t") String q, @Query("r") String format);
}
