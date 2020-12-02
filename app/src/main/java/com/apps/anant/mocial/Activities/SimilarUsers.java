package com.apps.anant.mocial.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;

import com.apps.anant.mocial.Adapter.UserAdapter;
import com.apps.anant.mocial.Apis.Api;
import com.apps.anant.mocial.Models.UserData;
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

public class SimilarUsers extends AppCompatActivity {

    String[] userName;
    private RecyclerView.Adapter adapter;
    private List<UserData> listItems;
    String json;
    SharedPreferences retrivepref;
    ConstraintLayout cl_similar;
    RecyclerView rv_similar;
    TextView tv_similarHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_users);


        cl_similar = (ConstraintLayout) findViewById(R.id.cl_similar);
        rv_similar = (RecyclerView) findViewById(R.id.rv_similar);
        rv_similar.setLayoutManager(new LinearLayoutManager(this));
        tv_similarHead = (TextView) findViewById(R.id.textView_head);

        Typeface myCustomFont = Typeface.createFromAsset(this.getAssets(),"font/arista_regulartrial.ttf");
        tv_similarHead.setTypeface(myCustomFont);

        final Context context = rv_similar.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.task_layout_falldown);

        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api= retrofit.create(Api.class);

        retrivepref = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String genre = retrivepref.getString( "Genre", "");
        final String uname = retrivepref.getString( "Username", "");


        json = "{\n" +
                "\t\"genre\": \"" + genre + "\"\n" +
                "}";

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json);

        api.positiveUsers(requestBody).enqueue(new Callback<userResponse>() {
            @Override
            public void onResponse(Call<userResponse> call, Response<userResponse> response) {

                userResponse details = response.body();

                List<UserData> users = details.getMatches();

                boolean err = details.isError();

                ArrayList<UserData> newList = new ArrayList<>();

                for (UserData element : users) {

                    if (!newList.contains(element)) {

                        newList.add(element);
                    }
                }

                if(err == false){
                    userName = new String[newList.size()];
                    for (int i=0;i<newList.size();i++){
                        userName[i]= newList.get(i).getUname();
                        Log.d("Review Submit","Username is :"+userName[i]);
                    }
                    listItems = new ArrayList<>();



                    for (int i=0;i<users.size();i++){
                        if(!userName[i].equals(uname)){
                            UserData listItem = new UserData(
                                userName[i]
                            );
                            listItems.add(listItem);
                        }


                    }

                    adapter = new UserAdapter(listItems,getApplicationContext());
                    rv_similar.setAdapter(adapter);
                    rv_similar.setLayoutAnimation(controller);
                    rv_similar.scheduleLayoutAnimation();

                }
                if (err == true){
                    Snackbar snackbar = Snackbar.make(cl_similar,"Internal Server Error",2000);
                    snackbar.show();
                }

            }

            @Override
            public void onFailure(Call<userResponse> call, Throwable t) {
                Snackbar snackbar = Snackbar.make(cl_similar,"Internal Server Error",2000);
                snackbar.show();
            }
        });

    }
}
