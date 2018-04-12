package com.digipodium.khushboo.imagevision;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Others extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // Toast.makeText(this, "see", Toast.LENGTH_SHORT).show();
        Intent s= getIntent();
        if(s.hasExtra("Imagepath")){
            String imagepath = s.getStringExtra("Imagepath");
            ImageView selectedImage = findViewById(R.id.imviewothers);
            Glide.with(this).load(imagepath).into(selectedImage);
            finish();
        }
    }

}
