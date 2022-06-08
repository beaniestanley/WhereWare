package at.tugraz.software22.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.Order;
import at.tugraz.software22.ui.adapter.OrderAdapter;
import at.tugraz.software22.ui.viewmodel.OrderViewModel;

public class MainActivity extends AppCompatActivity{

    private Button btnLogin;
    private Button btnEnglish;
    private Button btnGerman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        btnLogin = findViewById(R.id.login_button);
        btnEnglish = findViewById(R.id.btnEng);
        btnGerman = findViewById(R.id.btnGer);

        btnLogin.setOnClickListener(v -> login());
        btnEnglish.setOnClickListener(v -> changeLanguage("en"));
        btnGerman.setOnClickListener(v -> changeLanguage("de"));

        changeLanguage(Locale.getDefault().getLanguage());
    }

    private void changeLanguage(String language)
    {
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        Locale locale = new Locale(language);
        configuration.setLocale(locale);
        Locale.setDefault(locale);
        resources.updateConfiguration(configuration, metrics);
        recreate();
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