package at.tugraz.software22.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.Language;

public class LanguageAdapter extends ArrayAdapter<Language> {

    static class ViewHolder {
        TextView name;
        ImageView imgThumb;
    }

    private Context context;
    private LayoutInflater inflater;
    private List<Language> languages;
    private ViewHolder holder;

    public LanguageAdapter(@NonNull Context context, int resource, List<Language> languages) {
        super(context, resource);

        this.context = context;
        this.languages = languages;
        inflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public int getCount() {
        return languages.size();
    }

    @Nullable
    @Override
    public Language getItem(int position) {
        return languages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public void clear() {
        this.languages.clear();
        super.clear();
    }

    public void addAll(@NonNull Collection<? extends Language> collection) {
        this.languages.addAll(collection);
        super.addAll(collection);
    }

    private View getCustomView(int position, View view, ViewGroup parent)
    {
        Language language = languages.get(position);

        View row = view;
        if (null == row) {
            holder = new ViewHolder();
            row = inflater.inflate(R.layout.spinner_item_language, parent, false);
            holder.name = row.findViewById(R.id.name);
            holder.imgThumb = row.findViewById(R.id.imgThumb);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.name.setText(language.getLocalizedString());
        holder.imgThumb.setBackgroundResource(language.getDrawable());

        return row;
    }
}
