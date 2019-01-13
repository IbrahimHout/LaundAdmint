package dev.ibrahhout.shinystoreadmin.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.ibrahhout.shinystoreadmin.Models.UserModel;
import dev.ibrahhout.shinystoreadmin.R;

/**
 * This file is created By: ( Ibrahim A. Elhout ) on 07/27/2018 at 10:28 PM
 * Project : LaundryAppAdmin
 * Contacts:
 * Email: Ibrahimhout.dev@gmail.com
 * Phone: 00972598825662
 **/

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.FeedbackHolder> {

    private Context context;
    private ArrayList<UserModel> users;

    public UsersAdapter(Context context, ArrayList<UserModel> users) {
        this.context = context;
        this.users = users;
    }


    @NonNull
    @Override
    public FeedbackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_user, parent, false);


        return new FeedbackHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FeedbackHolder holder, int position) {

        holder.userName.setText(users.get(position).getName());
        holder.userAdress.setText(users.get(position).getAddress());
        holder.userEmail.setText(users.get(position).getEmail());


    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    class FeedbackHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.userName)
        TextView userName;
        @BindView(R.id.userAdress)
        TextView userAdress;
        @BindView(R.id.userEmail)
        TextView userEmail;
        FeedbackHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


}
