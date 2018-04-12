package com.digipodium.khushboo.imagevision;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity {


    private Button btnhappy;
    private Button btnsad;
    private Button btnsatisfy;
    private Button btnfeedback;
    private EditText feedbck;
    private String rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedbck= findViewById(R.id.mlfeedback);
        rating = "";
        btnhappy = findViewById(R.id.bt1);
        btnhappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating ="It was great";

            }
        });

        btnsatisfy= findViewById(R.id.bt2);
        btnsatisfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating ="It did the job";
        //String satisfy=btnsatisfy.getText().toString();
            }
        });
        btnsad = findViewById(R.id.bt3);
        btnsad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        //String sad=btnsad.getText().toString();
            rating ="I am dissapointed";
            }
        });
       // String level=btnhappy.getText().toString()||btnsatisfy.getText().toString()||btnsatisfy.getText().toString();
        btnfeedback = findViewById(R.id.btfeedback);
        btnfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(rating.equalsIgnoreCase("")){
                    Toast.makeText(FeedbackActivity.this, "Please rate the app first", Toast.LENGTH_SHORT).show();
                    return;
                }
                String message="User rating:"+rating+"\n"+feedbck.getText().toString();
             composeEmail(new String[]{"khushisoni1615@gmail.com"},"feedback",message);
            }
        });

    }
    public void composeEmail(String[] addresses, String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent,"select an app"));
        }
    }

}
