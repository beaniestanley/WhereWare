package at.tugraz.software22.ui.activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import at.tugraz.software22.R;
import at.tugraz.software22.WhereWareApplication;
import at.tugraz.software22.domain.Order;
import at.tugraz.software22.service.OrderService;
import at.tugraz.software22.service.UserService;

public class ManagerActivity extends AppCompatActivity {

    int pageHeight = 1120;
    int pagewidth = 792;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        Button buttonManageEmployees = findViewById(R.id.buttonManageEmployees);
        Button buttonManageOrders = findViewById(R.id.buttonManageOrders);
        Button buttonManageProducts = findViewById(R.id.buttonManageProducts);
        Button buttonManageReports = findViewById(R.id.buttonManageReports);
        EditText editTextStartDatePicker = findViewById(R.id.start_date_picker);
        EditText editTextEndDatePicker = findViewById(R.id.end_date_picker);
        Button buttonLogout = findViewById(R.id.buttonLogoutManager);

        buttonManageOrders.setOnClickListener(l -> {
            Intent intent = new Intent(getApplicationContext(), ManagerOrderActivity.class);
            startActivity(intent);
        });

        buttonManageEmployees.setOnClickListener(l -> {
            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
            startActivity(intent);
        });

        buttonLogout.setOnClickListener(l -> {
            finish();
        });

        buttonManageReports.setOnClickListener(l -> {
            String startDate_str = editTextStartDatePicker.getText().toString();
            String endDate_str = editTextEndDatePicker.getText().toString();

            boolean bothDatesValid = true;

            if(!isValidDate(startDate_str)) {
                editTextStartDatePicker.setError(getString(R.string.invalid_date));
                bothDatesValid = false;
            }
            if(!isValidDate(endDate_str)) {
                editTextEndDatePicker.setError(getString(R.string.invalid_date));
                bothDatesValid = false;
            }

            if(bothDatesValid) {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate startDate = LocalDate.parse(startDate_str, dateFormatter);
                LocalDate endDate = LocalDate.parse(endDate_str, dateFormatter);

                if(startDate.isAfter(LocalDate.now())) {
                    editTextStartDatePicker.setError(getString(R.string.invalid_date_in_future));
                    bothDatesValid = false;
                }
                if(startDate.isAfter(endDate)) {
                    editTextEndDatePicker.setError(getString(R.string.invalid_date_end_before_start));
                    bothDatesValid = false;
                }

                if(bothDatesValid)
                    generatePDF(startDate, endDate);
            }
        });
    }

    private void generatePDF(LocalDate startDate, LocalDate endDate) {

        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

        // creating an object variable
        // for our PDF document.
        PdfDocument pdfDocument = new PdfDocument();

        // two variables for paint "paint" is used
        // for drawing shapes and we will use "title"
        // for adding text in our PDF file.
        Paint title = new Paint();

        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.

        WhereWareApplication whereWareApplication = (WhereWareApplication) getApplication();
        OrderService orderService = whereWareApplication.getOrderService();
        UserService userService = whereWareApplication.getUserService();
        List<Order> allOrders = orderService.getOrdersFromTimeframe(startDate, endDate);

        int pageExcessing = allOrders.size() - 66;
        if(pageExcessing > 0)
            pageHeight += pageExcessing * 15;

        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();

        // below line is used for setting
        // start page for our PDF file.
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        // creating a variable for canvas
        // from our page of PDF.
        Canvas canvas = myPage.getCanvas();

        //Draw Title
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        title.setTextSize(15);
        title.setColor(ContextCompat.getColor(this, R.color.black));
        canvas.drawText("Report", 50, 50, title);

        Paint tableText = new Paint();
        tableText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tableText.setColor(ContextCompat.getColor(this, R.color.black));
        tableText.setTextSize(10);

        //Draw Headers
        int verticalStart = 100;
        canvas.drawText("ID", 50, verticalStart, tableText);
        canvas.drawText("Employee", 80, verticalStart, tableText);
        canvas.drawText("Qt.", 180, verticalStart, tableText);
        canvas.drawText("Start Time", 200, verticalStart, tableText);
        canvas.drawText("End Time", 350, verticalStart, tableText);
        canvas.drawText("Collection Time [min]", 500, verticalStart, tableText);
        verticalStart += 15;

        tableText.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        //Draw Orders
        for (Order o: allOrders) {
            canvas.drawText(o.getId().toString(), 50, verticalStart, tableText);
            canvas.drawText(userService.getUserStringForOrder(o), 80, verticalStart, tableText);
            canvas.drawText(o.getProductQuantity_().toString(), 180, verticalStart, tableText);
            canvas.drawText(o.getStartTime().toString(), 200, verticalStart, tableText);
            canvas.drawText(o.getEndTime().toString(), 350, verticalStart, tableText);
            canvas.drawText(Long.toString(o.getCollectionTime().toMinutes()), 500, verticalStart, tableText);
            verticalStart += 15;
        }

        //Finish
        pdfDocument.finishPage(myPage);
        File file = new File("storage/self/primary/Documents/Report.pdf");

        try {
            // after creating a file name we will
            // write our PDF file to that location.
            pdfDocument.writeTo(new FileOutputStream(file));

            // below line is to print toast message
            // on completion of PDF generation.
            Toast.makeText(ManagerActivity.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // below line is used
            // to handle error
            e.printStackTrace();
        }

        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close();

        Uri filename= Uri.parse("storage/self/primary/Documents/Report.pdf");
        String mimeType =  MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(file.toString()));

        try {
            Intent i;
            i = new Intent(Intent.ACTION_VIEW);
            i.setDataAndType(filename,mimeType);
            startActivity(i);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(this,
                    "No Application Available to view this file type",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    //Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    public boolean isValidDate(String dateStr) {
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate.parse(dateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}