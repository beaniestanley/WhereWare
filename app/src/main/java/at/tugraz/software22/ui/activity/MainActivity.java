package at.tugraz.software22.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.Order;
import at.tugraz.software22.ui.adapter.OrderAdapter;
import at.tugraz.software22.ui.viewmodel.OrderViewModel;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<Order> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OrderViewModel orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        setupListView();
        orderViewModel.getOpenOrders().observe(this, orders -> {
            TextView createdSprint = findViewById(R.id.amountOfOrders);
            createdSprint.setText(getString(R.string.unclosed_orders, orders.size()));
            adapter.clear();
            adapter.addAll(orders);
        });
        orderViewModel.loadData();
    }

    private void setupListView() {
        ListView listView = findViewById(R.id.allOrders);
        adapter = new OrderAdapter(this, R.layout.list_item);
        listView.setAdapter(adapter);
    }

}