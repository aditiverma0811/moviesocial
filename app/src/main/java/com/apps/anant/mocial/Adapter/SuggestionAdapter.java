package com.apps.anant.mocial.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.apps.anant.mocial.Activities.UserReview;
import com.apps.anant.mocial.Models.AnimateCounter;
import com.apps.anant.mocial.Models.suggestionData;
import com.apps.anant.mocial.R;
import com.txusballesteros.widgets.FitChart;

import org.w3c.dom.Text;

import java.util.List;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter .ViewHolder> {

    private List<suggestionData> listItems;
    private Context context;
    SharedPreferences sharedPreferences;


    public SuggestionAdapter(List<suggestionData> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public SuggestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listitem,viewGroup,false);
        return new SuggestionAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final SuggestionAdapter.ViewHolder viewHolder, int i) {

        final suggestionData listItem= listItems.get(i);

        viewHolder.tv_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String us = listItem.getUname();
                sharedPreferences= context.getApplicationContext().getSharedPreferences("MyData", 0); // 0 - for private mode
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Username1",us); // Storing boolean - true/false
                editor.commit();


                Intent intent = new Intent("Show Bottom Sheet");
                intent.putExtra("name" , String.valueOf("Show"));
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//                 viewHolder.bottomsheetbehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                TextView bottomSheetNew = (TextView) view.findViewById(R.id.tv_new);
            }
        });


        viewHolder.cd_suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String us = listItem.getUname();

                sharedPreferences= context.getApplicationContext().getSharedPreferences("MyData", 0); // 0 - for private mode
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Username1",us); // Storing boolean - true/false
                editor.commit();
                Intent intent = new Intent(context, UserReview.class);
                context.startActivity(intent);


            }
        });
        String unm = listItem.getUname();
        Log.d("Suggestion Adapter","User Name : "+unm);
        viewHolder.tv_name.setText(unm);
        String pos = listItem.getPos();
        String neg = listItem.getNeg();
        int total = Integer.parseInt(pos)+ Integer.parseInt(neg);
        Log.d("Suggestion Adapter","The total value is : "+total);
        float pper = (float) Integer.parseInt(pos)/total;
        Log.d("Suggestion Adapter","The pper value is : "+pper);
        float pneg = (float) Integer.parseInt(neg)/total;

        pper = pper*100;
        pneg = pneg*100;

        int perp = (int) Math.ceil(pper);
        int pern = (int) Math.ceil(pneg);

        if(perp > pern){
                viewHolder.img_top.setImageResource(R.drawable.curved_topgreen);
        }

        if(perp < pern){
            viewHolder.img_top.setImageResource(R.drawable.curved_top);
        }


        viewHolder.pb_pos.setValue(perp);
        viewHolder.pb_neg.setValue(pern);


        Log.d("Suggestion Adapter","The positive percentage is "+perp);


        Log.d("Suggestion Adapter","The nper value is : "+pneg);

        Log.d("Suggestion Adapter","Positive Count: "+pos);

        AnimateCounter animateCounter = new AnimateCounter.Builder(viewHolder.tv_pos)
                .setCount(0, perp)
                .setDuration(1200)
                .build();

        animateCounter.execute();

        viewHolder.tv_pos.setText(String.valueOf(perp));

        Log.d("Suggestion Adapter","Negative Count : "+neg);

        AnimateCounter animateCounter1 = new AnimateCounter.Builder(viewHolder.tv_neg)
                .setCount(0, pern)
                .setDuration(1200)
                .build();

        animateCounter1.execute();

        viewHolder.tv_neg.setText(String.valueOf(pern));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name;
        private TextView tv_pos;
        private TextView tv_neg;
        private TextView tv_explore;
        private TextView tv_posCount;
        private TextView tv_negCount;
        private ImageView img_top;
        private CardView cd_suggestion;
        private FitChart pb_pos;
        private FitChart pb_neg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.textView_uname);
            tv_pos = (TextView) itemView.findViewById(R.id.textViewPositive);
            tv_neg = (TextView) itemView.findViewById(R.id.textViewNegative);
            tv_explore = (TextView) itemView.findViewById(R.id.textView_explore);
            pb_pos = (FitChart) itemView.findViewById(R.id.progress_barPositive);
            pb_neg = (FitChart) itemView.findViewById(R.id.progress_barNegative);
            tv_posCount = (TextView) itemView.findViewById(R.id.textView_posCount);
            tv_negCount = (TextView) itemView.findViewById(R.id.textView_negCount);
            img_top = (ImageView) itemView.findViewById(R.id.imageView_top);
            cd_suggestion = (CardView) itemView.findViewById(R.id.cd_suggestion);


            pb_pos.setMinValue(0f);
            pb_pos.setMaxValue(100f);
            pb_neg.setMinValue(0f);
            pb_neg.setMaxValue(100f);

            Typeface myCustomFont = Typeface.createFromAsset(context.getAssets(),"font/arista_regulartrial.ttf");
            Typeface myCustomFont1 = Typeface.createFromAsset(context.getAssets(),"font/montserratBold.ttf");
            tv_name.setTypeface(myCustomFont);
            tv_pos.setTypeface(myCustomFont1);
            tv_neg.setTypeface(myCustomFont1);
            tv_explore.setTypeface(myCustomFont1);
            tv_posCount.setTypeface(myCustomFont1);
            tv_negCount.setTypeface(myCustomFont1);

        }
    }
}


