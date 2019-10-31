package com.example.filmesmvp.filmesmvp.filmes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.filmesmvp.R;
import com.example.filmesmvp.filmesmvp.filmeDetalhes.model.FilmeDetalhes;
import com.squareup.picasso.Picasso;

public class FilmeDetalhesActivity extends AppCompatActivity {

    private ImageView poster;
    private ImageView backdrop;
    private TextView title;
    private TextView releaseDate;
    private TextView awards;
    private TextView overview;
    private TextView nameActors;
    private RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme_detalhes);

        backdrop = findViewById(R.id.filme_thumb);
        poster = findViewById(R.id.movie_poster);
        title = findViewById(R.id.movie_title);
        nameActors = findViewById(R.id.actors);
        awards = findViewById(R.id.awards);
        rating = findViewById(R.id.movie_rating);
        releaseDate = findViewById(R.id.movie_release_date);
        overview = findViewById(R.id.movie_overview);

        Intent i = getIntent();
        Bundle extras = i.getExtras();

        if(extras!= null){
            populateDetails(extras);
        } else {
            finish();
        }
    }

    private void populateDetails(Bundle extras) {

        Picasso.with(this)
                .load(extras.getString("Poster"))
                .fit().centerCrop()
                .into(poster);

        Picasso.with(this)
                .load(extras.getString("Poster"))
                .fit().centerCrop()
                .into(backdrop);

        releaseDate.setText(extras.getString("DateRelease"));
        overview.setText(extras.getString("Overview"));
        title.setText(extras.getString("Title"));
        awards.setText(extras.getString("Awards"));
        nameActors.setText(extras.getString("Actors"));
        rating.setRating(extras.getFloat("Ratings", 0f));
    }

}
