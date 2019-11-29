package com.kmanolopoulos.KSolo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DataFileBrowser extends SQLiteOpenHelper
{
    private final int MAX_ENTRIES = 10;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "highscoresDB";
    private final String TABLE_NAME_HIGHSCORES = "Highscores";
    private final String HIGHSCORE_ID = "highscore_id";
    private final String PLAYER_NAME = "player_name";
    private final String PAWNS_LEFT = "pawns_left";
    private final String SECONDS_ELAPSED = "seconds_elapsed";
    private final String GAME_DATE = "game_date";

    public DataFileBrowser(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dbase)
    {
        // Create Products table
        String CREATE_TABLE_PRODUCT =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_HIGHSCORES +
                        "(" +
                        HIGHSCORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        PLAYER_NAME	+ " TEXT NOT NULL, " +
                        PAWNS_LEFT + " INTEGER NOT NULL, " +
                        SECONDS_ELAPSED + " INTEGER NOT NULL, " +
                        GAME_DATE + " TEXT NOT NULL" +
                        ")";

        dbase.execSQL(CREATE_TABLE_PRODUCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbase, int oldVersion, int newVersion)
    {
        // Actions here only if database schema changes
    }

    public boolean AddHighcore(HighscoreEntry entry)
    {
        SQLiteDatabase dbase = this.getWritableDatabase();
        String sql1, sql2;

        if (GetEntriesNumber() == MAX_ENTRIES)
        {
            sql1 = "DELETE FROM " + TABLE_NAME_HIGHSCORES +
                    " WHERE " + HIGHSCORE_ID + "=" + GetWorstEntry().GetID();
        }
        else
        {
            sql1 = "";
        }

        sql2 = "INSERT INTO " + TABLE_NAME_HIGHSCORES +
                " (" + PLAYER_NAME + "," + PAWNS_LEFT + "," + SECONDS_ELAPSED + "," + GAME_DATE + ") " +
                "VALUES (\"" + entry.GetPlayerName() + "\"," + entry.GetPawnsLeft() + "," +
                entry.GetSecondsElapsed() + ",\"" + entry.GetGameDate() + "\")";

        try
        {
            if (sql1.length() > 0)
                dbase.execSQL(sql1);

            dbase.execSQL(sql2);
        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }

    public boolean IsHighscore(HighscoreEntry entry)
    {
        if (GetEntriesNumber() < MAX_ENTRIES)
            return true;

        if (entry.IsBetterThan(GetWorstEntry()))
            return true;

        return false;
    }

    public ArrayList<HighscoreEntry> GetHighscores()
    {
        SQLiteDatabase dbase = this.getReadableDatabase();
        String sql = "SELECT *" +
                " FROM " + TABLE_NAME_HIGHSCORES +
                " ORDER BY " + PAWNS_LEFT + " ASC, " + SECONDS_ELAPSED + " ASC";
        ArrayList<HighscoreEntry> all_highscores = new ArrayList<HighscoreEntry>();
        Cursor result;

        result = dbase.rawQuery(sql, null);

        if (result == null)
        {
            return all_highscores;
        }

        if (result.moveToFirst())
        {
            HighscoreEntry entry0 = new HighscoreEntry(
               result.getInt(result.getColumnIndex(HIGHSCORE_ID)),
               result.getString(result.getColumnIndex(PLAYER_NAME)),
               result.getInt(result.getColumnIndex(PAWNS_LEFT)),
               result.getInt(result.getColumnIndex(SECONDS_ELAPSED)),
               result.getString(result.getColumnIndex(GAME_DATE))
            );
            all_highscores.add(entry0);

            while(result.moveToNext())
            {
                HighscoreEntry entry = new HighscoreEntry(
                        result.getInt(result.getColumnIndex(HIGHSCORE_ID)),
                        result.getString(result.getColumnIndex(PLAYER_NAME)),
                        result.getInt(result.getColumnIndex(PAWNS_LEFT)),
                        result.getInt(result.getColumnIndex(SECONDS_ELAPSED)),
                        result.getString(result.getColumnIndex(GAME_DATE))
                );
                all_highscores.add(entry);
            }
        }
        result.close();

        return all_highscores;
    }

    private int GetEntriesNumber()
    {
        SQLiteDatabase dbase = this.getReadableDatabase();
        String sql = "SELECT COUNT(*)" +
                    " FROM " + TABLE_NAME_HIGHSCORES;
        Cursor result;
        int count=0;

        result = dbase.rawQuery(sql, null);

        if (result == null)
        {
            return 0;
        }

        if (result.moveToFirst())
        {
            count = result.getInt(0);
        }
        result.close();

        return count;
    }

    private HighscoreEntry GetWorstEntry()
    {
        ArrayList<HighscoreEntry> all_highscores = GetHighscores();
        int worst_entry_index = 0;

        for (int i=0; i<all_highscores.size(); i++)
        {
            if (all_highscores.get(worst_entry_index).IsBetterThan(all_highscores.get(i)))
            {
                worst_entry_index = i;
            }
        }

        return all_highscores.get(worst_entry_index);
    }
}
