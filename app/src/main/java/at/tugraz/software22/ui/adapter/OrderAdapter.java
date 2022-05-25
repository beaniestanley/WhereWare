package at.tugraz.software22.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.Order;

public class OrderAdapter extends ArrayAdapter<Order> {

    private static class ViewHolder{
        TextView id;
        TextView location;
        TextView estimatedTime;
    }
    public OrderAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

            TextView id = convertView.findViewById(R.id.order_id);
            TextView estimatedTime = convertView.findViewById(R.id.text_estimated_id);
            TextView location = convertView.findViewById(R.id.text_quantity);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.id = id;
            viewHolder.estimatedTime = estimatedTime;
            viewHolder.location = location;
            convertView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        Order order = getItem(position);

        viewHolder.id.setText(getContext().getString(R.string.order_id, order.getId()));
        viewHolder.location.setText(getContext().getString(R.string.product_quantity, order.getProductQuantity_()));
        viewHolder.estimatedTime.setText(getContext().getString(R.string.estimated_time, order.getEstimatedTime_() / 60, order.getEstimatedTime_() % 60));

        return convertView;
    }
}
