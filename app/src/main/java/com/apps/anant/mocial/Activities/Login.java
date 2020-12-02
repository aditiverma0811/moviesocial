package com.apps.anant.mocial.Activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.apps.anant.mocial.Apis.Api;
import com.apps.anant.mocial.Models.myResponse;
import com.apps.anant.mocial.R;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    TextView tv_signup, tv_or;
    Button btn_login;
    EditText et_username, et_password;
    ConstraintLayout cl_login;
    RelativeLayout revealView;
    String json,output;
    float pixelDensity;
    SharedPreferences sharedPreferences;
    int y,hypotenuse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_signup = (TextView) findViewById(R.id.textView_signup);
        tv_or = (TextView) findViewById(R.id.textView_or);
        btn_login = (Button) findViewById(R.id.button_login);
        cl_login = (ConstraintLayout) findViewById(R.id.cl_login);
        revealView = (RelativeLayout) findViewById(R.id.revealView);
        et_username = (EditText) findViewById(R.id.editText_usernameLogin);
        et_password = (EditText) findViewById(R.id.editText_passwordLogin);


        pixelDensity = getResources().getDisplayMetrics().density;
        tv_signup.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "font/arista_regulartrial.ttf");
        Typeface myCustomFont1 = Typeface.createFromAsset(getAssets(), "font/montserratBold.ttf");
        tv_signup.setTypeface(myCustomFont);
        et_username.setTypeface(myCustomFont);
        et_password.setTypeface(myCustomFont);
        tv_or.setTypeface(myCustomFont);
        btn_login.setTypeface(myCustomFont);

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(Login.this, Signup.class);
                Login.this.startActivity(signupIntent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });

    }

    public void login() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        String uname = et_username.getText().toString().trim();
        String pass = et_password.getText().toString().trim();

        sharedPreferences= getApplicationContext().getSharedPreferences("MyData", 0); // 0 - for private mode
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Username",uname); // Storing boolean - true/false
        editor.commit();


        json = "{\n" +
                "\t\"password\": \"" + pass + "\",\n" +
                "\t\"uname\": \"" + uname + "\"\n" +
                "}";

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        if (!TextUtils.isEmpty(pass) && !TextUtils.isEmpty(pass)) {
            api.login(requestBody).enqueue(new Callback<myResponse>() {
                @Override
                public void onResponse(Call<myResponse> call, Response<myResponse> response) {

                    myResponse res = response.body();

                    boolean err = res.isError();
                    output = res.getOutput();

                    if (err == true) {
                        Snackbar snackbar = Snackbar.make(cl_login, output, 3000);
                        snackbar.show();
                    }
                    if (err == false) {

                        int width = cl_login.getWidth();
                        int height = cl_login.getHeight();

                        int x1 = width/2;
                        int y1 = height/2;

                        hypotenuse = (int) Math.hypot(x1,y1);

                        y = cl_login.getHeight() / 4;

                        ObjectAnimator objectAnimatorY
                                = ObjectAnimator.ofFloat(btn_login, "translationY", 0f, -y);
                        objectAnimatorY.setDuration(400);
                        objectAnimatorY.start();

                        objectAnimatorY.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onAnimationEnd(Animator animator) {
                                Animator anim = ViewAnimationUtils.createCircularReveal(revealView, 368, 375, 28 * pixelDensity, hypotenuse);
                                anim.setDuration(450);

                                btn_login.setVisibility(View.GONE);
                                et_password.setVisibility(View.GONE);
                                et_username.setVisibility(View.GONE);
                                revealView.setVisibility(View.VISIBLE);
                                anim.start();

                                anim.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        Intent loginIntent = new Intent(Login.this, Movie.class);
                                        Login.this.startActivity(loginIntent);
//                                        btn_login.setVisibility(View.VISIBLE);
//                                        et_password.setVisibility(View.VISIBLE);
//                                        et_username.setVisibility(View.VISIBLE);
//                                        revealView.setVisibility(View.GONE);
//                                        Snackbar snackbar = Snackbar.make(cl_login, output, 3000);
//                                        snackbar.show();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {

                                    }
                                });



                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<myResponse> call, Throwable t) {
                    Snackbar snackbar = Snackbar.make(cl_login, "Internal Server Error.. Please try checking your internet Connection Too !!", 3000);
                    snackbar.show();
                }
            });
        } else {
            Snackbar snackbar = Snackbar.make(cl_login, "You cannot leave any of the fields empty !! ", 3000);
            snackbar.show();
        }
    }
}
