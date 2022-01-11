package com.example.eventmapjava.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventmapjava.R;
import com.example.eventmapjava.logic.EventManager;
import com.example.eventmapjava.model.ComponentManager;
import com.example.eventmapjava.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class CreateEvent extends AppCompatActivity {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private double[] location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Intent intent = getIntent();
        location = new double[]{intent.getDoubleExtra("Latitude", 0), intent.getDoubleExtra("Longitude", 0)};
        Log.i("GeoAsString", Arrays.toString(location));
        Log.i("ButtonIDStart", Integer.toString(R.id.start_time_button));
        Log.i("ButtonIDStart", Integer.toString(R.id.end_time_button));
    }

    public void showTimePickerDialog(View v) {
        LocalTime now = LocalTime.now();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, selectedHour, selectedMinute) -> {
            if (v.getId() == R.id.start_time_button) {
                startTime = LocalTime.of(selectedHour, selectedMinute);
                TextView startTimeDisplay = findViewById(R.id.start_time_display);
                startTimeDisplay.setText(startTime.format(DateTimeFormatter.ISO_TIME));
            } else {
                endTime = LocalTime.of(selectedHour, selectedMinute);
                TextView endTimeDisplay = findViewById(R.id.end_time_display);
                endTimeDisplay.setText(endTime.format(DateTimeFormatter.ISO_TIME));
            }
            Log.i("TimeFromWhere", "ID: " + v.getId());
        }, now.getHour(), now.getMinute(), true);
        timePickerDialog.show();
    }

    public void showDatePickerDialog(View v) {
        LocalDate today = LocalDate.now();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, yearInput, monthInput, dayInput) -> {
            date = LocalDate.of(yearInput, monthInput + 1, dayInput);
            TextView dateDisplay = findViewById(R.id.date_display);
            dateDisplay.setText(date.format(DateTimeFormatter.ISO_DATE));
        }, today.getYear(), today.getMonthValue() - 1, today.getDayOfMonth());
        datePickerDialog.show();
    }

    public void submitEvent(View v) {
        TextView eventNameInput = findViewById(R.id.event_name_input);
        String eventName = eventNameInput.getText().toString();
        if (eventName.equals("")) {
            Toast.makeText(getApplicationContext(), "Please provide event name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (date == null) {
            Toast.makeText(getApplicationContext(), "Please give start date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (startTime == null) {
            Toast.makeText(getApplicationContext(), "Please give start time", Toast.LENGTH_SHORT).show();
            return;
        }
        if (endTime == null) {
            Toast.makeText(getApplicationContext(), "Please give end time", Toast.LENGTH_SHORT).show();
            return;
        }
        if (startTime.compareTo(endTime) >= 0) {
            Toast.makeText(getApplicationContext(), "End time must be greater than start time", Toast.LENGTH_SHORT).show();
            return;
        }

        EventManager eventManager = ComponentManager.getComponentManager().getEventManager();

        boolean success = eventManager.requestInsertion(location, eventName, LocalDateTime.of(date, startTime), LocalDateTime.of(date, endTime));
        Log.i("Event Created", Boolean.toString(success));
        eventManager.showAllEvents();
        finish();
    }
}

