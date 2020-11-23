package com.project.newsapp.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.project.newsapp.Dao.NewsDao;
import com.project.newsapp.Model.NewsItem;

@Database(entities = {NewsItem.class} , version = 1)
public abstract class NewsDatabase extends RoomDatabase {

    private static final String DATABASE_NAME="NewsDatabase";

    public abstract NewsDao newsDao();

    private static volatile NewsDatabase INSTANCE;

    public static NewsDatabase getINSTANCE(Context context) {

        if(INSTANCE == null)
        {
            synchronized (NewsDatabase.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context,
                            NewsDatabase.class,
                            DATABASE_NAME)
                            .addCallback(callback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);


            new Thread(new Runnable() {
                @Override
                public void run() {

                    INSTANCE.newsDao().removeAllBookMarks();
                }
            }).start();
        }
    };



}
