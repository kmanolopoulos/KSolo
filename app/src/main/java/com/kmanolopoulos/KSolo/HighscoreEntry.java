package com.kmanolopoulos.KSolo;

public class HighscoreEntry
{
    private int ID;
    private String PlayerName;
    private int PawnsLeft;
    private int SecondsElapsed;
    private String GameDate;

    public HighscoreEntry(int id, String player_name, int pawns_left, int seconds_elapsed, String game_date)
    {
        ID = id;
        PlayerName = player_name;
        PawnsLeft = pawns_left;
        SecondsElapsed = seconds_elapsed;
        GameDate = game_date;
    }

    public int GetID()
    {
        return ID;
    }

    public String GetPlayerName()
    {
        return PlayerName;
    }

    public int GetPawnsLeft()
    {
        return PawnsLeft;
    }

    public int GetSecondsElapsed()
    {
        return SecondsElapsed;
    }

    public String GetGameDate()
    {
        return GameDate;
    }

    public boolean IsBetterThan(HighscoreEntry entry)
    {
        if (PawnsLeft < entry.GetPawnsLeft())
            return true;

        if (PawnsLeft > entry.GetPawnsLeft())
            return false;

        if (SecondsElapsed <= entry.GetSecondsElapsed())
            return true;

        return false;
    }
}
