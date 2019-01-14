package dev.ibrahhout.shinystoreadmin.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.ibrahhout.shinystoreadmin.Models.FeedbackModel;
import dev.ibrahhout.shinystoreadmin.R;

/**
 * This file is created By: ( Ibrahim A. Elhout ) on 07/27/2018 at 9:55 PM
 * Project : LaundaryApp1
 * Contacts:
 * Email: Ibrahimhout.dev@gmail.com
 * Phone: 00972598825662
 **/
public class FeedbacksAdapter extends RecyclerView.Adapter<FeedbacksAdapter.FeedbackHolder> {

    private Context context;
    private ArrayList<FeedbackModel> feedbackModels;

    public FeedbacksAdapter(Context context, ArrayList<FeedbackModel> feedbackModels) {
        this.context = context;
        this.feedbackModels = feedbackModels;
    }

    @NonNull
    @Override
    public FeedbackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_layout_feedbacks, parent, false);


        return new FeedbackHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackHolder holder, int position) {

        holder.userNameFeedBackTV.setText(feedbackModels.get(position).getName());
        holder.feedbackTV.setText(feedbackModels.get(position).getFeedBack());


    }

    @Override
    public int getItemCount() {
        return feedbackModels.size();
    }


    class FeedbackHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.userNameFeedBackTV)
        TextView userNameFeedBackTV;
        @BindView(R.id.feedbackTV)
        TextView feedbackTV;
        public FeedbackHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
