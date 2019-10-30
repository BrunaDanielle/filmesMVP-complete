package com.example.filmesmvp.filmesmvp.filmes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.filmesmvp.R;
import com.example.filmesmvp.filmesmvp.filmeDetalhes.model.FilmeDetalhes;

public class FilmeDetalhesActivity extends AppCompatActivity {

    private ImageView imPoster;
    private TextView titleMovie;
    private TextView nameActors;
    private RatingBar ratings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme_detalhes);

        imPoster = findViewById(R.id.filme_thumb);
        titleMovie = findViewById(R.id.nameMovie);
        nameActors = findViewById(R.id.nameActors);
        ratings = findViewById(R.id.movie_rating);

        Intent i = getIntent();
        Bundle extras = i.getExtras();

        if(extras!= null){
            populateDetails(extras);
        } else {
            finish();
        }
    }

    private void populateDetails(Bundle extras) {
        titleMovie.setText(extras.getString("Title"));
        nameActors.setText(extras.getString("Actors"));
        ratings.setRating(extras.getFloat("Ratings", 0f)/2);
    }

}
