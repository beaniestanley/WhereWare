package at.tugraz.software22.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import at.tugraz.software22.R;
import at.tugraz.software22.WhereWareApplication;
import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.Product;
import at.tugraz.software22.domain.Statuses;
import at.tugraz.software22.service.OrderService;
import at.tugraz.software22.ui.adapter.ProductAdapter;
import at.tugraz.software22.ui.viewmodel.ProductViewModel;

public class ProductActivity extends AppCompatActivity {

    private ArrayAdapter<Product> adapter;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.loadData(getIntent().getIntExtra("ORDER_ID", -1));
        setupObservers();
        setupProductList();

        Order order = productViewModel.getOrder(getIntent().getIntExtra("ORDER_ID", -1));
        Button button = findViewById(R.id.finish_button);

        if(order.getStatus() == Statuses.FINISHED)
        {
            button.setText("FINISHED");
            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_24,0,0,0);
        }

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                button.setText("FINISHED");
                button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_24,0,0,0);
                order.finishOrder();

                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    private void setupObservers() {
        productViewModel.getOrderLiveData().observe(this, order -> {
            adapter.clear();
            // TODO get all products
            adapter.addAll(order);
        });
    }

    private void setupProductList() {
        ListView listView = findViewById(R.id.listView_productList);

        adapter = new ProductAdapter(this);
        listView.setAdapter(adapter);
    }
}