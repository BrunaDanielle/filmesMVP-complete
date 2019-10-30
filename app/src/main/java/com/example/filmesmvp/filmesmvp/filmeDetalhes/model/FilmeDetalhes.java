package com.example.filmesmvp.filmesmvp.filmeDetalhes.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FilmeDetalhes {

    @SerializedName("Title")
    public String title;

    @SerializedName("Year")
    public int year;

    @SerializedName("Rated")
    public String rated;

    @SerializedName("Released")
    public String released;

    @SerializedName("Runtime")
    public String runtime;

    @SerializedName("Genre")
    public String genre;

    @SerializedName("Director")
    public String director;

    @SerializedName("Writer")
    public String writer;

    @SerializedName("Actors")
    public String actors;

    @SerializedName("Plot")
    public String plot;

    @SerializedName("Language")
    public String language;

    @SerializedName("Country")
    public String country;

    @SerializedName("Awards")
    public String awards;

    @SerializedName("Poster")
    public String poster;

    @SerializedName("Ratings")
    public List<Ratings> ratings;

    @SerializedName("Metascore")
    public int metascore;

    @SerializedName("imdbRating")
    public Double imdbRating;

    @SerializedName("imdbVotes")
    public String imdbVotes;

    @SerializedName("imdbID")
    public String imdbID;

    @SerializedName("Type")
    public String type;

    @SerializedName("DVD")
    public String dVD;

    @SerializedName("BoxOffice")
    public String boxOffice;

     @SerializedName("Production")
    public String production;

    @SerializedName("Website")
    public String website;

    @SerializedName("Response")
    public boolean response;

    public FilmeDetalhes(){
    }

    public FilmeDetalhes(String title, int year, String rated, String released, String runtime,
                         String genre, String director, String writer, String actors, String plot,
                         String language, String country, String awards, String poster, List<Ratings> ratings,
                         int metascore, Double imdbRating, String imdbVotes, String imdbID, String type, String dVD,
                         String boxOffice, String production, String website, boolean response) {

        this.title = title;
        this.year = year;
        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.genre = genre;
        this.director = director;
        this.writer = writer;
        this.actors = actors;
        this.plot = plot;
        this.language = language;
        this.country = country;
        this.awards = awards;
        this.poster = poster;
        this.ratings = ratings;
        this.metascore = metascore;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
        this.imdbID = imdbID;
        this.type = type;
        this.dVD = dVD;
        this.boxOffice = boxOffice;
        this.production = production;
        this.website = website;
        this.response = response;
    }
}
