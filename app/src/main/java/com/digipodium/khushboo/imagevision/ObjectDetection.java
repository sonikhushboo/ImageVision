package com.digipodium.khushboo.imagevision;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ObjectDetection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_detection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Toast.makeText(this, "display", Toast.LENGTH_SHORT).show();
        Intent i= getIntent();
        if(i.hasExtra("Imagepath")){
            String imagepath = i.getStringExtra("Imagepath");
            ImageView selectedImage = findViewById(R.id.imviewobject);
            Glide.with(this).load(imagepath).into(selectedImage);
            //finish();
        }
    }

}
