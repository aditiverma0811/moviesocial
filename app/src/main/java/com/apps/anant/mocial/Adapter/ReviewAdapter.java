package com.apps.anant.mocial.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.apps.anant.mocial.Activities.UserReview;
import com.apps.anant.mocial.Apis.Api;
import com.apps.anant.mocial.Models.AnimateCounter;
import com.apps.anant.mocial.Models.myResponse;
import com.apps.anant.mocial.Models.review;
import com.apps.anant.mocial.Models.suggestionData;
import com.apps.anant.mocial.R;
import com.txusballesteros.widgets.FitChart;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<review> listItems;
    private Context context;
    SharedPreferences sharedPreferences;
    int flag=0;
    String uname="";

    public ReviewAdapter(List<review> listItems, Context context,String uname) {
        this.listItems = listItems;
        this.context = context;
        this.uname = uname;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reviewlist,viewGroup,false);
        return new ReviewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReviewAdapter.ViewHolder viewHolder, int i) {


        final review listItem= listItems.get(i);

        final String movie = listItem.getMovie();
        Log.d("Suggestion Adapter","Movie Name : "+movie);
        viewHolder.tv_mname.setText(movie);

        String review = listItem.getReview();
        viewHolder.tv_review.setText(review);
        Log.d("Suggestion Adapter","Review : "+review);

        String rating = listItem.getRating();
        Log.d("Suggestion Adapter","Rating : "+rating);

        String stars = listItem.getStars();
        viewHolder.rb_rate.setRating(Float.parseFloat(stars));
        Log.d("Suggestion Adapter","Stars : "+stars);

        if(rating.equals("positive")){
            viewHolder.img_top.setImageResource(R.drawable.curved_topgreen);
        }

        if(rating.equals("negative")){
            viewHolder.img_top.setImageResource(R.drawable.curved_top);
        }

        final String favourite = listItem.getIsAdded();
        if(favourite.equals("1"))
            viewHolder.img_wishlist.setImageResource(R.drawable.heart_on);

        viewHolder.img_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if(favourite.equals("0")){
                    viewHolder.img_wishlist.setImageResource(R.drawable.heart_on);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(context.getResources().getString(R.string.BaseUrl))
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Api api = retrofit.create(Api.class);

                    String json = "{\n" +
                            "\t\"uname\": \"" + uname+ "\",\n" +
                            "\t\"movie\": \"" + movie+ "\"\n" +
                            "}";

                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json);

                    api.addtoFavourite(requestBody).enqueue(new Callback<myResponse>() {
                        @Override
                        public void onResponse(Call<myResponse> call, Response<myResponse> response) {

                            myResponse res= response.body();

                            boolean err = res.isError();

                            if(err == false){
                                Snackbar snackbar = Snackbar.make(view,"Added the movie to favourites to watch later",2000);
                                snackbar.show();
                            }

                            if(err == true){
                                Snackbar snackbar = Snackbar.make(view,"Internal Server Error !!",2000);
                                snackbar.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<myResponse> call, Throwable t) {

                            Snackbar snackbar = Snackbar.make(view,"Internal Server Error !!",2000);
                            snackbar.show();
                        }
                    });

                }
            }
        });

//        public void addToFavourite(){
////
////            Retrofit retrofit = new Retrofit.Builder()
////                    .addConverterFactory(GsonConverterFactory.create())
////                    .baseUrl(context.getResources().getString(R.string.BaseUrl))
////                    .build();
////
////            Api api = retrofit.create(Api.class);
////
////            RequestBody requestBody =
////
////        }

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_mname;
        private TextView tv_review;
        private RatingBar rb_rate;
        private ImageView img_top;
        private ImageView img_wishlist;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_mname = (TextView) itemView.findViewById(R.id.textView_movie);
            tv_review= (TextView) itemView.findViewById(R.id.textView_review);
            rb_rate = (RatingBar) itemView.findViewById(R.id.ratingBar);
            img_top = (ImageView) itemView.findViewById(R.id.imageView_top);
            img_wishlist = (ImageView) itemView.findViewById(R.id.imageView_wishlist);

            Typeface myCustomFont = Typeface.createFromAsset(context.getAssets(),"font/arista_regulartrial.ttf");
            Typeface myCustomFont1 = Typeface.createFromAsset(context.getAssets(),"font/montserratBold.ttf");
            tv_mname.setTypeface(myCustomFont1);
            tv_review.setTypeface(myCustomFont1);
        }
    }
}


