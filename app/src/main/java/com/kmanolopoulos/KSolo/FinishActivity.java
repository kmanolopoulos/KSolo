package com.kmanolopoulos.KSolo;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FinishActivity extends Activity
{
    private int PawnsLeft;
    private int TimeElapsed;
    private String Name;
    String Date;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        GetData();
        AddToHighScores();
        ShowMessage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.finish, menu);
        return true;
    }

    private void GetData()
    {
        PawnsLeft = getIntent().getIntExtra("com.kmanolopoulos.KSolo.PawnsLeft", 9);
        TimeElapsed = getIntent().getIntExtra("com.kmanolopoulos.KSolo.TimeElapsed", 0);
    }

    private void ShowMessage()
    {
        TextView resultText;
        String message;

        message = getResources().getString(R.string.result_message1) + PawnsLeft +
                  System.getProperty("line.separator") +
                  getResources().getString(R.string.result_message2) + TimeElapsed;

        resultText = findViewById(R.id.resultText);
        resultText.setText(message);
    }

    private void AddToHighScores()
    {
        DataFileBrowser browser = new DataFileBrowser(this);

        // Check if current game can set a high score
        if (!browser.IsHighscore(new HighscoreEntry(-1, "", PawnsLeft, TimeElapsed, "")))
            return;

        // Get current date
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date = df.format(Calendar.getInstance().getTime());

        // Get player name
        GetPlayerNameAndSaveHighscore();
    }

    private void GetPlayerNameAndSaveHighscore()
    {
        Intent intent = new Intent(this, NameentryActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                DataFileBrowser browser = new DataFileBrowser(this);

                Name = data.getStringExtra("Name");

                browser.AddHighcore(new HighscoreEntry(-1, Name, PawnsLeft, TimeElapsed, Date));
            }
        }
    }

    public void OkChoice(View v)
    {
        setResult(Activity.RESULT_OK);
        finish();
    }

}
