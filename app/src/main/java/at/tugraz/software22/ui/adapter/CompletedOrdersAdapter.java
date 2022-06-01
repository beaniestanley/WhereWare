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

public class CompletedOrdersAdapter extends ArrayAdapter<Order> {

    public CompletedOrdersAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_completed_order, parent, false);

            TextView id = convertView.findViewById(R.id.textViewOrderID);
            TextView collectionTime = convertView.findViewById(R.id.textViewCollectionTime);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.id = id;
            viewHolder.collectionTime = collectionTime;
            convertView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        Order order = getItem(position);


        viewHolder.id.setText(getContext().getString(R.string.order_id, order.getId()));
        System.out.println(order.getCollectionTime().toMinutes());
        viewHolder.collectionTime.setText(getContext().getString(R.string.collection_time, order.getCollectionTime().toHours(), order.getCollectionTime().toMinutes() % 60));

        return convertView;
    }

    private static class ViewHolder {
        TextView id;
        TextView collectionTime;
    }
}
