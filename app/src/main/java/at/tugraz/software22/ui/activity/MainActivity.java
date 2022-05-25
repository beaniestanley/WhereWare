package at.tugraz.software22.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.Order;
import at.tugraz.software22.ui.adapter.OrderAdapter;
import at.tugraz.software22.ui.viewmodel.OrderViewModel;

public class MainActivity extends AppCompatActivity{

    private OrderViewModel orderViewModel;
    private ArrayAdapter<Order> adapter;
    private View mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        Button button= (Button) findViewById(R.id.login_button);

        View.OnClickListener btnListener = new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                login();
            }
        };
        button.setOnClickListener(btnListener);

    }

    public void login()
    {
        System.out.println("Login Started");

        EditText loginName = findViewById(R.id.login_name);
        EditText loginPasswd = findViewById(R.id.login_passwd);

        if (loginName.getText().toString().equals(getString(R.string.admin)) && loginPasswd.getText().toString().equals(getString(R.string.admin)))
        {
            //Admin login
            Intent intent = new Intent(MainActivity.this, ManagerActivity.class);
            startActivity(intent);
        }
        else if (loginName.getText().toString().equals(getString(R.string.employee)) && loginPasswd.getText().toString().equals(getString(R.string.employee)))
        {
            //Employee login
            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            startActivity(intent);
        }
        else
        {
            //Invalid login
            Toast.makeText(getApplicationContext(), getString(R.string.invalid_login), Toast.LENGTH_SHORT).show();
        }
    }

}