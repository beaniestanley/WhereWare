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
import at.tugraz.software22.domain.User;

public class UserAdapter extends ArrayAdapter<User> {

    private static class ViewHolder{
        TextView fullName;
        TextView collectionTime;
    }
    public UserAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
            TextView fullName = convertView.findViewById(R.id.full_name);
            TextView collectionTime = convertView.findViewById(R.id.text_avg_collection_time);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.fullName = fullName;
            viewHolder.collectionTime = collectionTime;
            convertView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        User user = getItem(position);

        viewHolder.fullName.setText(getContext().getString(R.string.user_name_text, user.getForename(), user.getLastname()));
        viewHolder.collectionTime.setText(getContext().getString(R.string.text_collection_time)); //TODO change to corresponding collection time

        return convertView;
    }
}
