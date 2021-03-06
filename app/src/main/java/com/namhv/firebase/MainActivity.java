package com.namhv.firebase;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.namhv.firebase.databinding.ActivityMainBinding;
import com.namhv.firebase.model.Movie;
import com.namhv.firebase.model.MovieStore;
import com.namhv.firebase.utils.ModifyInBackgroundTask;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_setting) {
            Bundle bundle = new Bundle();
            bundle.putString("MenuItem", "ModifyMovieDescription");
            FirebaseAnalytics.getInstance(this).logEvent("MenuClick", bundle);
            new ModifyInBackgroundTask().execute(MovieStore.getAllMovies().toArray(new Movie[0]));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initRecyclerView();
    }

    private void initRecyclerView() {
        final RecyclerView list = binding.list;
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        final ListAdapter adapter = new ListAdapter(MovieStore.getAllMovies());
        adapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int id, Movie movie) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, movie.getTitle());
                bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, movie.getGenre());
                FirebaseAnalytics.getInstance(MainActivity.this).logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
                startActivity(DetailActivity.buildIntent(getApplicationContext(), id));
            }
        });
        list.setAdapter(adapter);
    }
}
