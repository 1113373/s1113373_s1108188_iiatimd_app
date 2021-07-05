package com.example.learnkanji;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder> {
    private Context mContext;
    private ArrayList<ProgressItem> mProgress;

    public ProgressAdapter(Context context, ArrayList<ProgressItem> progress) {
        mContext = context;
        mProgress = progress;
    }

    @Override
    public ProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.progress_item, parent, false);
        return new ProgressViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProgressAdapter.ProgressViewHolder holder, int position) {
        ProgressItem currentItem = mProgress.get(position);

        String mistakesText = currentItem.getMistakesTotal();
        String percentageText = currentItem.getMistakesPercentage();
        String dateText = currentItem.getDateQuiz();

        holder.mTextViewMistakes.setText("Mistakes: " + mistakesText);
        holder.mTextViewPercentage.setText("Mistakes %: " + percentageText);
        holder.mTextViewDate.setText("Date: " + dateText);
    }

    @Override
    public int getItemCount() {
        return mProgress.size();
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewMistakes;
        public TextView mTextViewPercentage;
        public TextView mTextViewDate;


        public ProgressViewHolder(View itemView) {
            super(itemView);
            mTextViewMistakes = itemView.findViewById(R.id.mistakes_textview);
            mTextViewPercentage = itemView.findViewById(R.id.percentage_textview);
            mTextViewDate = itemView.findViewById(R.id.date_textview);
        }
    }
}
