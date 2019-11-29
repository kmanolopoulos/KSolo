package com.kmanolopoulos.KSolo;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class NameentryActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nameentry);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.nameentry, menu);
        return true;
    }

    public void OkChoice(View v)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Name", ((EditText)findViewById(R.id.name_entry_edit_text)).getText().toString());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}
