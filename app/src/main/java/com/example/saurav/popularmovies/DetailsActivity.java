package com.example.saurav.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saurav.popularmovies.model.Movies;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
/**
 * Created by Saurav on 6/27/2018.
 */
public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView PosterImage = (ImageView) findViewById(R.id.details_poster);
        TextView Title = (TextView)findViewById(R.id.detail_Tiltle);
        TextView Overview = (TextView)findViewById(R.id.detail_synopsis);
        TextView ReleaseDate = (TextView)findViewById(R.id.detail_release_date);
        TextView VoteAverage = (TextView)findViewById(R.id.details_vote);

        Movies selectedMovie = (Movies) getIntent().getSerializableExtra("clicked_movie");

        Picasso.with(DetailsActivity.this).load(selectedMovie.getPosterPath()).into(PosterImage);
        Title.setText(selectedMovie.getTitle());
        Overview.setText(selectedMovie.getOverview());
        try {
            ReleaseDate.setText(selectedMovie.getReleaseDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        VoteAverage.setText(String.valueOf(selectedMovie.getVoteAverage()));
    }
}
