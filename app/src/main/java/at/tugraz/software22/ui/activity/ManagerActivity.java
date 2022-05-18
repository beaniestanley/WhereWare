package at.tugraz.software22.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import at.tugraz.software22.R;

public class ManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        Button buttonManageEmployees = findViewById(R.id.buttonManageEmployees);
        Button buttonManageOrders = findViewById(R.id.buttonManageOrders);
        Button buttonManageProducts = findViewById(R.id.buttonManageProducts);
        Button buttonManageReports = findViewById(R.id.buttonManageReports);

        buttonManageOrders.setOnClickListener(l -> {
            Intent intent = new Intent(getApplicationContext(), ManagerOrderActivity.class);
            startActivity(intent);
        });
    }
}