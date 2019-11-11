package com.example.filmesmvp.filmesmvp.data;


import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.filmesmvp.filmesmvp.data.model.FilmeResultadoBusca;
import com.example.filmesmvp.filmesmvp.filmeDetalhes.model.FilmeDetalhes;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

//classe concreta (FilmeServiceImpl.java), que irá “assinar” o contrato proposto pela FilmeServiceAPI.

public class FilmeServiceImpl implements FilmeServiceAPI {
    //RetrofitEndpoint mRetrofit;
    private final OkHttpClient client = new OkHttpClient();




    public FilmeServiceImpl(){
       // mRetrofit = RetrofitClient.getClient().create(RetrofitEndpoint.class);
    }

    @Override
    public void getFilme(String filmeId, final FilmeServiceCallBack<FilmeDetalhes> callback) {
        Request request = new Request.Builder()
                .url("http://www.omdbapi.com/?i=" + filmeId + "&apikey=e5f3720f")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call call, Response response) {
                try(ResponseBody responseBody = response.body()) {

                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    if(response.code() == 200){
                        Gson gson = new Gson();
                        FilmeDetalhes entity = gson.fromJson(responseBody.string(), FilmeDetalhes.class);
                        callback.onLoaded(entity);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getFilmePesquisa(String nomeFilme, final FilmeServiceCallBack<FilmeResultadoBusca> callback) {

            Request request = new Request.Builder()
                    .url("http://www.omdbapi.com/?s=" + nomeFilme.trim() + "&apikey=e5f3720f")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                   // e.printStackTrace();
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call call, Response response) {
                    try(ResponseBody responseBody = response.body()) {

                        if (!response.isSuccessful())
                            throw new IOException("Unexpected code " + response);

                        Headers responseHeaders = response.headers();
                        for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                        }

                        try {
                        if(response.code() == 200) {
                            Gson gson = new Gson();
                            final FilmeResultadoBusca entity = gson.fromJson(responseBody.string(), FilmeResultadoBusca.class);
                            callback.onLoaded(entity);
                        }
                        }catch (Exception e){
                           e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
}
