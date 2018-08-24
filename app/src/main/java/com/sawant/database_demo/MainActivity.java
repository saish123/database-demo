package com.sawant.database_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sawant.database_demo.library.DatabaseManager;
import com.sawant.database_demo.library.IDatabaseManager;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button createTableButton;
    private Button fetchDataButton;
    private IDatabaseManager databaseManager;
    private ArrayList<HashMap<String, Object>> list;
    private Button insertDataButton;
    private EditText idEditText;
    private EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseManager = new DatabaseManager(MainActivity.this);
        createTableButton = (Button) findViewById(R.id.createTableButton);
        fetchDataButton = (Button) findViewById(R.id.fetchDataButton);
        insertDataButton = (Button) findViewById(R.id.insertDataButton);
        idEditText = (EditText) findViewById(R.id.idEditText);
        nameEditText = (EditText) findViewById(R.id.nameEditText);


        createTableButton.setOnClickListener(this);
        fetchDataButton.setOnClickListener(this);
        insertDataButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createTableButton:
                String onConflictReplaceConstraint = "UNIQUE (id) ON CONFLICT REPLACE";
                databaseManager.createDbTable("student", new String[]{"id", "name"}, new String[]{"text", "text"}, onConflictReplaceConstraint);

                break;

            case R.id.insertDataButton:
                if (idEditText != null && idEditText.getText().toString() != null
                        && !TextUtils.isEmpty(idEditText.getText().toString())
                        && nameEditText != null && nameEditText.getText().toString() != null
                        && !TextUtils.isEmpty(nameEditText.getText().toString())) {
                    databaseManager.executeUpdate("INSERT OR REPLACE INTO student(id,name) values(?,?)", new Object[]{idEditText.getText().toString(), nameEditText.getText().toString()});
                }
                break;
            case R.id.fetchDataButton:
                list = databaseManager.executeQuery("select * from student");

                for (HashMap<String, Object> hashMap : list) {
                    Log.d("Id", hashMap.get("id").toString());
                    Log.d("Name", hashMap.get("name").toString());

                }
                break;


        }
    }
}
