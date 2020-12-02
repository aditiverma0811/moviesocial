package com.apps.anant.mocial.Models;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.apps.anant.mocial.Apis.Api;
import com.apps.anant.mocial.R;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExampleBottomSheetDialog extends BottomSheetDialogFragment {

    private  BottomSheetListner mListner;
    Dialog dialog;
    Button proceed;
    ImageView closePopupsubmitImg,img_preiew;
    SharedPreferences pref;
    BottomSheetBehavior bottomSheetBehavior;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottomsheet,container,false);

        LinearLayout l1 = (LinearLayout) v.findViewById(R.id.newPlan);
        LinearLayout l2 = (LinearLayout) v.findViewById(R.id.existingPlan);

        dialog = new Dialog(getContext());
        context = getContext();

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowtransactionPopup();
//                Toast.makeText(getContext(), "New Plan Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListner.onLayoutClicked("Existing Plan clicked");
            }
        });

        return v;
    }

    public interface BottomSheetListner{
        void onLayoutClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListner = (BottomSheetListner) context;
        }catch (ClassCastException e){
            throw  new ClassCastException(context.toString()+"must implement listner");
        }


    }

    private void HomeLoading() {
        dialog.setContentView(R.layout.create_plan_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.color.homeback);
        dialog.show();
    }


    private void ShowtransactionPopup() {

        final EditText et_plan;

        dialog.setContentView(R.layout.wanna_invite);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog .setCanceledOnTouchOutside(false);

        et_plan = (EditText) dialog.findViewById(R.id.et_planname);
        proceed = (Button) dialog.findViewById(R.id.proceedbtn);
        closePopupsubmitImg = (ImageView) dialog.findViewById(R.id.closePopupsubmitImg);


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                pref = context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
                String genre= pref.getString("Genre1", "");
                String cname= pref.getString("Username1", "");
                String pname= pref.getString("Username", "");
                String planname= et_plan.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getResources().getString(R.string.BaseUrl))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);

                String json = "{\n" +
                        "\t\"cname\": \"" + cname+ "\",\n" +
                        "\t\"pname\": \"" + pname + "\",\n" +
                        "\t\"planname\": \"" +planname+ "\",\n" +
                        "\t\"genre\": \"" + genre + "\",\n" +
                        "\t\"exist\": \"" + "no" + "\"\n" +
                        "}";

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

                api.invite(requestBody).enqueue(new Callback<myResponse>() {
                    @Override
                    public void onResponse(Call<myResponse> call, Response<myResponse> response) {

                        myResponse res = response.body();

                        boolean err = res.isError();

                        if(err == false){
                            dialog.dismiss();
                        }
                        if (err == true){
                            Toast.makeText(context, "There was some problem with the request. Retry !!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<myResponse> call, Throwable t) {
                        Toast.makeText(context, "There was some problem with the request. Retry !!", Toast.LENGTH_SHORT).show();
                    }
                });

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
