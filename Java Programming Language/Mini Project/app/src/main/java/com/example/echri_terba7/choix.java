package com.example.echri_terba7;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class choix extends AppCompatActivity
        implements View.OnClickListener {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_choix);
            ImageView Category1xmljava = findViewById(R.id.Category2xml);
            Category1xmljava.setOnClickListener(this);
            ImageView Category2xmljava = findViewById(R.id.Category1xml);
            Category2xmljava.setOnClickListener(this);
            ImageView Category3xmljava = findViewById(R.id.Category3xml);
            Category3xmljava.setOnClickListener(this);
            ImageView Category4xmljava = findViewById(R.id.Category4xml);
            Category4xmljava.setOnClickListener(this);

            TextView prixFinal = findViewById(R.id.prixFinal);
            int p1 = Integer.parseInt(prixFinal.getText().toString().split(" ")[2]);
            int total = getIntent().getIntExtra("total",0);
            total+=p1;
            prixFinal.setText("Total : "+total);
        }


        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.Category2xml)
            {
                Intent intent = new Intent(com.example.echri_terba7.choix.this, ListElements.class);
                startActivity(intent);
                finish();
            }
            else if (v.getId() == R.id.Category1xml)
            {
                Intent intent = new Intent(com.example.echri_terba7.choix.this, list_element2.class);
                startActivity(intent);
                finish();
            }
            else if (v.getId() == R.id.Category3xml)
            {
                Intent intent = new Intent(com.example.echri_terba7.choix.this, List_element3.class);
                startActivity(intent);
                finish();
            }
            else if (v.getId() == R.id.Category4xml)
            {
                Intent intent = new Intent(com.example.echri_terba7.choix.this, list_element4.class);
                startActivity(intent);
                finish();
            }
        }
    }