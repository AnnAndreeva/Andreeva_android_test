package com.example.andreeva_android_test;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

class JSONHelper {

    private static final String FILE_NAME = "data.json";


    //Для сериализации данных в формат json
    static boolean exportToJSON(Context context, List<CalendarDate> dataList) {

        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setCalendarDates(dataList);
        String jsonString = gson.toJson(dataItems);
        try (FileOutputStream fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE))
        {
            fileOutputStream.write(jsonString.getBytes());
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    //Для десериализации данных из формата json
    static List<CalendarDate> importFromJSON(Context context) {

        try(FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
            InputStreamReader streamReader = new InputStreamReader(fileInputStream)){

            Gson gson = new Gson();

            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            streamReader.close();
            fileInputStream.close();

            return  dataItems.getCalendarDates();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        return null;
    }

    //список объектов типа CalendarDate
    private static class DataItems {
        private List<CalendarDate> calendarDates;

        List<CalendarDate> getCalendarDates() {
            return calendarDates;
        }

        void setCalendarDates(List<CalendarDate> calendarDates) {
            this.calendarDates = calendarDates;
        }
    }
}