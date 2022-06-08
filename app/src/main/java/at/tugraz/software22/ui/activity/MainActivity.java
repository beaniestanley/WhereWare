package at.tugraz.software22.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Objects;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.Language;
import at.tugraz.software22.ui.adapter.LanguageAdapter;

public class MainActivity extends AppCompatActivity{

    private TextView title;
    private Button btnLogin;
    private Spinner languageSelector;
    private LanguageAdapter languageAdapter;
    private EditText loginName;
    private EditText loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        title = findViewById(R.id.tvLoginTitle);
        loginName = findViewById(R.id.login_name);
        loginPassword = findViewById(R.id.login_passwd);
        btnLogin = findViewById(R.id.login_button);
        languageSelector = findViewById(R.id.langSelector);

        btnLogin.setOnClickListener(v -> login());

        languageAdapter = new LanguageAdapter(this,
                android.R.layout.simple_spinner_item, Language.getLanguages(getResources()));
        languageSelector.setAdapter(languageAdapter);
        languageSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                changeLanguage(Objects.requireNonNull(languageAdapter.getItem(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void changeLanguage(Language language)
    {
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        Locale locale = new Locale(language.getId());
        configuration.setLocale(locale);
        Locale.setDefault(locale);
        resources.updateConfiguration(configuration, metrics);
        Log.d("MainActivity", "Changed language to " + language.getId());

        loginName.setHint(R.string.name);
        loginPassword.setHint(R.string.password);
        title.setText(R.string.login_title);
        btnLogin.setText(R.string.login_button);
        languageAdapter.clear();
        languageAdapter.addAll(Language.getLanguages(getResources()));
    }

    public void login()
    {
        Log.d("MainActivity", "Login started");

        if (loginName.getText().toString().equals(getString(R.string.admin)) && loginPassword.getText().toString().equals(getString(R.string.admin)))
        {
            //Admin login
            Intent intent = new Intent(MainActivity.this, ManagerActivity.class);
            startActivity(intent);
        }
        else if (loginName.getText().toString().equals(getString(R.string.employee)) && loginPassword.getText().toString().equals(getString(R.string.employee)))
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