package com.example.learnkanji;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import org.json.JSONObject;

import java.util.List;

@Dao
public interface WordDAO {

    @Query("SELECT * FROM words")
    List<Data> getAll();

    @Insert
    void InsertWord(Data data);

    @Delete
    void delete(Data data);


}
