package com.namhv.firebase;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.namhv.firebase.databinding.ActivityDetailBinding;
import com.namhv.firebase.model.Movie;
import com.namhv.firebase.model.MovieStore;
import com.namhv.firebase.utils.DateUtils;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";
    private ActivityDetailBinding binding;

    public static Intent buildIntent(final Context context, final int movieId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);
        final Movie movie = MovieStore.getAllMovies().get(movieId);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        binding.setMovie(movie);
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.title.getText().toString();
                String genre = binding.genre.getText().toString();
                String publicationDateString = binding.publicationDate.getText().toString();
                movie.setTitle(title);
                movie.setGenre(genre);
                movie.setPublicationDate(DateUtils.parseDate(publicationDateString));

                // Log FireBase
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, title);
                bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, genre);
                bundle.putString("ItemDate", publicationDateString);
                FirebaseAnalytics.getInstance(DetailActivity.this).logEvent("EditMovie", bundle);

                finish();
            }
        });
    }
}
