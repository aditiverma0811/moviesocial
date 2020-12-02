package com.apps.anant.mocial.Activities;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.apps.anant.mocial.Adapter.HorizontalAdapter;
import com.apps.anant.mocial.Adapter.SuggestionAdapter;
import com.apps.anant.mocial.Apis.Api;
import com.apps.anant.mocial.Models.ExampleBottomSheetDialog;
import com.apps.anant.mocial.Models.suggestionData;
import com.apps.anant.mocial.Models.suggestionResponse;
import com.apps.anant.mocial.R;

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

public class SelectGenre extends AppCompatActivity implements ExampleBottomSheetDialog.BottomSheetListner {

    TextView tv_genre;
    ConstraintLayout cl_genre;
    RecyclerView list,rv_suggestions;
    LottieAnimationView lottieAnim,lottieChat;
    ArrayList<Integer> list1 = new ArrayList<Integer>();
    String[] details_uname;
    String[] details_pos;
    String[] details_neg;
    private RecyclerView.Adapter adapter;
    private List<suggestionData> listItems;
    SharedPreferences sharedPreferences;
    TextView tv_head, tv_new, tv_exist;
    private LinearLayout bottomsheetlayout;
    private LinearLayout bottomsheetnew;
    private BottomSheetBehavior bottomsheetbehavior;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_genre);

        tv_genre = (TextView) findViewById(R.id.textView_username);
        cl_genre = (ConstraintLayout) findViewById(R.id.cl_select);
        lottieAnim = (LottieAnimationView) findViewById(R.id.lottieAnimationProceed);
        lottieChat = (LottieAnimationView) findViewById(R.id.lottieChat);
        rv_suggestions = (RecyclerView) findViewById(R.id.recyclerView_suggestions);
        bottomsheetlayout = (LinearLayout) findViewById(R.id.bottomsheet);
        bottomsheetnew= (LinearLayout) findViewById(R.id.newPlan);
        bottomsheetbehavior= BottomSheetBehavior.from(bottomsheetlayout);
        tv_head = (TextView) findViewById(R.id.tv_heading);
        tv_new= (TextView) findViewById(R.id.tv_new);
        tv_exist= (TextView) findViewById(R.id.tv_exist);



        final Typeface myCustomFont = Typeface.createFromAsset(getAssets(),"font/arista_regulartrial.ttf");
        tv_genre.setTypeface(myCustomFont);
        tv_head.setTypeface(myCustomFont);
        tv_new.setTypeface(myCustomFont);
        tv_exist.setTypeface(myCustomFont);

        tv_exist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SelectGenre.this, "Exisiting Plan Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        bottomsheetbehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


