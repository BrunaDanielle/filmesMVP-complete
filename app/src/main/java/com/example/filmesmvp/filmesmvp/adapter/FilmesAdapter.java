package com.example.filmesmvp.filmesmvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmesmvp.R;
import com.example.filmesmvp.filmesmvp.data.model.Filme;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FilmesAdapter extends RecyclerView.Adapter<FilmesAdapter.ViewHolder> {

    private List<Filme> mFilmes;
    private ItemListener mItemListener;

    public FilmesAdapter(List<Filme> filmes, ItemListener itemListener){
        setList(filmes);
        mItemListener = itemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView = inflater.inflate(R.layout.filme_item, parent, false);

        return new ViewHolder(noteView, mItemListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Filme filme = mFilmes.get(position);

        Picasso.with(holder.thumbnail.getContext())
                .load(filme.posterUrl)
                .fit().centerCrop()
                .placeholder(R.drawable.ic_insert_photo_black_48px)
                .into(holder.thumbnail);

        holder.titulo.setText(filme.titulo);
    }

    public void replaceData(List<Filme> notes) {
        setList(notes);
        notifyDataSetChanged();
    }

    private void setList(List<Filme> notes) {
        mFilmes = notes;
    }

    @Override
    public int getItemCount() {
        return mFilmes.size();
    }

    public Filme getItem(int position) {
        return mFilmes.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView thumbnail;
        public TextView titulo;
        private ItemListener mItemListener;

        public ViewHolder(View itemView, ItemListener listener) {
            super(itemView);
            mItemListener = listener;
            titulo = itemView.findViewById(R.id.filme_titulo);
            thumbnail = itemView.findViewById(R.id.filme_thumb);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Filme filme = getItem(position);
            mItemListener.onFilmeClick(filme);
        }
    }
}

