package at.tugraz.software22.ui.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.Product;
import at.tugraz.software22.domain.Statuses;
import at.tugraz.software22.ui.adapter.ProductAdapter;
import at.tugraz.software22.ui.viewmodel.ProductViewModel;

public class ProductActivity extends AppCompatActivity {

    public final static int CAMERA_REQUEST_CODE = 420;
    public final static int CAMERA_SCAN_ERROR = 421;

    private ArrayAdapter<Product> adapter;
    private ProductViewModel productViewModel;
    private Button scanProductButton;

    private int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        scanProductButton = findViewById(R.id.scanProductbtn);
        scanProductButton.setOnClickListener(l -> {
            setupPermissions();
        });

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        orderId = getIntent().getIntExtra("ORDER_ID", -1);
        productViewModel.loadData(orderId);
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

        adapter = new ProductAdapter(this, productViewModel, orderId);
        listView.setAdapter(adapter);
    }

    private void setupPermissions()
    {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        Log.d("ProductActivity", "User has permission: "
                + (permission == PackageManager.PERMISSION_GRANTED));

        if(permission != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_REQUEST_CODE);
        }
        else
        {
            startCamera();
        }
    }

    private void startCamera()
    {
        Intent intent = new Intent(this, CameraScanActivity.class);
        activityResultLauncher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_REQUEST_CODE &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            startCamera();
        }
        else
        {
            Snackbar.make(findViewById(R.id.activityProductScreen),
                    R.string.no_camera_permission,
                    Snackbar.LENGTH_SHORT).show();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case RESULT_OK:
                            try {
                                int productId = Integer.parseInt(result.getData().getStringExtra("productId"));
                                if (!productViewModel.tickProduct(orderId, productId)) {
                                    throw new Exception("Could not tick product");
                                }
                            } catch (Exception ex) {
                                Snackbar.make(findViewById(R.id.activityProductScreen), R.string.scan_invalid_id, Snackbar.LENGTH_SHORT).show();
                                Log.d("ProductActivity", "Invalid id: " + result.getData());
                            }
                            break;
                        case CAMERA_SCAN_ERROR:
                            Log.d("ProductActivity", "Camera error: " + result.getData());
                            Snackbar.make(findViewById(R.id.activityProductScreen), R.string.camera_not_working, Snackbar.LENGTH_SHORT).show();
                            break;
                        case RESULT_CANCELED:
                            Log.d("ProductActivity", "User canceled scan!");
                            break;
                        default:
                            Log.d("ProductActivity", "Did not handle default case (" + result + ")!");
                    }
                }
            }
    );
}