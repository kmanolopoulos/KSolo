package com.kmanolopoulos.KSolo;

import android.content.Context;
import java.util.TimerTask;

public class BoardTimer extends TimerTask
{
    private Context mContext;
    private int timeElapsed;

    public BoardTimer(Context context)
    {
        mContext = context;
        timeElapsed = -1;
    }

    @Override
    public void run()
    {
        timeElapsed++;
        ((GameActivity)mContext).UpdateTimer(timeElapsed);
    }
}
