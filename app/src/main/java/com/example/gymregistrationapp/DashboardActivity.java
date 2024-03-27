package com.example.gymregistrationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
    private TextView userEmailTextView, workoutTimeTextView;
    private Spinner workoutSpinner, trainerSpinner;
    private Button logoutButton, saveButton;
    private SharedPreferences sharedPreferences;
    private boolean isSelectionEnabled = true;

    // Define arrays for predefined workout and trainer names
    private String[] workoutNames = {"Running", "Swimming", "weights"};
    private String[] trainerNames = {"Nyabuto", "Irene", "Moky"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        userEmailTextView = findViewById(R.id.user_email_text_view);
        workoutTimeTextView = findViewById(R.id.workout_time_text_view);
        workoutSpinner = findViewById(R.id.workout_spinner);
        trainerSpinner = findViewById(R.id.trainer_spinner);
        logoutButton = findViewById(R.id.logout_button);
        saveButton = findViewById(R.id.save_button);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Display user email and workout time
        String userEmail = sharedPreferences.getString("userEmail", "");
        String workoutTime = sharedPreferences.getString("workoutTime", "");
        userEmailTextView.setText("Email: " + userEmail);
        workoutTimeTextView.setText("Workout Time: " + workoutTime);

        // Initialize spinner adapters with predefined names
        ArrayAdapter<String> workoutAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, workoutNames);
        workoutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workoutSpinner.setAdapter(workoutAdapter);

        ArrayAdapter<String> trainerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, trainerNames);
        trainerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trainerSpinner.setAdapter(trainerAdapter);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear session and redirect to login page
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelectionEnabled) {
                    // Get selected workout and trainer names
                    String selectedWorkout = workoutSpinner.getSelectedItem().toString();
                    String selectedTrainer = trainerSpinner.getSelectedItem().toString();

                    // Display selected names
                    Toast.makeText(DashboardActivity.this, "Selected Workout: " + selectedWorkout + "\nSelected Trainer: " + selectedTrainer, Toast.LENGTH_SHORT).show();

                    // Disable selection options
                    isSelectionEnabled = false;
                    saveButton.setEnabled(false);
                }
            }
        });
    }
}
