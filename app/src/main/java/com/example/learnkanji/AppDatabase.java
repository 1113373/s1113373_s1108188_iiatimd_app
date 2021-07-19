package com.example.learnkanji;

import android.content.Context;
import android.view.contentcapture.ContentCaptureCondition;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Data.class}, version = 11)
public abstract class AppDatabase extends RoomDatabase {

    public abstract WordDAO wordDAO();

}
