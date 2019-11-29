package com.kmanolopoulos.KSolo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import java.util.Timer;

public class GameActivity extends Activity
{
    private GridView gridView;
    private BoardAdapter adapter;
    private int TimeElapsed;
    private Timer timer;
    private BoardTimer boardTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gridView = findViewById(R.id.boardGrid);
        adapter = new BoardAdapter(this);
        gridView.setAdapter(adapter);
        TimeElapsed = 0;
        boardTimer = new BoardTimer(this);
        timer = new Timer();
        StartTimer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_abort:
                StopTimer();
                setResult(Activity.RESULT_CANCELED);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void BackChoice(View v)
    {
        adapter.BackMove();
    }

    public void StartTimer()
    {
        timer.schedule(boardTimer, 0, 1000);
    }

    public void StopTimer()
    {
        timer.cancel();
        timer.purge();
    }

    public void UpdateTimer(final int value)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                String displayString;
                TextView tv;

                TimeElapsed = value;
                tv = findViewById(R.id.timeText);
                displayString = getResources().getString(R.string.time_text) + Integer.toString(TimeElapsed);
                tv.setText(displayString);
            }
        });
    }

    public void GameOver(int LeftPawns)
    {
        StopTimer();

        Intent intent = new Intent(this, FinishActivity.class);
        intent.putExtra("com.kmanolopoulos.KSolo.PawnsLeft", LeftPawns);
        intent.putExtra("com.kmanolopoulos.KSolo.TimeElapsed", TimeElapsed);
        startActivityForResult(intent, 1);

        setResult(Activity.RESULT_OK);
        finish();
    }
}
