package com.apps.anant.mocial.Activities;

import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.apps.anant.mocial.Apis.Api;
import com.apps.anant.mocial.Models.myResponse;
import com.apps.anant.mocial.R;
import com.google.firebase.iid.FirebaseInstanceId;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Signup extends AppCompatActivity {

    EditText et_name , et_username, et_pass, et_cpass;
    Button btn_signup;
    String json;
    ConstraintLayout cl_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btn_signup = (Button) findViewById(R.id.button_signup);
        et_name = (EditText) findViewById(R.id.editText_name_signup);
        et_username = (EditText) findViewById(R.id.editText_username_signup);
        et_pass = (EditText) findViewById(R.id.editText_password_signup);
        et_cpass = (EditText) findViewById(R.id.editText_cpassword_signup);
        cl_signup = (ConstraintLayout) findViewById(R.id.cl_signup);

        Typeface myCustomFont1 = Typeface.createFromAsset(getAssets(),"font/montserratBold.ttf");
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(),"font/arista_regulartrial.ttf");
        btn_signup.setTypeface(myCustomFont);
        et_name.setTypeface(myCustomFont);
        et_username.setTypeface(myCustomFont);
        et_pass.setTypeface(myCustomFont);
        et_cpass.setTypeface(myCustomFont);


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

    }

    public void signup() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        String name = et_name.getText().toString().trim();
        String uname = et_username.getText().toString().trim();
        String pass = et_pass.getText().toString().trim();
        String cpass = et_cpass.getText().toString().trim();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Idservice","New Token : "+refreshedToken);


        json = "{\n" +
                "\t\"name\": \"" + name + "\",\n" +
                "\t\"password\": \"" + pass + "\",\n" +
                "\t\"fcmtoken\": \"" + refreshedToken + "\",\n" +
                "\t\"uname\": \"" + uname + "\"\n" +
                "}";

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json);

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(cpass)) {
            api.signup(requestBody).enqueue(new Callback<myResponse>() {
                @Override
                public void onResponse(Call<myResponse> call, Response<myResponse> response) {

                    myResponse res = response.body();

                    boolean err = res.isError();
                    String output = res.getOutput();

                    if(err == true){
                        Snackbar snackbar = Snackbar.make(cl_signup,output,3000);
                        snackbar.show();
                    }
                    if (err == false){
                        Snackbar snackbar = Snackbar.make(cl_signup,output,3000);
                        snackbar.show();
                    }
                }

                @Override
                public void onFailure(Call<myResponse> call, Throwable t) {
                    Snackbar snackbar = Snackbar.make(cl_signup,"Internal Server Error.. Please try checking your internet Connection Too !!",3000);
                    snackbar.show();
                }
            });
        }
        else {
            Snackbar snackbar = Snackbar.make(cl_signup,"You cannot leave any of the fields empty !! ",3000);
            snackbar.show();
        }
    }
}
