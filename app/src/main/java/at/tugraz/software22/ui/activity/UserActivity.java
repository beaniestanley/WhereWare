package at.tugraz.software22.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.User;
import at.tugraz.software22.ui.adapter.UserAdapter;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

public class UserActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private ArrayAdapter<User> adapter;
    private View mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setupListView();
        ImageView addButton = findViewById(R.id.add_user_button);

        addButton.setOnClickListener(event -> {
            Intent userIntent = new Intent(UserActivity.this, UserViewActivity.class);
            startActivityForResult(userIntent, 2);
        });
        userViewModel.getUserLiveData().observe(this, user -> {
            adapter.clear();
            adapter.addAll(user);
        });
        userViewModel.loadData();
    }

    private void setupListView() {
        ListView listView = findViewById(R.id.allUsers);
        adapter = new UserAdapter(this, R.layout.user_list_item);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            String first_name = data.getStringExtra(UserViewActivity.INTENT_RESULT_FIRST_NAME);
            String last_name = data.getStringExtra(UserViewActivity.INTENT_RESULT_LAST_NAME);
            userViewModel.createUser(first_name, last_name);
        }
    }

}