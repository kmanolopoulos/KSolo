package com.kmanolopoulos.KSolo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class HighscoresActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        DisplayHighscores();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.highscores, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void DisplayHighscores()
    {
        View v;
        TextView tv1, tv2, tv3, tv4, tv5;
        DataFileBrowser data = new DataFileBrowser(this);
        ArrayList<HighscoreEntry> entries = data.GetHighscores();

        for (int i=0; i<entries.size(); i++)
        {
            switch (i)
            {
                case 0:
                    v = findViewById(R.id.data_layout_1);
                    break;
                case 1:
                    v = findViewById(R.id.data_layout_2);
                    break;
                case 2:
                    v = findViewById(R.id.data_layout_3);
                    break;
                case 3:
                    v = findViewById(R.id.data_layout_4);
                    break;
                case 4:
                    v = findViewById(R.id.data_layout_5);
                    break;
                case 5:
                    v = findViewById(R.id.data_layout_6);
                    break;
                case 6:
                    v = findViewById(R.id.data_layout_7);
                    break;
                case 7:
                    v = findViewById(R.id.data_layout_8);
                    break;
                case 8:
                    v = findViewById(R.id.data_layout_9);
                    break;
                case 9:
                default:
                    v = findViewById(R.id.data_layout_10);
                    break;
            }

            tv1 = v.findViewById(R.id.data_number_textview_layout);
            tv1.setText("" + (i+1));

            tv2 = v.findViewById(R.id.data_name_textview_layout);
            tv2.setText(entries.get(i).GetPlayerName());

            tv3 = v.findViewById(R.id.data_pawns_textview_layout);
            tv3.setText("" + entries.get(i).GetPawnsLeft());

            tv4 = v.findViewById(R.id.data_seconds_textview_layout);
            tv4.setText("" + entries.get(i).GetSecondsElapsed());

            tv5 = v.findViewById(R.id.data_date_textview_layout);
            tv5.setText(entries.get(i).GetGameDate());
        }
    }
}
