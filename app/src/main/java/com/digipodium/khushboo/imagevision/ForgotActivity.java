package com.digipodium.khushboo.imagevision;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {

    private static final String TAG = "forgot";
    private Button btnsubmit;
    private TextInputEditText tiResetPass;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String emailAddress = "user@example.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        tiResetPass = (TextInputEditText)findViewById(R.id.tieResetPass);
        btnsubmit = (Button)findViewById(R.id.btsubmit);
        btnsubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                emailAddress=tiResetPass.getText().toString();
                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                }
                            }
                        });
            }
        });


    }

}
