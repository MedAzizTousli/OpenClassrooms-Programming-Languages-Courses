package com.example.echri_terba7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class List_element3 extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_element3);
        Button btnSave = findViewById(R.id.Save3);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Save3)
        {

            int s=0;
            TableLayout table = findViewById(R.id.table3);
            for (int i=1; i<=5; i++)
            {
                TextView p = getTableLayoutCell(table,i,1);
                TextView q = getTableLayoutCell(table,i,2);
                int p1 = Integer.parseInt(p.getText().toString().split(" ")[0]);
                int q1 = Integer.parseInt(q.getText().toString());
                s+=p1*q1;
            }

            Intent intent = new Intent(List_element3.this, choix.class);
            intent.putExtra("total",s);
            startActivity(intent);

        }
    }

    public TextView getTableLayoutCell(TableLayout layout, int rowNo, int columnNo) {
        if (rowNo >= layout.getChildCount()) return null;
        TableRow row = (TableRow) layout.getChildAt(rowNo);

        if (columnNo >= row.getChildCount()) return null;
        return (TextView) row.getChildAt(columnNo);
    }
}
