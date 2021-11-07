package com.example.andreeva_android_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView dateView;
    private List<CalendarDate> calendarDates;
    private ArrayAdapter<CalendarDate> adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = this;

        getSupportActionBar().setTitle("Ежедневник");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        calendarView = findViewById(R.id.calendarView);
        dateView = findViewById(R.id.textView);
        listView = findViewById(R.id.list);

        calendarDates = new ArrayList<>();

        adapter = new ArrayAdapter<CalendarDate>(context, android.R.layout.simple_list_item_1, calendarDates);
        listView.setAdapter(adapter);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "-" + (month + 1) + "-" + year;
                dateView.setText("Дела на " + selectedDate + " : ");

                calendarDates = JSONHelper.importFromJSON(context);
                if(calendarDates!=null){
                    adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, calendarDates);
                    listView.setAdapter(adapter);
                    Toast.makeText(context, "Данные восстановлены", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(context, "Не удалось открыть данные", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void addDate(View v){
        Intent i;
        i = new Intent(this, AddDateActivity.class);
        startActivity(i);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Информация об авторе")
                        .setMessage("Андреева Анна\nemail: annandreeva21@gmail.com\nGitHub:https://github.com/AnnAndreeva")
                        .setCancelable(false)
                        .setNegativeButton("ОК",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}