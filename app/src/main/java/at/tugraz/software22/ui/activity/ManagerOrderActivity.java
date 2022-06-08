package at.tugraz.software22.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.Order;
import at.tugraz.software22.ui.adapter.CompletedOrdersAdapter;
import at.tugraz.software22.ui.viewmodel.OrderViewModel;

public class ManagerOrderActivity extends AppCompatActivity {

    private DatePickerFragment datePickerFragment;

    private Button filterOrdersButton;
    private TextView textViewTitleOrders;
    private TextView textViewAverageCollectionTime;

    private static LocalDate selected_date = LocalDate.now();

    private ArrayAdapter<Order> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_order);

        filterOrdersButton = findViewById(R.id.filterOrders);
        textViewTitleOrders = findViewById(R.id.textViewTitleOrders);
        textViewAverageCollectionTime = findViewById(R.id.textViewAverageCollectionTime);

        filterOrdersButton.setOnClickListener(e -> datePickerFragment.show(getSupportFragmentManager(), "datePicker"));

        OrderViewModel orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        setupListView();
        orderViewModel.getCompletedOrders().observe(this, orders -> {
            adapter.clear();
            adapter.addAll(orders);
            textViewTitleOrders.setText(getString(R.string.managerOrderActivityShowingOrders, orders.size(), DateTimeFormatter.ofPattern("MMM YYYY").format(selected_date)));
            textViewAverageCollectionTime.setText(getString(R.string.managerOrderActivityShowingOrders, orders.size(), DateTimeFormatter.ofPattern("MMM YYYY").format(selected_date)));
            textViewAverageCollectionTime.setText(getString(R.string.managerOrderActivityAvgTime, orders.stream().mapToLong(o -> o.getCollectionTime().toMinutes()).average().orElse(0)));
        });

        orderViewModel.loadData();
        orderViewModel.filterCompletedOrdersAndLoad(LocalDate.now());

        datePickerFragment = new DatePickerFragment(orderViewModel);
    }

    private void setupListView() {
        ListView listView = findViewById(R.id.listViewCompletedOrders);
        adapter = new CompletedOrdersAdapter(this, R.layout.list_item_completed_order);
        listView.setAdapter(adapter);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private OrderViewModel orderViewModel;

        public DatePickerFragment(OrderViewModel orderViewModel) {
            this.orderViewModel = orderViewModel;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            selected_date = LocalDate.of(year, Month.of(month + 1), day);
            orderViewModel.filterCompletedOrdersAndLoad(selected_date);
        }
    }
}