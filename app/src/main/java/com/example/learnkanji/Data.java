package com.example.learnkanji;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONObject;

@Entity(tableName = "words")
public class Data {
    @PrimaryKey
    int id;

    @ColumnInfo(name = "Kanji")
    String kanji;

    @ColumnInfo(name = "Hiragana")
    String hiragana;

    @ColumnInfo(name = "Romaji")
    String romaji;

    @ColumnInfo(name = "English")
    String english;


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setKanji(String kanji){
        this.kanji = kanji;
    }

    public String getKanji() {
        return kanji;
    }

    public void setHiragana(String hiragana) {
        this.hiragana = hiragana;
    }

    public String getHiragana() {
        return hiragana;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    public String getRomaji() {
        return romaji;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getEnglish() {
        return english;
    }

}
