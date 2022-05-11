package at.tugraz.software22.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.Product;
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
    }

    private void setupObservers() {
        productViewModel.getOrderLiveData().observe(this, order -> {
            adapter.clear();
            // TODO get all products
            adapter.addAll(order.getAllOrders_());
        });
    }

    private void setupProductList() {
        ListView listView = findViewById(R.id.listView_productList);

        adapter = new ProductAdapter(this);
        listView.setAdapter(adapter);
    }

}