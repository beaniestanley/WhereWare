package at.tugraz.software22.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.ScanMode;

import at.tugraz.software22.R;

public class CameraScanActivity extends AppCompatActivity {

    private CodeScanner codeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_scan);

        codeScanner = new CodeScanner(this, findViewById(R.id.scanner_view));
        codeScanner();
    }

    private void codeScanner()
    {
        codeScanner.setCamera(CodeScanner.CAMERA_BACK);
        codeScanner.setFormats(CodeScanner.ALL_FORMATS);
        codeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        codeScanner.setScanMode(ScanMode.CONTINUOUS);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFlashEnabled(false);

        codeScanner.setDecodeCallback(result -> {
            Intent out = new Intent();
            out.putExtra("productId", result.toString());
            setResult(RESULT_OK, out);
            finish();
        });

        codeScanner.setErrorCallback(thrown -> {
            setResult(ProductActivity.CAMERA_SCAN_ERROR);
            finish();
        });

        codeScanner.startPreview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }
}