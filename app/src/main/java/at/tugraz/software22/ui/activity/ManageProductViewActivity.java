package at.tugraz.software22.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.Product;

public class ManageProductViewActivity extends AppCompatActivity {
    public static final String INTENT_RESULT_PRODUCT_NAME = "product_name";
    public static final String INTENT_RESULT_ESTIMATED_TIME = "estimated_time";
    public static final String INTENT_RESULT_LOCATION = "location";
    public static final String INTENT_RESULT_QUANTITY = "quantity";
    public static final String INTENT_RESULT_DELETE_PRODUCT = "deleteProduct";
    public static final String INTENT_RESULT_PRODUCT_ID = "product_id";

    Product currentProduct = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_view_activity);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            currentProduct = (Product) extras.get(ManageProductActivity.INTENT_EXTRA_PRODUCT);
        }

        // Add your custom setup here (e.g., add an on click listener to the create button).
        Button createButton = findViewById(R.id.button_create_product);
        EditText input_product_name = findViewById(R.id.input_product_name_field);
        EditText input_estimated_time = findViewById(R.id.input_product_time);
        EditText input_location = findViewById(R.id.input_location_field);
        EditText input_quantity = findViewById(R.id.input_quantity_field);
        ImageView deleteButton = findViewById(R.id.deleteButton);
        if (currentProduct != null){
            createButton.setText(R.string.edit_product);
            input_product_name.setText(currentProduct.getName());
            input_estimated_time.setText(Integer.toString(currentProduct.getEstimatedTime()));
            input_location.setText(currentProduct.getLocation());
            input_quantity.setText(Integer.toString(currentProduct.getProductQuantity()));
        }

        deleteButton.setOnClickListener(event -> {
            Integer id = currentProduct.getId();
            Intent output = new Intent();
            output.putExtra(INTENT_RESULT_DELETE_PRODUCT, id);
            setResult(RESULT_OK, output);
            finish();
        });

        createButton.setOnClickListener(event -> {
            boolean shouldReturn = false;
            String text_product_name = input_product_name.getText().toString();
            String text_estimated_time = input_estimated_time.getText().toString();
            String text_location = input_location.getText().toString();
            String text_quantity = input_quantity.getText().toString();
            if (text_product_name.trim().equals("")){
                shouldReturn = true;
                input_product_name.setError("Entered value should not empty");
            }
            if (text_estimated_time.trim().equals("")){
                shouldReturn = true;
                input_estimated_time.setError("Entered value should not empty");
            }
            if (text_location.trim().equals("")){
                shouldReturn = true;
                input_location.setError("Entered value should not empty");
            }
            if (text_quantity.trim().equals("")){
                shouldReturn = true;
                input_quantity.setError("Entered value should not empty");
            }

            int quantity, estimatedTime;
            try {
                quantity = Integer.parseInt(text_quantity);
                estimatedTime = Integer.parseInt(text_estimated_time);
            } catch(NumberFormatException ex)
            {
                return;
            }

            if (shouldReturn){
                return;
            }

            Intent output = new Intent();
            output.putExtra(INTENT_RESULT_PRODUCT_NAME, text_product_name);
            output.putExtra(INTENT_RESULT_ESTIMATED_TIME, estimatedTime);
            output.putExtra(INTENT_RESULT_LOCATION, text_location);
            output.putExtra(INTENT_RESULT_QUANTITY, quantity);
            setResult(RESULT_OK, output);
            if (currentProduct != null){
                output.putExtra(INTENT_RESULT_PRODUCT_ID, currentProduct.getId());
                setResult(RESULT_OK, output);
            }
            finish();
        });


    }

}
