package com.example.filmesmvp.filmesmvp.data;

import com.example.filmesmvp.filmesmvp.data.model.FilmeResultadoBusca;
import com.example.filmesmvp.filmesmvp.filmeDetalhes.model.FilmeDetalhes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitEndpoint {
    @GET("./?&apikey=e5f3720f")
    Call<FilmeResultadoBusca> busca(@Query("s") String q, @Query("r") String format);

    @GET("./?apikey=e5f3720f")
    Call<FilmeDetalhes> buscaDetalhes(@Query("i") String q, @Query("r") String format);
}
