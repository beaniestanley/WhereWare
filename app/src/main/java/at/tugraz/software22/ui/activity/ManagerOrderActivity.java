package at.tugraz.software22.ui.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.Order;
import at.tugraz.software22.ui.adapter.CompletedOrdersAdapter;
import at.tugraz.software22.ui.viewmodel.OrderViewModel;

public class ManagerOrderActivity extends AppCompatActivity {

    private ArrayAdapter<Order> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_order);

        OrderViewModel orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        setupListView();
        orderViewModel.getCompletedOrders().observe(this, orders -> {
            adapter.clear();
            adapter.addAll(orders);
        });
        orderViewModel.loadData();

    }

    private void setupListView() {
        ListView listView = findViewById(R.id.listViewCompletedOrders);
        adapter = new CompletedOrdersAdapter(this, R.layout.list_item_completed_order);
        listView.setAdapter(adapter);
    }
}