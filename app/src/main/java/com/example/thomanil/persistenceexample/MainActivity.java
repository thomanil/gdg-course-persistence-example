package com.example.thomanil.persistenceexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Log.d("GDG", "App started ---------- ");

        String lastTime = getTimeFromKeyVal();
        Log.d("GDG", "Last saved timestamp: "+lastTime);

        String currentTime = new Date().toString();
        Log.d("GDG", "Saving timestamp: "+currentTime);
        saveCurrentTimeToKeyVal(currentTime);
    }

    private final static String FILE = "timestamps.txt";

    private String getTimeFromKeyVal() {
        SharedPreferences sharedPreferences = getSharedPreferences("FILE", MODE_PRIVATE);
        return sharedPreferences.getString("timestamp", "not set yet");
    }

    private void saveCurrentTimeToKeyVal(String currentTime) {
        SharedPreferences sharedPreferences = getSharedPreferences("FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("timestamp", currentTime);
        editor.commit();
    }


    // Alternative: save to/from File api instead

    private String getTimeFromFile() {
        FileInputStream inputStream;
        final StringBuffer storedString = new StringBuffer();
        try {
            inputStream = openFileInput(FILE);
            DataInputStream dataIO = new DataInputStream(inputStream);
            String strLine = null;

            if ((strLine = dataIO.readLine()) != null) {
                storedString.append(strLine);
            }

            dataIO.close();
            inputStream.close();
        } catch (Exception e) {
        }
        return storedString.toString();
    }

    private void saveCurrentTimeToFile(String currentTime) {
        FileOutputStream outputStream;
         try {
            outputStream = openFileOutput(FILE, Context.MODE_PRIVATE);
            outputStream.write(currentTime.getBytes());
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
