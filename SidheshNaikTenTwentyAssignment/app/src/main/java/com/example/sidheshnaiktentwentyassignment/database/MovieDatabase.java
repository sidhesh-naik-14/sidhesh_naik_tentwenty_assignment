package com.example.sidheshnaiktentwentyassignment.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MovieList.class}, version = 1,exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    // public abstract MovieDao movieDao();
    public abstract MovieDao movieDao();

}