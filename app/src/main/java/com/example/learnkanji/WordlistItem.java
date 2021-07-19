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

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public String getHiragana() {
        return hiragana;
    }

    public void setHiragana(String hiragana) {
        this.hiragana = hiragana;
    }

    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }
}
