package com.namhv.firebase.utils;

import android.os.AsyncTask;

import com.namhv.firebase.model.Movie;

public class ModifyInBackgroundTask extends AsyncTask<Movie, Void, Void> {
   @Override
   protected Void doInBackground(Movie... movies) {
      for (Movie movie : movies) {
         movie.setTitle(movie.getTitle() + " modified");
      }
      return null;
   }
}
