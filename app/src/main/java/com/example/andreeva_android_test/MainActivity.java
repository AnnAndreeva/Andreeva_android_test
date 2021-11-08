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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = listView.getItemAtPosition(position).toString();
                Intent intent = new Intent(context, InfoActivity.class);
                intent.putExtra("text",text);

                startActivity(intent);
            }
        });

        calendarDates = new ArrayList<>();

        adapter = new ArrayAdapter<CalendarDate>(context, android.R.layout.simple_list_item_1, calendarDates);
        listView.setAdapter(adapter);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "-" + (month + 1) + "-" + year;
                dateView.setText("Дела на " + selectedDate + " : ");


                adapter.clear();
                adapter.notifyDataSetChanged();

                Calendar thisDayStart = Calendar.getInstance();
                thisDayStart.set(Calendar.YEAR, year);
                thisDayStart.set(Calendar.MONTH, month);
                thisDayStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                thisDayStart.set(Calendar.HOUR_OF_DAY, 0);
                thisDayStart.set(Calendar.MINUTE, 0);
                thisDayStart.set(Calendar.SECOND, 0);

                Calendar thisDayEnd = Calendar.getInstance();
                thisDayEnd.set(Calendar.YEAR, year);
                thisDayEnd.set(Calendar.MONTH, month);
                thisDayEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                thisDayEnd.set(Calendar.HOUR_OF_DAY, 23);
                thisDayEnd.set(Calendar.MINUTE, 59);
                thisDayEnd.set(Calendar.SECOND, 59);

                Timestamp startTS = new Timestamp(thisDayStart.getTimeInMillis());
                Timestamp endTS = new Timestamp(thisDayEnd.getTimeInMillis());

                calendarDates = JSONHelper.importFromJSON(context);
                List<CalendarDate> calendarToday = new ArrayList<>();

                CalendarDate temp = new CalendarDate();

                if(calendarDates!=null) {

                    for (int i = 0; i < calendarDates.size(); i++) {
                        if (calendarDates.get(i).getDate_start().after(startTS)) {
                            if (calendarDates.get(i).getDate_finish().before(endTS)) {
                                calendarToday.add(calendarDates.get(i));
                            }
                        }
                    }

                    if (calendarToday.size() != 0) {
                        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, calendarToday);
                        listView.setAdapter(adapter);
                    } else {
                        Toast.makeText(context, "Нет событий", Toast.LENGTH_LONG).show();
                    }
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