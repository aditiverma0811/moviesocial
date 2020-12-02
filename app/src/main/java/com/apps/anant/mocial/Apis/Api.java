package com.apps.anant.mocial.Apis;

import com.apps.anant.mocial.Models.movieResponse;
import com.apps.anant.mocial.Models.myResponse;
import com.apps.anant.mocial.Models.reviewDetails;
import com.apps.anant.mocial.Models.reviewResponse;
import com.apps.anant.mocial.Models.suggestionResponse;
import com.apps.anant.mocial.Models.userResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {

    @POST("/signup")
    Call<myResponse> signup(@Body RequestBody requestBody);

    @POST("/login")
    Call<myResponse> login(@Body RequestBody requestBody);

    @POST("/matches")
    Call<userResponse> positiveUsers(@Body RequestBody requestBody);

    @POST("/seefav")
    Call<userResponse> fetchWishlist(@Body RequestBody requestBody);

    @POST("/seeplans")
    Call<userResponse> fetchPlans(@Body RequestBody requestBody);

    @POST("/invite")
    Call<myResponse> invite(@Body RequestBody requestBody);

    @POST("/like")
    Call<myResponse> addtoFavourite(@Body RequestBody requestBody);

    @POST("/userreviews")
    Call<reviewDetails> userReviews(@Body RequestBody requestBody);

    @POST("/review")
    Call<reviewResponse> submitReview(@Body RequestBody requestBody);

    @POST("/movies")
    Call<movieResponse> fetchMovies(@Body RequestBody requestBody);

    @POST("/oldreviews")
    Call<suggestionResponse> fetchSuggestions(@Body RequestBody requestBody);


}
