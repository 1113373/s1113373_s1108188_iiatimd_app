package com.example.iiatimd2021;

public class WordlistItem {
    private String mKanji;
    private String mHiragana;
    private String mRomaji;
    private String mEnglish;

    public WordlistItem(String kanji, String hiragana, String romaji, String english) {
        mKanji = kanji;
        mHiragana = hiragana;
        mRomaji = romaji;
        mEnglish = english;
    }

    public String getKanji() {
        return mKanji;
    }

    public String getHirgana() {
        return mHiragana;
    }

    public String getRomaji() {
        return mRomaji;
    }

    public String getEnglish() {
        return mEnglish;
    }
}
