package com.example.echri_terba7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ListElements extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_elements);
        Button btnSave = findViewById(R.id.Save1);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Save1)
        {
            int s=0;
            TableLayout table = findViewById(R.id.table1);
            for (int i=1; i<=5; i++)
            {
                EditText p = getTableLayoutCell(table,i,1);
                EditText q = getTableLayoutCell(table,i,2);
                int p1 = Integer.parseInt(p.getText().toString().split(" ")[0]);
                int q1 = Integer.parseInt(q.getText().toString());
                s+=p1*q1;
            }
            Intent intent = new Intent(ListElements.this, choix.class);
            intent.putExtra("total",s);
            startActivity(intent);
        }
    }

    public EditText getTableLayoutCell(TableLayout layout, int rowNo, int columnNo) {
        if (rowNo >= layout.getChildCount()) return null;
        TableRow row = (TableRow) layout.getChildAt(rowNo);

        if (columnNo >= row.getChildCount()) return null;
        return (EditText) row.getChildAt(columnNo);
    }
}
