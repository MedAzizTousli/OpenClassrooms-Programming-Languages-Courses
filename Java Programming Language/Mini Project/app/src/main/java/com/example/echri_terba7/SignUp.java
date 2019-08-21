package com.example.echri_terba7;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        final EditText email = findViewById(R.id.EmailUpxml);
        final EditText password1 = findViewById(R.id.PasswordUpxml);
        final EditText password2 = findViewById(R.id.PasswordUp2xml);

        Button btn = findViewById(R.id.SignUpxml);
        final Boolean vrai = (password1.getText().toString() == password2.getText().toString());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean Test = false;
                if ((v.getId() == R.id.SignUpxml) && (vrai == true)) Test = true;
                if (Test) {
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password1.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Go to next activity
                                Intent intent = new Intent(SignUp.this, SignIn.class);
                                startActivity(intent);
                                finish();
                                //Save the user
                                Toast.makeText(SignUp.this, mAuth.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
                                Intent myIntent = new Intent(SignUp.this, SignIn.class);
                                SignUp.this.startActivity(myIntent);
                            } else {
                                Toast.makeText(SignUp.this, "Error Try again", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignUp.this, "Error Try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}