package com.apps.anant.mocial.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.apps.anant.mocial.Adapter.ReviewAdapter;
import com.apps.anant.mocial.Adapter.SuggestionAdapter;
import com.apps.anant.mocial.Apis.Api;
import com.apps.anant.mocial.Models.review;
import com.apps.anant.mocial.Models.reviewDetails;
import com.apps.anant.mocial.Models.suggestionData;
import com.apps.anant.mocial.R;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserReview extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String[] details_movie;
    String[] details_review;
    String[] details_rating;
    String[] details_isAdded;
    String[] details_stars;
    private List<review> listItems;
    RecyclerView rv_review;
    TextView tv_uname;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_review);

        sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String genre= sharedPreferences.getString("Genre1", "");
        final String uname = sharedPreferences.getString("Username1", "");
        final String pname = sharedPreferences.getString("Username", "");

        Typeface myCustomFont1 = Typeface.createFromAsset(getAssets(),"font/montserratBold.ttf");

        rv_review = (RecyclerView) findViewById(R.id.recyclerView_review);
        tv_uname= (TextView) findViewById(R.id.textView_username);

        tv_uname.setText(uname);
        tv_uname.setTypeface(myCustomFont1);

        final Context context = rv_review.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.task_layout_falldown);

        rv_review.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        String json = "{\n" +
                "\t\"uname\": \"" + uname+ "\",\n" +
                "\t\"pname\": \"" + pname+ "\",\n" +
                "\t\"genre\": \"" + genre + "\"\n" +
                "}";

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json);

        api.userReviews(requestBody).enqueue(new Callback<reviewDetails>() {
            @Override
            public void onResponse(Call<reviewDetails> call, Response<reviewDetails> response) {


                reviewDetails res = response.body();

                boolean err = res.isError();
                List<review> data = res.getData();

                if (err == false){
                    details_movie= new String[data.size()];
                    details_rating= new String[data.size()];
                    details_stars= new String[data.size()];
                    details_review= new String[data.size()];
                    details_isAdded = new String[data.size()];

                    for (int i=0;i<data.size();i++) {
                        details_movie[i] = data.get(i).getMovie();
                        details_rating[i]  = data.get(i).getRating();
                        details_review[i] = data.get(i).getReview();
                        details_stars[i] = data.get(i).getStars();
                        details_isAdded[i] = data.get(i).getIsAdded();

                        Log.d("Suggestions ","Movie is :"+details_movie[i]);
                        Log.d("Suggestions ","Rating is :"+details_rating[i]);
                        Log.d("Suggestions ","Review is :"+details_review[i]);
                        Log.d("Suggestions ","Favourite is :"+details_isAdded[i]);
                        Log.d("Suggestions ","Star is :"+details_stars[i]);
                    }

                    listItems = new ArrayList<>();

                    for (int i=0;i<data.size();i++){
                        review review = new review(details_movie[i]
                                ,details_review[i]
                                ,details_rating[i],
                                details_stars[i],
                                details_isAdded[i]);

                        listItems.add(review);
                    }

                    adapter = new ReviewAdapter(listItems,getApplicationContext(),pname);
                    rv_review.setAdapter(adapter);
                    rv_review.setLayoutAnimation(controller);
                    rv_review.scheduleLayoutAnimation();

                }

                if (err == true){
//                    Snackbar snackbar = Snackbar.make(cl_genre,"Internal Error",2000);
//                    snackbar.show();
                }

            }

            @Override
            public void onFailure(Call<reviewDetails> call, Throwable t) {

            }
        });



    }
}
