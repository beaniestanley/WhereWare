package at.tugraz.software22.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.User;

public class UserViewActivity extends AppCompatActivity {
    public static final String INTENT_RESULT_FIRST_NAME = "first_name";
    public static final String INTENT_RESULT_LAST_NAME = "last_name";
    public static final String INTENT_RESULT_DELETE_USER = "deleteUser";
    Integer UPDATE_USER = 3;
    Integer DELETE_USER = 4;
    User currentUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_view_activity);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            currentUser = (User) extras.get(UserActivity.INTENT_EXTRA_USER);
        }

        // Add your custom setup here (e.g., add an on click listener to the create button).
        Button createButton = findViewById(R.id.button);
        EditText input_firstname = findViewById(R.id.input_firstname_field);
        EditText input_lastname = findViewById(R.id.input_lastname_field);
        ImageView deleteButton = findViewById(R.id.deleteButton);
        if (currentUser != null){
            createButton.setText(R.string.edit_user);
            input_firstname.setText(currentUser.getForename());
            input_lastname.setText(currentUser.getLastname());
        }
        deleteButton.setOnClickListener(event -> {
            Integer id = currentUser.getId();
            Intent output = new Intent();
            output.putExtra(INTENT_RESULT_DELETE_USER, id);
            setResult(DELETE_USER, output);
            finish();
        });
        createButton.setOnClickListener(event -> {
            Boolean shouldReturn = false;
            String text_firstname = input_firstname.getText().toString();
            String text_lastname = input_lastname.getText().toString();
            if (text_firstname.trim().equals("")){
                shouldReturn = true;
                input_firstname.setError("Entered value should not empty");
            }
            if (text_lastname.trim().equals("")){
                shouldReturn = true;
                input_lastname.setError("Entered value should not empty");
            }
            if (shouldReturn){
                return;
            }
            Intent output = new Intent();
            output.putExtra(INTENT_RESULT_FIRST_NAME, text_firstname);
            output.putExtra(INTENT_RESULT_LAST_NAME, text_lastname);
            setResult(RESULT_OK, output);
            if (currentUser != null){
                setResult(UPDATE_USER, output);
            }
            finish();
        });


    }

}
