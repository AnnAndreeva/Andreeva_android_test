package com.example.andreeva_android_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddDateActivity extends AppCompatActivity {

    private EditText nameText, descriptionText;
    private TextView currentDateTime;
    private List<CalendarDate> calendarDates;
    private Calendar dateAndTimeStart, dateAndTimeFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_date);

        getSupportActionBar().setTitle("Добавление события");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nameText = findViewById(R.id.nameText);
        dateAndTimeStart = Calendar.getInstance();
        dateAndTimeFinish = Calendar.getInstance();
        calendarDates = new ArrayList<>();
        descriptionText = findViewById(R.id.descriptionText);
        currentDateTime = findViewById(R.id.currentDateTime);
        setInitialDateTime();

    }

    // сохраняем созданное событие в json
    public void saveDate(View v) {
        CalendarDate calendarDate = new CalendarDate();
        if(!nameText.getText().toString().isEmpty()){
            calendarDate.setName(nameText.getText().toString());
            calendarDate.setDescription(descriptionText.getText().toString());
            calendarDate.setDate_start(new Timestamp(dateAndTimeStart.getTimeInMillis()));
            calendarDate.setDate_finish(new Timestamp(dateAndTimeFinish.getTimeInMillis()));
            calendarDates.add(calendarDate);
            boolean result = JSONHelper.exportToJSON(this, calendarDates);
            if(result){
                Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddDateActivity.this);
            builder.setTitle("Ошибка")
                    .setMessage("Задайте имя события!")
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }

    }


    private void setInitialDateTime() {
        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTimeStart.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTimeFinish.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(AddDateActivity.this, d,
                dateAndTimeStart.get(Calendar.YEAR),
                dateAndTimeStart.get(Calendar.MONTH),
                dateAndTimeStart.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    public void setStartTime(View v) {
        new TimePickerDialog(AddDateActivity.this, t_start,
                dateAndTimeStart.get(Calendar.HOUR_OF_DAY),
                dateAndTimeStart.get(Calendar.MINUTE), true)
                .show();
    }

    public void setFinishTime(View v) {
        new TimePickerDialog(AddDateActivity.this, t_finish,
                dateAndTimeFinish.get(Calendar.HOUR_OF_DAY),
                dateAndTimeFinish.get(Calendar.MINUTE), true)
                .show();
    }

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t_start = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTimeStart.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTimeStart.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    TimePickerDialog.OnTimeSetListener t_finish = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTimeFinish.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTimeFinish.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTimeStart.set(Calendar.YEAR, year);
            dateAndTimeStart.set(Calendar.MONTH, monthOfYear);
            dateAndTimeStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_info:
                AlertDialog.Builder builder = new AlertDialog.Builder(AddDateActivity.this);
                builder.setTitle("Информация об авторе")
                        .setMessage("Андреева Анна\n email: annandreeva21@gmail.com")
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