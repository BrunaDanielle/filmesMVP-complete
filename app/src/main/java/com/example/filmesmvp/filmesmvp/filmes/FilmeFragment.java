package com.example.filmesmvp.filmesmvp.filmes;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.filmesmvp.R;
import com.example.filmesmvp.filmesmvp.adapter.FilmesAdapter;
import com.example.filmesmvp.filmesmvp.adapter.ItemListener;
import com.example.filmesmvp.filmesmvp.data.FilmeServiceImpl;
import com.example.filmesmvp.filmesmvp.data.model.Filme;
import com.example.filmesmvp.filmesmvp.filmeDetalhes.model.FilmeDetalhes;
import java.util.ArrayList;
import java.util.List;

//exibe a lista de filmes com recycler view e cardview

public class FilmeFragment extends Fragment implements FilmesContract.View {
    private FilmesContract.UserActionsListener mActionsListener;

    private FilmesAdapter mListAdapter;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    private String querySearch;

    public FilmeFragment() {
    }

    public static FilmeFragment newInstance() {
        return new FilmeFragment();
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new FilmesAdapter(new ArrayList<Filme>(0), mItemListener);
        mActionsListener = new FilmesPresenter(new FilmeServiceImpl(), this);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mActionsListener.carregarFilmes(querySearch);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_filme, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.filmes_list);
        Toolbar mToolbar = root.findViewById(R.id.toolbar);
        recyclerView.setAdapter(mListAdapter);

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        int numColumns = 2;

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
                mActionsListener.carregarFilmes("Hannibal");
            }
        });

        return root;
    }

    @Override
    public void setCarregando(final boolean isAtivo) {
        if (getView() == null) {
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
    public void exibirFilmes(final List<Filme> filme) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mListAdapter.replaceData(filme);
            }
        });
    }

    @Override
    public void exibirDetalhesUI(FilmeDetalhes filme) {
        Intent i = new Intent(getActivity().getBaseContext(), FilmeDetalhesActivity.class);
        i.putExtra("Actors", filme.actors);
        i.putExtra("Title", filme.title);
        i.putExtra("Ratings", filme.imdbRating);
        i.putExtra("DateRelease", filme.released);
        i.putExtra("Overview", filme.plot);
        i.putExtra("Poster", filme.poster);
        i.putExtra("Awards", filme.awards);
        i.putExtra("Votes", filme.imdbVotes);

        if (filme.boxOffice != null) {
            i.putExtra("BoxOffice", filme.boxOffice);
        }

        if (filme.ratings.size() == 1) {
            i.putExtra("Source", filme.ratings.get(0).source);
            i.putExtra("Value", filme.ratings.get(0).value);
        } else {
            i.putExtra("Source", filme.ratings.get(1).source);
            i.putExtra("Value", filme.ratings.get(1).value);
        }
        getActivity().startActivity(i);
    }

    @Override
    public void mostraErro() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), getString(R.string.movieError), Toast.LENGTH_LONG).show();
            }
        });
    }

    ItemListener mItemListener = new ItemListener() {
        @Override
        public void onFilmeClick(Filme filme) {
            mActionsListener.abrirDetalhes(filme);
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);

                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    querySearch = query;
                    mActionsListener.carregarFilmes(query);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }
}
