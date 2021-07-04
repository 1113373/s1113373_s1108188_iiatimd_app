package com.example.iiatimd2021;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.WordlistViewHolder> {
    private Context mContext;
    private ArrayList<WordlistItem> mWordlist;

    public ProgressAdapter(Context context, ArrayList<WordlistItem> wordlist) {
        mContext = context;
        mWordlist = wordlist;
    }

    @Override
    public WordlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.wordlist_item, parent, false);
        return new WordlistViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProgressAdapter.WordlistViewHolder holder, int position) {
        WordlistItem currentItem = mWordlist.get(position);

        String kanjiText = currentItem.getKanji();
        String hiraganaText = currentItem.getHirgana();
        String romajiText = currentItem.getRomaji();
        String englishText = currentItem.getEnglish();

        holder.mTextViewKanji.setText("Kanji: " + kanjiText);
        holder.mTextViewHiragana.setText("Hiragana: " + hiraganaText);
        holder.mTextViewRomaji.setText("Romaji: " + romajiText);
        holder.mTextViewEnglish.setText("English: " + englishText);
    }

    @Override
    public int getItemCount() {
        return mWordlist.size();
    }

    public class WordlistViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewKanji;
        public TextView mTextViewHiragana;
        public TextView mTextViewRomaji;
        public TextView mTextViewEnglish;

        public WordlistViewHolder(View itemView) {
            super(itemView);
            mTextViewKanji = itemView.findViewById(R.id.kanji_textview);
            mTextViewHiragana = itemView.findViewById(R.id.hiragana_textview);
            mTextViewRomaji = itemView.findViewById(R.id.romaji_textview);
            mTextViewEnglish = itemView.findViewById(R.id.english_textview);
        }
    }
}
