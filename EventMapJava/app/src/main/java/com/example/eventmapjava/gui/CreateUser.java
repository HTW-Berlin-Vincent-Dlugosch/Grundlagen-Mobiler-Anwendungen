package com.example.eventmapjava.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventmapjava.R;
import com.example.eventmapjava.logic.UserManager;
import com.example.eventmapjava.model.ComponentManager;

public class CreateUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

    }

    public void createUser(View v) {
        TextView firstNameView = findViewById(R.id.first_name_input);
        TextView lastNameView = findViewById(R.id.last_name_input);
        if(lastNameView.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Please provide last name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(firstNameView.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Please provide first name", Toast.LENGTH_SHORT).show();
            return;
        }
        UserManager userManager = ComponentManager.getComponentManager().getUserManager();

        userManager.createNewUser(lastNameView.getText().toString(), firstNameView.getText().toString());
        userManager.saveCurrentUser(getApplicationContext());
        Log.d("UserExists", Boolean.toString(userManager.currentUserExists(getApplicationContext())));
        finish();
    }
}