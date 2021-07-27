package com.example.learnkanji;

public class WordlistItem {
    int id;
    String kanji;
    String hiragana;
    String romaji;
    String english;

    public WordlistItem(int id, String kanji, String hiragana, String romaji, String english) {
        this.id = id;
        this.kanji = kanji;
        this.hiragana = hiragana;
        this.romaji = romaji;
        this.english = english;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKanji() {
        return kanji;
    }

    public String getHiragana() {
        return hiragana;
    }

    public String getRomaji() {
        return romaji;
    }

    public String getEnglish() {
        return english;
    }
}
