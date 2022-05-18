package at.tugraz.software22.ui.adapter;

import android.content.Context;
import android.service.autofill.ImageTransformation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.Product;

public class ProductAdapter extends ArrayAdapter<Product> {

    private static class ViewHolder {
        TextView name;
        TextView estimatedTime;
        TextView quantity;
        TextView location;
        ImageView status;
    }

    public ProductAdapter(@NonNull Context context) {
        super(context, R.layout.productlist_item);
    }

    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.productlist_item, parent, false);

            TextView textViewName = convertView.findViewById(R.id.textViewProductName);
            TextView textViewEstimatedTime = convertView.findViewById(R.id.textViewProductEstimatedTime);
            TextView textViewQuantity = convertView.findViewById(R.id.textViewProductQuantity);
            TextView textViewLocation = convertView.findViewById(R.id.textViewProductLocation);
            ImageView imageViewStatus = convertView.findViewById(R.id.imageViewStatus);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = textViewName;
            viewHolder.estimatedTime = textViewEstimatedTime;
            viewHolder.quantity = textViewQuantity;
            viewHolder.location = textViewLocation;
            viewHolder.status = imageViewStatus;

            convertView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        Product product = getItem(position);

        String name = product.getName();
        String estimatedTime = getContext().getString(R.string.product_estimatedtime, product.getEstimatedTime());
        String quantity = getContext().getString(R.string.product_quantity, product.getProductQuantity());
        String location = getContext().getString(R.string.product_location, product.getLocation());

        // TODO accurate status
        if(product.getStatus() != "pending") {
            int status = R.drawable.ic_baseline_check_24;
            viewHolder.status.setImageResource(status);
            viewHolder.status.setColorFilter(R.color.grey);
        }

        viewHolder.name.setText(name);
        viewHolder.estimatedTime.setText(estimatedTime);
        viewHolder.quantity.setText(quantity);
        viewHolder.location.setText(location);

        return convertView;
    }
}
