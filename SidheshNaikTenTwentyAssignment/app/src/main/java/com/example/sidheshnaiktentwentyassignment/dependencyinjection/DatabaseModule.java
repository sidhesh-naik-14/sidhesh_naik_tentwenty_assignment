package com.example.sidheshnaiktentwentyassignment.dependencyinjection;

import android.app.Application;

import com.example.sidheshnaiktentwentyassignment.database.MovieDao;
import com.example.sidheshnaiktentwentyassignment.database.MovieDatabase;
import com.example.sidheshnaiktentwentyassignment.utils.Constants;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DatabaseModule {


    @Provides
    @Singleton
    MovieDatabase provideMovieDatabase(Application application){
        return Room.databaseBuilder(application,MovieDatabase.class, Constants.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    MovieDao provideWishListDao(MovieDatabase movieDatabase){
        return movieDatabase.movieDao();
    }
}