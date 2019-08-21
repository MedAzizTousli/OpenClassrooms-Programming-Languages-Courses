package com.example.echri_terba7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Button SignInjava = findViewById(R.id.SignInxml);
        SignInjava.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.SignInxml)
        {
            Intent intent = new Intent(SignIn.this, choix.class);
            startActivity(intent);
            //finish();
        }
    }
}