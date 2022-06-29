package at.tugraz.software22.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.io.Serializable;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.Product;
import at.tugraz.software22.domain.User;
import at.tugraz.software22.ui.adapter.ProductAdapter;
import at.tugraz.software22.ui.viewmodel.ProductViewModel;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

public class ManageProductActivity extends AppCompatActivity {

    private ProductViewModel productViewModel;
    private ArrayAdapter<Product> adapter;
    ListView productListView = null;
    private View mainView;
    public static final String INTENT_EXTRA_PRODUCT = "product_object";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_product);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        setupListView();
        ImageView addButton = findViewById(R.id.add_product_button);

        addButton.setOnClickListener(event -> {
            Intent productIntent = new Intent(ManageProductActivity.this, ManageProductViewActivity.class);
            startActivityForResult(productIntent, 2);
        });

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent productIntent = new Intent(ManageProductActivity.this, ManageProductViewActivity.class);
                productIntent.putExtra(INTENT_EXTRA_PRODUCT, productViewModel.getOrderLiveData().getValue().get(i));
                startActivityForResult(productIntent, 3);
            }
        });
        productViewModel.getOrderLiveData().observe(this, products -> {
            adapter.clear();
            adapter.addAll(products);
        });
        productViewModel.loadProductData();
    }

    private void setupListView() {
        productListView = findViewById(R.id.allProducts);
        adapter = new ProductAdapter(this, productViewModel, 0, false);
        productListView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 2 || requestCode == 3) && resultCode == RESULT_OK && data != null) {
            String product_name = data.getStringExtra(ManageProductViewActivity.INTENT_RESULT_PRODUCT_NAME);
            Integer estimated_time = data.getIntExtra(ManageProductViewActivity.INTENT_RESULT_ESTIMATED_TIME, -1);
            String location = data.getStringExtra(ManageProductViewActivity.INTENT_RESULT_LOCATION);
            Integer quantity = data.getIntExtra(ManageProductViewActivity.INTENT_RESULT_QUANTITY, -1);
            Integer id = data.getIntExtra(ManageProductViewActivity.INTENT_RESULT_PRODUCT_ID, -1);
            Integer deleteId = data.getIntExtra(ManageProductViewActivity.INTENT_RESULT_DELETE_PRODUCT, -1);
            if (deleteId != -1){
                productViewModel.removeProduct(deleteId);
                return;
            }

            if (requestCode == 2) {
                productViewModel.createProduct(product_name, estimated_time, location, quantity);
                return;
            }
            productViewModel.updateProduct(id, product_name, estimated_time, location, quantity);
        }
    }
}
