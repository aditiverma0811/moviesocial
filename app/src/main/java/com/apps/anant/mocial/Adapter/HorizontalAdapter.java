package com.apps.anant.mocial.Adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.apps.anant.mocial.Activities.Login;
import com.apps.anant.mocial.Activities.Movie;
import com.apps.anant.mocial.Activities.SelectGenre;
import com.apps.anant.mocial.Activities.Suggestions;
import com.apps.anant.mocial.R;

import java.util.ArrayList;

public class HorizontalAdapter extends  RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder> {

    private ArrayList<Integer> items;
    private TextView tv_gen;
    Typeface myCustomFont;
    Context context;

    public HorizontalAdapter(ArrayList<Integer> items, TextView tv) {
        this.items = items;
        this.tv_gen = tv;
    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_layout,viewGroup,false);
        myCustomFont = Typeface.createFromAsset(viewGroup.getContext().getAssets(),"font/arista_regulartrial.ttf");
        return  new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HorizontalViewHolder horizontalViewHolder, int i) {

        horizontalViewHolder.iv_genre.setImageResource(Integer.parseInt(String.valueOf(items.get(i))));
        if(i==0){
            tv_gen.setText("Action");
            tv_gen.setTypeface(myCustomFont);
        }
        if(i==1){
            tv_gen.setText("Romance");
            tv_gen.setTypeface(myCustomFont);
        }
        if(i==2){
            tv_gen.setText("Comdey");
            tv_gen.setTypeface(myCustomFont);
        }
        if(i==3){
            tv_gen.setText("Scientific Fiction");
            tv_gen.setTypeface(myCustomFont);
        }

//        horizontalViewHolder.iv_genre.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                switch (view.getId()){
//                    case R.drawable.action:
//                        tv_gen.setText("Action");
//                        tv_gen.setTypeface(myCustomFont);
//                        break;
//                    case R.drawable.romance:
//                        tv_gen.setText("Romance");
//                        tv_gen.setTypeface(myCustomFont);
//                        break;
//                    case R.drawable.comedy:
//                        tv_gen.setText("Comedy");
//                        tv_gen.setTypeface(myCustomFont);
//                        break;
//                    case R.drawable.scifi:
//                        tv_gen.setText("Scientific Fiction");
//                        tv_gen.setTypeface(myCustomFont);
//                        break;
//                    default:
//                        tv_gen.setText("Action");
//                        tv_gen.setTypeface(myCustomFont);
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        CardView cd_genre;
        ImageView iv_genre;
        RelativeLayout revealView;
        float pixelDensity,hypotenuse;
        ConstraintLayout cl_item;

        public HorizontalViewHolder(@NonNull View itemView) {
            super(itemView);

            cd_genre = (CardView) itemView.findViewById(R.id.cardView_genre);
            iv_genre = (ImageView) itemView.findViewById(R.id.imageView_genre);
            revealView = (RelativeLayout) itemView.findViewById(R.id.revealView);
            cl_item = (ConstraintLayout) itemView.findViewById(R.id.cl_item);
            pixelDensity = itemView.getResources().getDisplayMetrics().density;

            final Animation zoomOutAnimation = AnimationUtils.loadAnimation(context, R.anim.zoomout);
            final Animation fadeinAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out);


//            final LayoutAnimationController controller =
//                    AnimationUtils.loadLayoutAnimation(context, R.anim.zoomout);

            iv_genre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = getAdapterPosition();

                    if(i==0){
                        tv_gen.setText("Action");
                        tv_gen.setTypeface(myCustomFont);

                        int width = cd_genre.getWidth();
                        int height = cd_genre.getHeight();

                        int x1 = width/2;
                        int y1 = height/2;

                      hypotenuse = (int) Math.hypot(x1,y1);

                        ObjectAnimator objectAnimatorY
                                = ObjectAnimator.ofFloat(tv_gen, "translationY", -110f);
                        objectAnimatorY.setDuration(200);
                        objectAnimatorY.start();

                        ((SelectGenre)context).suggest(0);

//                        cd_genre.startAnimation(zoomOutAnimation);
//                        cd_genre.setVisibility(View.GONE);
//                        objectAnimatorY.addListener(new Animator.AnimatorListener() {
//                            @Override
//                            public void onAnimationStart(Animator animator) {
//
//                            }
//
//                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//                            @Override
//                            public void onAnimationEnd(Animator animator) {
//                                Animator anim = ViewAnimationUtils.createCircularReveal(revealView, 368, 375, 28 * pixelDensity, hypotenuse);
//                                anim.setDuration(450);
//
//                                cd_genre.setVisibility(View.GONE);
//                                iv_genre.setVisibility(View.GONE);
//                                revealView.setVisibility(View.VISIBLE);
//                                anim.start();
//
//                                anim.addListener(new Animator.AnimatorListener() {
//                                    @Override
//                                    public void onAnimationStart(Animator animator) {
//
//                                    }
//
//                                    @Override
//                                    public void onAnimationEnd(Animator animator) {
//                                        Intent suggestionIntent = new Intent(context, Suggestions.class);
//                                        context.startActivity(suggestionIntent );
////                                        btn_login.setVisibility(View.VISIBLE);
////                                        et_password.setVisibility(View.VISIBLE);
////                                        et_username.setVisibility(View.VISIBLE);
////                                        revealView.setVisibility(View.GONE);
////                                        Snackbar snackbar = Snackbar.make(cl_login, output, 3000);
////                                        snackbar.show();
//                                    }
//
//                                    @Override
//                                    public void onAnimationCancel(Animator animator) {
//
//                                    }
//
//                                    @Override
//                                    public void onAnimationRepeat(Animator animator) {
//
//                                    }
//                                });
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



                    }
                    if(i==1){
                        tv_gen.setText("Romance");
                        tv_gen.setTypeface(myCustomFont);
                        ObjectAnimator objectAnimatorY
                                = ObjectAnimator.ofFloat(tv_gen, "translationY", -110f);
                        objectAnimatorY.setDuration(200);
                        objectAnimatorY.start();
                        ((SelectGenre)context).suggest(1);
                    }
                    if(i==2){
                        tv_gen.setText("Comdey");
                        tv_gen.setTypeface(myCustomFont);
                        ObjectAnimator objectAnimatorY
                                = ObjectAnimator.ofFloat(tv_gen, "translationY", -110f);
                        objectAnimatorY.setDuration(200);
                        objectAnimatorY.start();
                        ((SelectGenre)context).suggest(2);
                    }
                    if(i==3){
                        tv_gen.setText("Science Fiction");
                        tv_gen.setTypeface(myCustomFont);
                        ObjectAnimator objectAnimatorY
                                = ObjectAnimator.ofFloat(tv_gen, "translationY", -110f);
                        objectAnimatorY.setDuration(200);
                        objectAnimatorY.start();
                        ((SelectGenre)context).suggest(3);
                    }

                }
            });

        }
    }
}
