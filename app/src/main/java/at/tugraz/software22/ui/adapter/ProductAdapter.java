package at.tugraz.software22.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.Product;
import at.tugraz.software22.domain.Statuses;
import at.tugraz.software22.ui.viewmodel.ProductViewModel;

public class ProductAdapter extends ArrayAdapter<Product> {

    private final ProductViewModel productViewModel;
    private final int orderId;

    private static class ViewHolder {
        TextView name;
        TextView estimatedTime;
        TextView quantity;
        TextView location;
        CheckBox status;
    }

    public ProductAdapter(@NonNull Context context, ProductViewModel productViewModel, int orderId) {
        super(context, R.layout.productlist_item);
        this.productViewModel = productViewModel;
        this.orderId = orderId;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.productlist_item, parent, false);

            TextView textViewName = convertView.findViewById(R.id.textViewProductName);
            TextView textViewEstimatedTime = convertView.findViewById(R.id.textViewProductEstimatedTime);
            TextView textViewQuantity = convertView.findViewById(R.id.textViewProductQuantity);
            TextView textViewLocation = convertView.findViewById(R.id.textViewProductLocation);
            CheckBox checkbox = convertView.findViewById(R.id.checkBox);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = textViewName;
            viewHolder.estimatedTime = textViewEstimatedTime;
            viewHolder.quantity = textViewQuantity;
            viewHolder.location = textViewLocation;
            viewHolder.status = checkbox;

            convertView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        Product product = getItem(position);

        String name = product.getName();
        String estimatedTime = getContext().getString(R.string.product_estimatedtime, product.getEstimatedTime());
        String quantity = getContext().getString(R.string.product_quantity, product.getProductQuantity());
        String location = getContext().getString(R.string.product_location, product.getLocation());

        viewHolder.status.setOnClickListener(
                v -> productViewModel.tickProduct(orderId, product.getId()));

        viewHolder.status.setChecked(product.getStatus() == Statuses.FINISHED);
        viewHolder.name.setText(name);
        viewHolder.estimatedTime.setText(estimatedTime);
        viewHolder.quantity.setText(quantity);
        viewHolder.location.setText(location);

        return convertView;
    }
}
