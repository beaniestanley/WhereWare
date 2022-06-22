package at.tugraz.software22.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import at.tugraz.software22.R;


public class MapActivity extends AppCompatActivity {

    String aisle_location = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            aisle_location = (String) extras.get(ProductActivity.INTENT_EXTRA_PRODUCT);
        }
        drawPicture();
    }

    private void drawPicture() {
        ImageView imageView = findViewById(R.id.warehouse);
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);

        Canvas canvas = new Canvas(bitmap);
        Integer x = 2 + (int)(Math.random() * ((3 - 2) + 1));
        Integer y =  2 + (int)(Math.random() * ((3 - 2) + 1));
        canvas.drawCircle(bitmap.getWidth() / x, bitmap.getHeight() / y, bitmap.getWidth() / 11, paint);
        imageView.setImageBitmap(bitmap);
    }
}
