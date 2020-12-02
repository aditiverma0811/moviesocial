package com.apps.anant.mocial.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.anant.mocial.Models.UserData;
import com.apps.anant.mocial.R;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter .ViewHolder> {

    private List<UserData> listItems;
    private Context context;

    public UserAdapter(List<UserData> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.same_user,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final UserData listItem= listItems.get(i);

        String unm = listItem.getUname();
        Log.d("User Adapter","User Name : "+unm);
        viewHolder.tv_name.setText(unm);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name;
        private TextView tv_id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.textView_uname);
            Typeface myCustomFont = Typeface.createFromAsset(context.getAssets(),"font/arista_regulartrial.ttf");
            Typeface myCustomFont1 = Typeface.createFromAsset(context.getAssets(),"font/montserratBold.ttf");
            tv_name.setTypeface(myCustomFont1);

        }
    }
}
