package at.tugraz.software22.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

}