package com.apps.anant.mocial.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.apps.anant.mocial.Apis.Api;
import com.apps.anant.mocial.Models.Movies;
import com.apps.anant.mocial.Models.movieResponse;
import com.apps.anant.mocial.R;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Movie extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {

    public Spinner spn_movie,  spn_genre;
    Button btn_next;
    SharedPreferences sharedPreferences;
    String[] movie_name;
    ConstraintLayout cl_movie;
    LottieAnimationView movies,fav,plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        spn_genre = (Spinner) findViewById(R.id.spinner_genre);
        spn_movie = (Spinner) findViewById(R.id.spinner_movie);
        btn_next = (Button) findViewById(R.id.button_next);
        cl_movie = (ConstraintLayout) findViewById(R.id.cl_movie);
        movies = (LottieAnimationView) findViewById(R.id.lottieAnimationProceed);
        fav = (LottieAnimationView) findViewById(R.id.lottieAnimationFavourite);
        plan = (LottieAnimationView) findViewById(R.id.lottieAnimationPlans);

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(),"font/arista_regulartrial.ttf");
        btn_next.setTypeface(myCustomFont);

        spn_genre.setOnItemSelectedListener(this);
        String[] genres = new String[]{"Genre","Action", "Romance", "Sci-fi","Comedy"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, genres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_genre.setAdapter(adapter);

        movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent genreIntent = new Intent(Movie.this,SelectGenre.class);
                Movie.this.startActivity(genreIntent);
            }
        });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reviewIntent = new Intent(Movie.this,Favourite.class);
                Movie.this.startActivity(reviewIntent);
            }
        });

        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reviewIntent = new Intent(Movie.this,Plans.class);
                Movie.this.startActivity(reviewIntent);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String genre= spn_genre.getSelectedItem().toString();
                String movie = spn_movie.getSelectedItem().toString();

                sharedPreferences= getApplicationContext().getSharedPreferences("MyData", 0); // 0 - for private mode
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Movie",movie); // Storing boolean - true/false
                editor.putString("Genre",genre);
                editor.commit();

                Intent reviewIntent = new Intent(Movie.this,Review.class);
                Movie.this.startActivity(reviewIntent);

            }
        });


    }

    public void movie_spinner() {

        spn_movie.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,movie_name);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_movie.setAdapter(adapter);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Object item = adapterView.getItemAtPosition(i);

        ((TextView) view).setTextSize(18);

        if (i == 0 && adapterView.getId() == R.id.spinner_genre) {
            // Set the hint text color gray
            ((TextView) view).setTextColor(Color.GRAY);
        }
        if (adapterView.getId() == R.id.spinner_genre) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getResources().getString(R.string.BaseUrl))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Api api = retrofit.create(Api.class);


            String json = "{\n" +
                    "\t\"genre\": \"" + item + "\"\n" +
                    "}";

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

            api.fetchMovies(requestBody).enqueue(new Callback<movieResponse>() {
                @Override
                public void onResponse(Call<movieResponse> call, Response<movieResponse> response) {
                    movieResponse res = response.body();

                    Log.d("Movie Activity", response.body().toString());

                    boolean err = res.isError();
                    List<Movies> mov = res.getMovies();

                    if (err == true) {
                        String out = res.getOutput();
                        Snackbar snackbar = Snackbar.make(cl_movie, out, 2000);
                        snackbar.show();
                    }
                    if (err == false) {
                        String out = res.getOutput();
                        Snackbar snackbar = Snackbar.make(cl_movie, out, 2000);
                        snackbar.show();
                        movie_name = new String[mov.size()];

                        for (int i = 0; i < mov.size(); i++) {
                            movie_name[i] = mov.get(i).getMname();
                            Log.d("The college ", "Movie name is : " + movie_name[i]);
                        }
                        movie_spinner();
                    }
                }

                @Override
                public void onFailure(Call<movieResponse> call, Throwable t) {
                    Snackbar snackbar = Snackbar.make(cl_movie, "Internal Server Error !!", 2000);
                    snackbar.show();
                }
            });

        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
