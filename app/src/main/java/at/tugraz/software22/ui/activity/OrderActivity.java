package at.tugraz.software22.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.Order;
import at.tugraz.software22.ui.adapter.OrderAdapter;
import at.tugraz.software22.ui.viewmodel.OrderViewModel;

public class OrderActivity extends AppCompatActivity {

    private OrderViewModel orderViewModel;
    private ArrayAdapter<Order> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonLogout = findViewById(R.id.buttonLogoutEmployee);

        buttonLogout.setOnClickListener(l -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
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
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            int orderId = adapter.getItem(position).getId();

            orderViewModel.startOrder(orderId);

            Intent intent = new Intent(view.getContext(), ProductActivity.class);
            intent.putExtra("ORDER_ID", orderId);
            startActivity(intent);
        });
    }


    @Override
    protected void onResume() {

        super.onResume();
        orderViewModel.loadData();
    }
}