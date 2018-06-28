package com.example.saurav.popularmovies;

import android.app.AlertDialog;
import android.app.LoaderManager;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.saurav.popularmovies.adapter.MoviesAdapter;
import com.example.saurav.popularmovies.model.Movies;
import com.example.saurav.popularmovies.utils.QueryUtils;

import java.util.List;
/**
 * Created by Saurav on 6/27/2018.
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movies>>, MoviesAdapter.ListItemClickListener {

    private List<Movies> listOfMovies;
    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private static final int MOVIE_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        getLoaderManager().initLoader(1, null, MainActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_popular) {
            QueryUtils.filter = 0;
            getLoaderManager().restartLoader(1, null, MainActivity.this);
        }
        if (id == R.id.action_rated) {
            QueryUtils.filter = 1;
            getLoaderManager().restartLoader(1, null, MainActivity.this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Movies>> onCreateLoader(int i, Bundle bundle) {
        return new MoviePosterLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<List<Movies>> loader, List<Movies> moviesList) {
        if (moviesList != null) {
            this.listOfMovies = moviesList;
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 2);
            recyclerView.setLayoutManager(mLayoutManager);
            moviesAdapter = new MoviesAdapter(moviesList,MainActivity.this,MainActivity.this);
            recyclerView.setAdapter(moviesAdapter);


            }
    }

    @Override
    public void onLoaderReset(Loader<List<Movies>> loader) {

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("clicked_movie", listOfMovies.get(clickedItemIndex));
        startActivity(intent);
    }

}


