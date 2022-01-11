package com.example.eventmapjava.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventmapjava.R;
import com.example.eventmapjava.logic.EventManager;
import com.example.eventmapjava.model.ComponentManager;
import com.example.eventmapjava.model.Event;

import org.w3c.dom.Text;

import java.time.format.DateTimeFormatter;

public class ViewEvent extends AppCompatActivity {
    private EventManager eventManager = ComponentManager.getComponentManager().getEventManager();
    private int eventID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        Intent intent = getIntent();
        eventID = intent.getIntExtra("EventID", -1);
        fillFields();
    }

    private void fillFields() {
        Event event = eventManager.requestEvent(eventID);
        TextView name = findViewById(R.id.event_name);
        TextView startTimeDate = findViewById(R.id.start_time_date);
        TextView endTimeDate = findViewById(R.id.end_time_date);
        name.setText(event.getName());
        startTimeDate.setText(event.getStartDateTime().format(DateTimeFormatter.ISO_DATE_TIME));
        endTimeDate.setText(event.getEndDateTime().format(DateTimeFormatter.ISO_DATE_TIME));
    }

    public void deleteButtonClicked(View v){
        if(eventManager.authorize(eventID)){
            eventManager.requestDeletion(eventID);
            Toast.makeText(getApplicationContext(),"Event Successfully deleted!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Toast.makeText(getApplicationContext(),"You are not authorized to delete Event!", Toast.LENGTH_SHORT).show();
    }
}