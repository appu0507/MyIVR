package com.example.ivrapp;

import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.provider.Settings;

// this class is the startup activity that consists of just a start button 

public class MainScreenActivity<btnStart> extends Activity {

  Button btnStart;
//    Button btnAdd;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        
        
        
        // Buttons
        btnStart = (Button) findViewById(R.id.btnStart);
//        btnAdd = (Button) findViewById(R.id.btnAdd);
        
        // view products click event
        btnStart.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), TraverseTreeActivity.class);
                startActivity(i);
 
            }
        });
 
        
    }

}
