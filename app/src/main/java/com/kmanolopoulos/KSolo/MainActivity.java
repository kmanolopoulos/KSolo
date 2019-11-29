package com.kmanolopoulos.KSolo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
	    {
	    	case R.id.action_about:
	    		Intent intent = new Intent(this, AboutActivity.class);
                startActivityForResult(intent, 1);
	    		return true;
	        case R.id.action_exit:
	            finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

    public void NewGame(View v)
    {
		Intent intent = new Intent(this, GameActivity.class);
        startActivityForResult(intent, 1);
    }

    public void HighScores(View v)
    {
    	Intent intent = new Intent(this, HighscoresActivity.class);
		startActivityForResult(intent, 1);
    }

}