//        bottomsheetnew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                bottomsheetbehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//                Toast.makeText(SelectGenre.this, "New Plan selected", Toast.LENGTH_SHORT).show();
//            }
//        });


        BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                ExampleBottomSheetDialog bottomsheet = new ExampleBottomSheetDialog();
                bottomsheet.show(getSupportFragmentManager(),"Example Bottomsheet");


            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("Show Bottom Sheet"));

        list = (RecyclerView) findViewById(R.id.recyclerView_genre);

        list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rv_suggestions.setLayoutManager(new LinearLayoutManager(this));


        list1.add(R.drawable.action);
        list1.add(R.drawable.romance);
        list1.add(R.drawable.comedy);
        list1.add(R.drawable.scifi);
        list.setAdapter(new HorizontalAdapter(list1,tv_genre));
        final Animation zoomInAnimation = AnimationUtils.loadAnimation(this, R.anim.zoomin);

        lottieChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatIntent = new Intent(SelectGenre.this,ChatBoxActivity.class);
                SelectGenre.this.startActivity(chatIntent);
            }
        });

        tv_genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lottieChat.setVisibility(View.GONE);
                ObjectAnimator objectAnimatorY
                        = ObjectAnimator.ofFloat(tv_genre, "translationY", 20f);
                objectAnimatorY.setDuration(200);
                objectAnimatorY.start();

                rv_suggestions.setVisibility(View.GONE);

                ObjectAnimator objectAnimatorY1
                        = ObjectAnimator.ofFloat(lottieAnim, "translationY", 100f);
                objectAnimatorY1.setDuration(400);
                objectAnimatorY1.start();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lottieAnim.startAnimation(zoomInAnimation);
                        list1.add(R.drawable.action);
                        list1.add(R.drawable.romance);
                        list1.add(R.drawable.comedy);
                        list1.add(R.drawable.scifi);
                        list.setVisibility(View.VISIBLE);
                        list.setAdapter(new HorizontalAdapter(list1,tv_genre));
                    }
                }, 600);
            }
        });


    }

    public void suggest(int position){

        final Context context = rv_suggestions.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.task_layout_falldown);

        final Animation zoomOutAnimation = AnimationUtils.loadAnimation(this, R.anim.zoomout);
        String json = "";

        list1.clear();
        list.setAdapter(new HorizontalAdapter(list1,tv_genre));
        list.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);

        if(position==0){
            json = "{\n" +
                    "\t\"genre\": \"" + "Action"+ "\"\n" +
                    "}";
            sharedPreferences= getApplicationContext().getSharedPreferences("MyData", 0); // 0 - for private mode
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Genre1","Action"); // Storing boolean - true/false
            editor.commit();

        }
        if(position==1){
            json = "{\n" +
                    "\t\"genre\": \"" + "Romance"+ "\"\n" +
                    "}";
            sharedPreferences= getApplicationContext().getSharedPreferences("MyData", 0); // 0 - for private mode
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Genre1","Romance"); // Storing boolean - true/false
            editor.commit();

        }
        if(position==2){
            json = "{\n" +
                    "\t\"genre\": \"" + "Comedy"+ "\"\n" +
                    "}";

            sharedPreferences= getApplicationContext().getSharedPreferences("MyData", 0); // 0 - for private mode
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Genre1","Comedy"); // Storing boolean - true/false
            editor.commit();
        }
        if(position==3){
            json = "{\n" +
                    "\t\"genre\": \"" + "Scifi"+ "\"\n" +
                    "}";
            sharedPreferences= getApplicationContext().getSharedPreferences("MyData", 0); // 0 - for private mode
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Genre1","Scifi"); // Storing boolean - true/false
            editor.commit();
        }


        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        api.fetchSuggestions(requestBody).enqueue(new Callback<suggestionResponse>() {
            @Override
            public void onResponse(Call<suggestionResponse> call, Response<suggestionResponse> response) {

                suggestionResponse res = response.body();

                List<suggestionData> data = res.getData();

                boolean err = res.isError();
                Log.d("Suggestions","The err value is "+err);

                if (err == false){
                    details_uname = new String[data.size()];
                    details_pos= new String[data.size()];
                    details_neg= new String[data.size()];

                    for (int i=0;i<data.size();i++) {
                        details_uname[i] = data.get(i).getUname();
                        details_pos[i]  = data.get(i).getPos();
                        details_neg[i] = data.get(i).getNeg();

                        Log.d("Suggestions ","Username is :"+details_uname[i]);
                        Log.d("Suggestions ","Positive is :"+details_pos[i]);
                        Log.d("Suggestions ","Negative is :"+details_neg[i]);
                    }

                    listItems = new ArrayList<>();

                    for (int i=0;i<data.size();i++){
                        suggestionData suggestion = new suggestionData(details_uname[i]
                        ,details_pos[i]
                        ,details_neg[i]);

                        listItems.add(suggestion);
                    }

                    rv_suggestions.setVisibility(View.VISIBLE);

                    adapter = new SuggestionAdapter(listItems,getApplicationContext());
                    rv_suggestions.setAdapter(adapter);
                    rv_suggestions.setLayoutAnimation(controller);
                    rv_suggestions.scheduleLayoutAnimation();

                }

                if (err == true){
                    Snackbar snackbar = Snackbar.make(cl_genre,"Internal Error",2000);
                    snackbar.show();
                }

            }

            @Override
            public void onFailure(Call<suggestionResponse> call, Throwable t) {

            }
        });

        lottieAnim.startAnimation(zoomOutAnimation);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator objectAnimatorY
                        = ObjectAnimator.ofFloat(lottieAnim, "translationY", -4000f);
                objectAnimatorY.setDuration(400);
                objectAnimatorY.start();
                lottieChat.setVisibility(View.VISIBLE);

            }
        }, 600);

//        objectAnimatorY.addListener(new Animator.AnimatorListener() {
//                            @Override
//                            public void onAnimationStart(Animator animator) {
//
//                            }
//
//                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//                            @Override
//                            public void onAnimationEnd(Animator animator) {
//                                ObjectAnimator objectAnimatorY
//                                        = ObjectAnimator.ofFloat(lottieAnim, "translationY", -1524f);
//                                objectAnimatorY.setDuration(400);
//                                objectAnimatorY.start();
//
//
//
//
//                            }
//
//                            @Override
//                            public void onAnimationCancel(Animator animator) {
//
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animator animator) {
//
//                            }
//                        });



//        lottieAnim.setVisibility(View.GONE);
//        notifyItemRangeRemoved(0, size);
//        notifyItemRangeRemoved(0, size);

//        list.startAnimation(zoomOutAnimation);
//        list.setVisibility(View.GONE);
    }

    @Override public boolean dispatchTouchEvent(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (bottomsheetbehavior.getState()==BottomSheetBehavior.STATE_EXPANDED) {

                    bottomsheetbehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onLayoutClicked(String text) {

    }
}
