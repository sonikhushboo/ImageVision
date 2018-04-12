package com.digipodium.khushboo.imagevision;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText tiemail;
    private TextInputEditText tipass;
    private TextInputEditText ticpass;
    private Button btnregister;
    private TextView tvsocial;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        tiemail = (TextInputEditText) findViewById(R.id.tieEmail);
        tipass = (TextInputEditText) findViewById(R.id.tiePassword);
        ticpass = (TextInputEditText) findViewById(R.id.tieCPassword);
        btnregister = (Button) findViewById(R.id.btnregister);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = tiemail.getText().toString().trim();
                String password = tipass.getText().toString().trim();
                if (email.isEmpty()) {
                    tiemail.setError("Email cannot be empty");
                    return;
                }
                if (password.isEmpty()) {
                    tipass.setError("password cannot be empty");
                    return;
                }
                if (password.length() < 8) {
                    tipass.setError("password cannot be less than eight");
                    return;
                }


//                if (!password.equals(tipass)){
//                    ticpass.setError("password do not match");
//                    return;
//                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }

                                // ...
                            }
                        });
            }


        });
        tvsocial = (TextView) findViewById(R.id.tvSocialLogin);
        tvsocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
        }
    }

}
