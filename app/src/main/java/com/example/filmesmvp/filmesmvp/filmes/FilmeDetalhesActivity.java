package com.example.filmesmvp.filmesmvp.filmes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filmesmvp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.squareup.picasso.Picasso;

public class FilmeDetalhesActivity extends AppCompatActivity {

    private ImageView poster;
    private ImageView backdrop;
    private TextView title;
    private TextView releaseDate;
    private TextView awards;
    private TextView overview;
    private TextView nameActors;
    private TextView source;
    private TextView value;
    private TextView boxOffice;
    private TextView votes;
    private RatingBar rating;
    private ActionBar ab;

    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme_detalhes);

        initView();

        ab = getSupportActionBar();
        if(ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
        }

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);

        Intent i = getIntent();
        Bundle extras = i.getExtras();

        if (extras != null) {
            populateDetails(extras);
            fetchColor();
        } else {
            finish();
        }
    }

    private void initView() {
        backdrop = findViewById(R.id.filme_thumb);
        poster = findViewById(R.id.movie_poster);
        title = findViewById(R.id.movie_title);
        nameActors = findViewById(R.id.actors);
        awards = findViewById(R.id.awards);
        source = findViewById(R.id.source);
        value = findViewById(R.id.value);
        votes = findViewById(R.id.votesImdb);
        boxOffice = findViewById(R.id.boxOffice);
        rating = findViewById(R.id.movie_rating);
        releaseDate = findViewById(R.id.movie_release_date);
        overview = findViewById(R.id.movie_overview);
    }

    private void fetchColor() {
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, (task) -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(FilmeDetalhesActivity.this, "Fetch and activate succeeded",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FilmeDetalhesActivity.this, "Fetch failed",
                                Toast.LENGTH_SHORT).show();
                    }
                    displayColor();
                });
    }

    private void displayColor() {
        String fontColor = mFirebaseRemoteConfig.getString("font_color");
        overview.setTextColor(Color.parseColor(fontColor));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
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
        ab.setTitle(extras.getString("Title"));

        awards.setText(extras.getString("Awards"));
        source.setText(extras.getString("Source"));
        value.setText(extras.getString("Value"));
        votes.setText(extras.getString("Votes"));
        String bOffice = extras.getString("BoxOffice");
        if (bOffice != null) {
            boxOffice.setText(bOffice);
        } else {
            boxOffice.setText("N/A");
        }
        nameActors.setText(extras.getString("Actors"));
        rating.setRating((float) extras.getDouble("Ratings") / 2);

        LayerDrawable stars = (LayerDrawable) rating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
    }

}
