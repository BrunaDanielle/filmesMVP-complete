package com.example.filmesmvp.filmesmvp.filmes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.filmesmvp.R;
import com.example.filmesmvp.filmesmvp.data.FilmeServiceImpl;
import com.example.filmesmvp.filmesmvp.data.model.Filme;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//exibe a lista de filmes com recycler view e cardview

public class FilmeFragment extends Fragment implements FilmesContract.View {
    private FilmesContract.UserActionsListener mActionsListener;

    private FilmesAdapter mListAdapter;

    public FilmeFragment(){
    }

    public static FilmeFragment newInstance(){
        return  new FilmeFragment();
    }

    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mListAdapter = new FilmesAdapter(new ArrayList<Filme>(0), mItemListener);
        mActionsListener = new FilmesPresenter(new FilmeServiceImpl(), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mActionsListener.carregarFilmes();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_filme, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.filmes_list);
        recyclerView.setAdapter(mListAdapter);

        int numColumns = 1;

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numColumns));

        SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mActionsListener.carregarFilmes();
            }
        });

        return root;
    }

    @Override
    public void setCarregando(final boolean isAtivo) {
        if(getView() ==  null){
            return;
        }

        final SwipeRefreshLayout srl = getView().findViewById(R.id.refresh_layout);

        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(isAtivo);
            }
        });
    }

    @Override
    public void exibirFilmes(List<Filme> filme) {
        mListAdapter.replaceData(filme);
    }

    @Override
    public void exibirDetalhesUI(String filmeId) {
        Toast.makeText(getContext(), filmeId, Toast.LENGTH_LONG).show();
    }

    ItemListener mItemListener = new ItemListener(){
        @Override
        public void onFilmeClick(Filme filme) {
            mActionsListener.abrirDetalhes(filme);
        }
    };

    private static class FilmesAdapter extends RecyclerView.Adapter<FilmesAdapter.ViewHolder> {

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
                titulo = (TextView) itemView.findViewById(R.id.filme_titulo);
                thumbnail = (ImageView) itemView.findViewById(R.id.filme_thumbnail);
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
    public interface ItemListener {
        void onFilmeClick(Filme clickedNote);
    }
}
