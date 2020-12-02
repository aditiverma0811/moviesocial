package com.apps.anant.mocial.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.apps.anant.mocial.Adapter.UserAdapter;
import com.apps.anant.mocial.Apis.Api;
import com.apps.anant.mocial.Models.UserData;
import com.apps.anant.mocial.Models.reviewResponse;
import com.apps.anant.mocial.Models.userResponse;
import com.apps.anant.mocial.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Review extends AppCompatActivity {

    TextView tv_moviename;
    Button btn_submitReview;
    EditText et_movieReview;
    RatingBar rb_movie;
    String uname;
    String movie;
    String genre;
    String review,json;
    float stars;
    SharedPreferences retrivepref;
    ConstraintLayout cl_review;
    Dialog dialog;
    Button proceed;
    ImageView closePopupsubmitImg,img_preiew;
    String[] userName;
    private RecyclerView.Adapter adapter;
    private List<UserData> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);


        dialog = new Dialog(this);
        tv_moviename = (TextView) findViewById(R.id.textView_moviename);
        btn_submitReview = (Button) findViewById(R.id.button_submitReview);
        et_movieReview = (EditText) findViewById(R.id.editText_movieReview);
        rb_movie = (RatingBar) findViewById(R.id.movie_ratingBar);
        cl_review = (ConstraintLayout) findViewById(R.id.cl_review);

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(),"font/arista_regulartrial.ttf");
        Typeface myCustomFont1 = Typeface.createFromAsset(getAssets(),"font/montserratBold.ttf");
        tv_moviename.setTypeface(myCustomFont1);
        btn_submitReview.setTypeface(myCustomFont);
        et_movieReview.setTypeface(myCustomFont);
        rb_movie.setRating(0);



        retrivepref = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        movie = retrivepref.getString( "Movie", "");
        genre = retrivepref.getString( "Genre", "");
        uname = retrivepref.getString( "Username", "");

        tv_moviename.setText(movie);

        btn_submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
//                Intent positiveIntent = new Intent(Review.this,SimilarUsers.class);
//                Review.this.startActivity(positiveIntent);
            }
        });

    }

    public void submit(){

        review = et_movieReview.getText().toString().trim();
        stars = rb_movie.getRating();

        if(!TextUtils.isEmpty(review)){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getResources().getString(R.string.BaseUrl))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Api api = retrofit.create(Api.class);

            json = "{\n" +
                    "\t\"uname\": \"" + uname+ "\",\n" +
                    "\t\"stars\": \"" + stars+ "\",\n" +
                    "\t\"genre\": \"" + genre+ "\",\n" +
                    "\t\"movie\": \"" + movie+ "\",\n" +
                    "\t\"review\": \"" + review + "\"\n" +
                    "}";

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json);

            api.submitReview(requestBody).enqueue(new Callback<reviewResponse>() {
                @Override
                public void onResponse(Call<reviewResponse> call, Response<reviewResponse> response) {
                    reviewResponse res = response.body();

                    boolean err = res.isError();

                    if(err == true){
                        String out = res.getOutput();
                        Snackbar snackbar = Snackbar.make(cl_review, out, 3000);
                        snackbar.show();
                    }
                    if(err == false) {
                        String out = res.getRating();
                        if(out.equals("positive")){
                            ShowPositivePopup();
                        }
                        if(out.equals("negative")){
                            ShowNegativePopup();
                        }
                    }
                }

                @Override
                public void onFailure(Call<reviewResponse> call, Throwable t) {
                    Snackbar snackbar = Snackbar.make(cl_review, "Internal Server Error !!", 3000);
                    snackbar.show();
                }
            });
        }
        else{
            Snackbar snackbar = Snackbar.make(cl_review,"You cannot leave any fields empty !!", 3000);
            snackbar.show();
        }


    }


    private void ShowPositivePopup() {


        dialog.setContentView(R.layout.positive_review_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog .setCanceledOnTouchOutside(false);

        proceed = (Button) dialog.findViewById(R.id.proceedbtn);
        closePopupsubmitImg = (ImageView) dialog.findViewById(R.id.closePopupsubmitImg);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                Intent positiveIntent = new Intent(Review.this,SimilarUsers.class);
                Review.this.startActivity(positiveIntent);
            }
        });

        closePopupsubmitImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void ShowNegativePopup() {

        dialog.setContentView(R.layout.negative_review_poopup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog .setCanceledOnTouchOutside(false);

        proceed = (Button) dialog.findViewById(R.id.proceedbtn);
        closePopupsubmitImg = (ImageView) dialog.findViewById(R.id.closePopupsubmitImg);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        closePopupsubmitImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
