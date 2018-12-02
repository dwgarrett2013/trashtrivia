package com.example.david.trashtrivia;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class NotificationsActivity extends Activity {

    TableLayout notificationsTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        notificationsTable=(TableLayout) findViewById(R.id.notification_table_layout);
        for(int i=0; i<2; i++) {
            TableRow row=new TableRow(getApplicationContext());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            CheckBox checkBox = new CheckBox(this);
            TextView tv = new TextView(this);
            TextView qty = new TextView(this);
            checkBox.setText("hello");
            qty.setText("10");
            row.addView(checkBox);

            row.addView(qty);
            notificationsTable.addView(row,i);
        }
    }
}
